package cn.lzscxb.business.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.lzscxb.business.mapper.FengProblemMapper;
import cn.lzscxb.business.mapper.FengTaskJoinMapper;
import cn.lzscxb.common.core.redis.RedisCache;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.DictUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import cn.lzscxb.common.utils.docker.DockerClientUtils;
import cn.lzscxb.common.utils.file.FreeMarkerUtils;
import cn.lzscxb.domain.constant.CacheConstants;
import cn.lzscxb.domain.entity.FengProblem;
import cn.lzscxb.domain.entity.FengTaskJoin;
import cn.lzscxb.domain.model.ExecuteResult;
import cn.lzscxb.domain.page.PageDomain;
import cn.lzscxb.domain.page.TableSupport;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.github.dockerjava.api.command.CreateContainerResponse;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemQueueMapper;
import cn.lzscxb.domain.entity.FengProblemQueue;
import cn.lzscxb.business.service.IFengProblemQueueService;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

/**
 * 任务管理Service业务层处理
 *
 * @author Likfees
 * @date 2022-12-08
 */
@Slf4j
@Service
public class FengProblemQueueServiceImpl implements IFengProblemQueueService {
    @Autowired
    private FengProblemQueueMapper fengProblemQueueMapper;

    @Autowired
    private FengProblemMapper fengProblemMapper;

    @Autowired
    private FengTaskJoinMapper fengTaskJoinMapper;

    @Autowired
    private FengProblemServiceImpl fengProblemService;

    @Autowired
    private DockerClientUtils dockerClientUtils;

    @Autowired
    private RedisCache redisCache;

    /**
     * 存放执行任务的信息
     */
    private FengProblemQueue queueInfo;

    /**
     * 存放执行任务的题目信息
     */
    private FengProblem problemInfo;

    @Override
    @Transactional // 开启事物
    public Integer checkQueue(FengProblemQueue fengProblemQueue) {
        FengProblemQueue queue = fengProblemQueueMapper.selectFengProblemQueueById(fengProblemQueue.getId());
        if (queue == null) {
            log.info("queueId:{} 信息不存在", fengProblemQueue.getId());
            throw new RuntimeException("记录不存在");
        }
        fengProblemQueue.setStatus(2);
        fengProblemQueue.setType(queue.getType());
        fengProblemQueue.setUpdateTime(DateUtils.getNowDate());
        int i = fengProblemQueueMapper.updateFengProblemQueue(fengProblemQueue);
        if (i <= 0) {
            throw new RuntimeException("更新任务状态失败");
        }
        // 修改旧的待批阅提交数据为失败状态
        queue.setStatus(5);
        List<FengProblemQueue> list = fengProblemQueueMapper.selectFengProblemQueue(queue);
        for (FengProblemQueue problemQueue : list) {
            problemQueue.setStatus(3);
            i = fengProblemQueueMapper.updateFengProblemQueue(problemQueue);
            if (i <= 0) {
                log.info("queueId:{} 旧数据更新状态失败", problemQueue.getId());
                throw new RuntimeException("更新子任务状态失败");
            }
        }
        FengProblem problem = new FengProblem();
        problem.setTaskId(queue.getTaskId());
        // 判断参与的学习任务是都是全部完成 并计算最终成绩和批阅时间
        List<FengProblem> problems = fengProblemService.selectFengProblemTaskList(problem, queue.getUserId());
        Integer totalScore = 0;
        boolean isComplete = true; // 是否全部完成
        for (FengProblem fengProblem : problems) {
            if (fengProblem.getOwnness() != 1) {
                isComplete = false;
                break;
            }
            if (fengProblem.getScore() == null) {
                totalScore += 100;
            } else{
                totalScore += fengProblem.getScore() ;
            }
        }
        // 如果所有任务已完成
        if (isComplete) {
            FengTaskJoin join = fengTaskJoinMapper.selectFengTaskJoinById(queue.getTaskJoinId());
            join.setScore(totalScore / problems.size());
            join.setStatus(1);
            join.setCheckTime(DateUtils.getNowDate());
            join.setUpdateBy(fengProblemQueue.getUpdateBy());
            join.setUpdateTime(DateUtils.getNowDate());
            fengTaskJoinMapper.updateFengTaskJoin(join);
        }
        return 1;
    }

    /**
     * 待批阅列表
     */
    @Override
    public List<FengProblemQueue> selectFengTaskCheckList(FengProblemQueue fengProblemQueue) {
        List<FengProblemQueue> list = fengProblemQueueMapper.selectFengTaskCheckList(fengProblemQueue);
        return list;
    }

    @Override
    public List<FengProblemQueue> selectFengProblemRankList(FengProblemQueue fengProblemQueue) {
        List<FengProblemQueue> fengProblemQueues = fengProblemQueueMapper.selectFengProblemRankList(fengProblemQueue);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        log.info("当前 PageNum:{}, pageSize: {}", pageNum, pageSize);
        int ranking = (pageNum - 1) * pageSize;
        for (int i = 0; i < fengProblemQueues.size(); i++) {
            fengProblemQueues.get(i).setRanking(ranking + i + 1);
        }
        return fengProblemQueues;
    }

    @Override
    public FengProblemQueue excuteQuque(long id) {
        FengTaskJoin joinInfo = null;
        try {
            queueInfo = fengProblemQueueMapper.selectFengProblemQueueById(id);
            if (queueInfo == null) {
                log.error("ququeId：{} 记录信息不存在", id);
                return null;
            }
            if (queueInfo.getTaskId() > 0) {
                FengTaskJoin join = new FengTaskJoin();
                join.setUserId(queueInfo.getUserId());
                join.setTaskId(queueInfo.getTaskId());
                joinInfo = fengTaskJoinMapper.selectFengTaskJoinByTaskId(join);
                joinInfo.setSubmitTime(DateUtils.getNowDate());
//                if (queueInfo.getIsAuto().equals(0)) {
//                    joinInfo.setCheckTime(DateUtils.getNowDate());
//                }
            }
            problemInfo = fengProblemMapper.selectFengProblemById(queueInfo.getProblemId());
            if (problemInfo == null) {
                log.error("ququeId：{}, problemId:{} 题目记录信息不存在", id, queueInfo.getProblemId());
                return null;
            }
            Long problemId = problemInfo.getId();
            Long userId = queueInfo.getUserId();
            queueInfo.setStatus(1); // 执行中
            fengProblemQueueMapper.updateFengProblemQueue(queueInfo);
            ExecuteResult executeResult = null;
            switch (queueInfo.getType()) {
                case 0:
                    executeResult = excuteQueueSubmit(id, userId, problemId, "java");
                    break;
                case 1:
                    executeResult = excuteQueueSubmit(id, userId, problemId, "python");
                    break;
                case 6:
                    executeResult = excuteQueueSubmit(id, userId, problemId, "shell");
                    break;
                default:
                    throw new RuntimeException("未找到语言类型执行方案：" + queueInfo.getType());
            }
            queueInfo.setExecuteResult(executeResult);
            queueInfo.setSuccessMsg(JSON.toJSONString(executeResult));
            if (!executeResult.isStatus()) {
                queueInfo.setStatus(3); // 执行失败
                if (executeResult.getErrorMsg() != null && !executeResult.getErrorMsg().equals("")) {
                    queueInfo.setErrorMsg(JSON.toJSONString(executeResult.getErrorMsg()));
                } else {
                    queueInfo.setErrorMsg(JSON.toJSONString(executeResult.getErrorTestCase()));
                }
                log.error("ququeId: {}, 执行队列任务失败：{}", queueInfo.getId(), queueInfo.getErrorMsg());
            } else {
                // 执行成功
                queueInfo.setStatus(2);
                // 如果是教师批阅则删除之前的所有执行记录（只保留一条）
                if (problemInfo.getIsAuto() == 1 || queueInfo.getType() == 6) { // 待批阅状态
                    queueInfo.setStatus(5);
                } else {
                    queueInfo.setScore(100);
                    problemInfo.setSuccessCount(problemInfo.getSuccessCount() + 1); // 添加成功次数
                }
//                if (joinInfo != null && queueInfo.getIsAuto().equals(0)) {
//                    joinInfo.setScore(100);
//                }
                // 如果有 taskId 检查学习任务是否全部完成
            }

        } catch (Exception e) {
            // 执行失败
            queueInfo.setStatus(4);
            queueInfo.setErrorMsg(JSON.toJSONString(e.getMessage()));
            log.error("ququeId: {}, 执行队列任务异常：{}", queueInfo.getId(), e.getMessage());
            e.printStackTrace();
        } finally {
            queueInfo.setRetryCount(queueInfo.getRetryCount() + 1);
            problemInfo.setSubmitCount(problemInfo.getSubmitCount() + 1);
            fengProblemMapper.updateFengProblem(problemInfo);
            fengProblemQueueMapper.updateFengProblemQueue(queueInfo);
            if (joinInfo != null) {
                fengTaskJoinMapper.updateFengTaskJoin(joinInfo);
            }
        }
        // 如果有 taskId 检查学习任务是否全部完成
        if (queueInfo.getTaskId() > 0) {
            FengProblem problem = new FengProblem();
            problem.setTaskId(queueInfo.getTaskId());
            // 判断参与的学习任务是都是全部完成 并计算最终成绩和批阅时间
            List<FengProblem> problems = fengProblemService.selectFengProblemTaskList(problem, queueInfo.getUserId());
            Integer totalScore = 0;
            boolean isComplete = true; // 是否全部完成
            for (FengProblem fengProblem : problems) {
                if (fengProblem.getOwnness() != 1) {
                    isComplete = false;
                    break;
                }
                if (fengProblem.getScore() == null) {
                    totalScore += 100;
                } else {
                    totalScore += fengProblem.getScore();
                }
            }
            // 如果所有任务已完成
            if (isComplete) {
                FengTaskJoin join = fengTaskJoinMapper.selectFengTaskJoinById(queueInfo.getTaskJoinId());
                join.setScore(totalScore / problems.size());
                join.setStatus(1);
                join.setCheckTime(DateUtils.getNowDate());
                join.setUpdateBy("robot");
                join.setUpdateTime(DateUtils.getNowDate());
                fengTaskJoinMapper.updateFengTaskJoin(join);
            }
        }

        return queueInfo;
    }

    /**
     * 执行 JAVA 任务的队列
     *
     * @param
     * @return
     */
    public ExecuteResult excuteQueueSubmit(long id, long userId, long problemId, String language) {

        // 临时目录
        String workDir = String.format("%sfengcode/u%d-id%d-p%d/", System.getProperty("java.io.tmpdir"), userId, id, problemId);
        // Dockerfile 是保存在本地的，为了方便测使用，和上传目录分开写
        String dockerfileName = workDir + language + "Dockerfile";

        String codeDir = workDir + language;
        File javaCodeDirFile = new File(codeDir);
        if (!javaCodeDirFile.exists()) {
            if (!javaCodeDirFile.mkdirs()) {
                throw new RuntimeException("创建代码目录失败, " + codeDir);
            }
        }
        log.info("复制文件中");
        // 先将resource 中的代码复制到指指定目录
        FreeMarkerUtils.BatCopyFileFromJar("/template/code/" + language, codeDir);
        FreeMarkerUtils.CopyFileFromJar("/template/docker/" + language + ".tpl", dockerfileName);

        // 填充代码模版
        switch (language) {
            case "java":
                javaThymeleafFill(codeDir);
                break;
            case "python":
                pythonThymeleafFill(codeDir);
                break;
            case "shell":
                shellThymeleafFill(codeDir);
                break;
        }

        log.info("编译镜像中");
        String imageId = dockerClientUtils.buildImage(String.format("solution-u%d-id%d-p%d", userId, id, problemId), new File(dockerfileName));

        // 创建容器
        log.info("创建容器中");
        CreateContainerResponse container = dockerClientUtils.createContainer(imageId);

        // 启动容器
        log.info("启动容器，id:{}", container.getId());
        dockerClientUtils.startContainer(container.getId());
        // 查看容器启动日志
        String containerLogs = dockerClientUtils.getContainerLogs(container.getId());
        log.info("日志内容：{}", containerLogs);
        // 删除容器
        dockerClientUtils.deleteContainer(container.getId());
        // 删除镜像
        dockerClientUtils.deleteImage(imageId);
        ExecuteResult executeResult = null;
        try {
            executeResult = JSON.parseObject(containerLogs, ExecuteResult.class);
        } catch (JSONException e) {
            log.warn("解析容器日志JSON失败,err:{}，logs为:{}", e.getMessage(), containerLogs);
            executeResult = new ExecuteResult();
            executeResult.setErrorMsg(containerLogs);
        }
        return executeResult;

    }

    /**
     * 拼接参数列表 --- JAVA
     *
     * @param languageType 语言类型
     * @return String
     */
    public String joinParamType(int languageType) {
        String[] paramsArr = problemInfo.parseParamType(languageType);
        String param = "  ";
        StringBuilder stringBuffer = new StringBuilder();
        switch (languageType) {
            case 0:
                for (int i = 0; i < paramsArr.length; i++) {
                    stringBuffer.append(String.format("ReflectUtils.conver(%s.class, paramArr[%d]), ", paramsArr[i], i));
                }
                param = stringBuffer.toString();
                break;
            case 1:
                System.out.println("");
                for (int i = 0; i < paramsArr.length; i++) {
                    stringBuffer.append(String.format("conver_param(param_arr[%d]), ", i));
                }
                param = stringBuffer.toString();
                break;
        }
        return param.substring(0, param.length() - 2);
    }

    /**
     * 拼接参数列表 --- JAVA
     *
     * @param languageType 语言类型
     * @return String
     */
    public String joinParamType(int languageType, int paramCount) {
        String param = "--";
        StringBuilder stringBuffer = new StringBuilder();
        switch (languageType) {
            case 1:
                System.out.println("");
                for (int i = 0; i < paramCount; i++) {
                    stringBuffer.append(String.format("conver_param(param_arr[%d]), ", i));
                }
                param = stringBuffer.toString();
                break;
        }
        return param.substring(0, param.length() - 2);
    }

    /**
     * Java 代码填充
     */
    public void javaThymeleafFill(String codeDir) {
        TemplateEngine engine = new TemplateEngine();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix(codeDir + "/");
        templateResolver.setSuffix(".tpl");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCacheable(false);
        engine.setTemplateResolver(templateResolver);

        // 填充代码
        FileWriter SolutionWriter = null;
        try {
            SolutionWriter = new FileWriter(codeDir + "/Solution.java");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Context SolutionContext = new Context();
        SolutionContext.setVariable("codeText", queueInfo.getCode());
        engine.process("Solution", SolutionContext, SolutionWriter);


        // 测试类
        FileWriter TestSolutionWriter = null;
        try {
            TestSolutionWriter = new FileWriter(codeDir + "/TestSolution.java");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Context TestSolutionContext = new Context();
        TestSolutionContext.setVariable("methodName", problemInfo.parseMethodName(queueInfo.getType()));
        String paramString = joinParamType(queueInfo.getType());
        log.info("拼接所得的字符串参数为：{}", paramString);
        TestSolutionContext.setVariable("paramString", paramString); // 入参字符串
        String paramTypeStr = JSON.toJSONString(problemInfo.parseParamType(queueInfo.getType())).replace("\"", "\\\"");
        TestSolutionContext.setVariable("paramTypeStr", paramTypeStr);
        TestSolutionContext.setVariable("testCase", problemInfo.getTestCase());

        engine.process("TestSolution", TestSolutionContext, TestSolutionWriter);

    }

    /**
     * Python 代码填充
     */
    public void pythonThymeleafFill(String codeDir) {
        TemplateEngine engine = new TemplateEngine();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix(codeDir + "/");
        templateResolver.setSuffix(".tpl");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCacheable(false);
        engine.setTemplateResolver(templateResolver);

        // 测试类
        FileWriter SolutionWriter = null;
        try {
            SolutionWriter = new FileWriter(codeDir + "/Solution.py");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Context TestSolutionContext = new Context();
        TestSolutionContext.setVariable("methodName", problemInfo.parseMethodName(queueInfo.getType()));

        int paramCount = 0;
        Pattern compile = Pattern.compile("def " + problemInfo.parseMethodName(queueInfo.getType()) + "(.*?):");
        Matcher matcher = compile.matcher(queueInfo.getCode());
        boolean b = matcher.find();
        if (b) {
            String s = matcher.group();
            paramCount = s.split(",").length;
            if (paramCount >= 1) {
                paramCount -= 1;
            }
        }
        String paramString = joinParamType(queueInfo.getType(), paramCount);
        log.info("拼接所得的字符串参数为：{}", paramString);
        TestSolutionContext.setVariable("codeText", queueInfo.getCode()); // 入参字符串
        TestSolutionContext.setVariable("paramString", paramString); // 入参字符串
        TestSolutionContext.setVariable("testCase", problemInfo.getTestCase());

        engine.process("Solution", TestSolutionContext, SolutionWriter);

    }

    /**
     * Shell 代码填充
     */
    public void shellThymeleafFill(String codeDir) {
        TemplateEngine engine = new TemplateEngine();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix(codeDir + "/");
        templateResolver.setSuffix(".tpl");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCacheable(false);
        engine.setTemplateResolver(templateResolver);

        // 测试类
        FileWriter SolutionWriter = null;
        try {
            SolutionWriter = new FileWriter(codeDir + "/Solution.py");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Context TestSolutionContext = new Context();
        TestSolutionContext.setVariable("codeText", queueInfo.getCode()); // 入参字符串
        engine.process("Solution", TestSolutionContext, SolutionWriter);

    }

    /**
     * 查询任务管理
     *
     * @param id 任务管理主键
     * @return 任务管理
     */
    @Override
    public FengProblemQueue selectFengProblemQueueById(Long id) {
        FengProblemQueue fengProblemQueue = fengProblemQueueMapper.selectFengProblemQueueById(id);
        String code_language = DictUtils.getDictLabel("code_language", String.valueOf(fengProblemQueue.getType()));
        log.info("typeId:{} dictVal: {}", String.valueOf(fengProblemQueue.getType()), code_language );
        fengProblemQueue.setTypeName(code_language);
        return fengProblemQueue;
    }

    /**
     * 查询任务管理列表
     *
     * @param fengProblemQueue 任务管理
     * @return 任务管理
     */
    @Override
    public List<FengProblemQueue> selectFengProblemQueueList(FengProblemQueue fengProblemQueue) {
        return fengProblemQueueMapper.selectFengProblemQueueList(fengProblemQueue);
    }

    /**
     * 新增任务管理
     *
     * @param fengProblemQueue 任务管理
     * @return 结果
     */
    @Override
    public int insertFengProblemQueue(FengProblemQueue fengProblemQueue) {
        Long userId = SecurityUtils.getUserId();
        fengProblemQueue.setCreateTime(DateUtils.getNowDate());
        fengProblemQueue.setUserId(SecurityUtils.getUserId());
        Long taskId = fengProblemQueue.getTaskId();
        // 如果有 taskId 就是学习任务中答题
        if (taskId != null && taskId > 0) {
            fengProblemQueue.setTaskId(taskId);
            FengTaskJoin join = new FengTaskJoin();
            join.setTaskId(taskId);
            join.setUserId(userId);
            FengTaskJoin fengTaskJoin = fengTaskJoinMapper.selectFengTaskJoinByTaskId(join);
            fengProblemQueue.setTaskJoinId(fengTaskJoin.getId());
        }


        long i = fengProblemQueueMapper.insertFengProblemQueue(fengProblemQueue);
        long id = fengProblemQueue.getId();
        // 放入 redis 中
        redisCache.cacheListRpush(CacheConstants.QUQUE_EXECUTE, (int) id);
        return (int) id;
    }

    /**
     * 修改任务管理
     *
     * @param fengProblemQueue 任务管理
     * @return 结果
     */
    @Override
    public int updateFengProblemQueue(FengProblemQueue fengProblemQueue) {
        fengProblemQueue.setUpdateTime(DateUtils.getNowDate());
        return fengProblemQueueMapper.updateFengProblemQueue(fengProblemQueue);
    }

    /**
     * 批量删除任务管理
     *
     * @param ids 需要删除的任务管理主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemQueueByIds(Long[] ids) {
        return fengProblemQueueMapper.deleteFengProblemQueueByIds(ids);
    }

    /**
     * 删除任务管理信息
     *
     * @param id 任务管理主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemQueueById(Long id) {
        return fengProblemQueueMapper.deleteFengProblemQueueById(id);
    }

    @Override
    public List<FengProblemQueue> submitList(FengProblemQueue fengProblemQueue) {
        fengProblemQueue.setUserId(SecurityUtils.getUserId());
        return fengProblemQueueMapper.selectSubmitList(fengProblemQueue);
    }

}
