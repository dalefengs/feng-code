package cn.lzscxb.business.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cn.lzscxb.common.config.DockerConfig;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import cn.lzscxb.common.utils.docker.DockerClientUtils;
import cn.lzscxb.common.utils.file.FreeMarkerUtils;
import cn.lzscxb.common.utils.file.SftpUtils;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemQueueMapper;
import cn.lzscxb.domain.entity.FengProblemQueue;
import cn.lzscxb.business.service.IFengProblemQueueService;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
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
    private DockerConfig dockerConfig;

    @Autowired
    private DockerClientUtils dockerClientUtils;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SftpUtils sftpUtils;

    public void excuteJavaQueue(long id, long problemId) {
        Long userId = 1L;
//        Long userId = SecurityUtils.getUserId();
        // 临时目录
        String workDir = String.format("%sfengcode/u%d-i%d-p%d/", System.getProperty("java.io.tmpdir"), userId, id, problemId);
        // Dockerfile 是保存在本地的，为了方便测使用，和上传目录分开写
        String dockerfileName = workDir + "Dockerfile";

        String javaCodeDir = workDir + "java";
        File javaCodeDirFile = new File(javaCodeDir);
        if (!javaCodeDirFile.exists()) {
            if (!javaCodeDirFile.mkdirs()) {
                throw new RuntimeException("创建代码目录失败, " + javaCodeDir);
            }
        }
        log.info("复制文件中");
        // 先将resource 中的代码复制到指指定目录
        FreeMarkerUtils.BatCopyFileFromJar("/template/code/java", javaCodeDir);
        FreeMarkerUtils.CopyFileFromJar("/template/docker/java.tpl", dockerfileName);

        log.info("编译镜像中");
        String imageId = dockerClientUtils.buildImage("test", new File(dockerfileName));

        // 创建容器
        log.info("创建容器中");
        CreateContainerResponse container = dockerClientUtils.createContainer(imageId);

        // 启动容器
        log.info("启动容器，id:{}", container.getId());
        dockerClientUtils.startContainer(container.getId());
        // 查看容器启动日志
        String containerLogs = dockerClientUtils.getContainerLogs(container.getId());
        log.info("日志内容：{}", containerLogs);

    }

    public void excuteQueue1111(long id) {

        System.out.println("excuteId:" + id);
        Resource resource = resourceLoader.getResource("classpath:template/docker/java.tpl");

        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TemplateEngine engine = new TemplateEngine();
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:/template/docker/");
        templateResolver.setSuffix(".tpl");
        templateResolver.setCacheable(false);
        engine.setTemplateResolver(templateResolver);
        Context context = new Context();
        FileWriter writer = null;
        try {
            writer = new FileWriter("z:/java.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        context.setVariable("hello", "我去,太赞了");
        engine.process("java", context, writer);
        String test = dockerClientUtils.buildImage("test", file);
        dockerClientUtils.startContainer(test);
        System.out.println("id === " + test);

    }


    /**
     * 查询任务管理
     *
     * @param id 任务管理主键
     * @return 任务管理
     */
    @Override
    public FengProblemQueue selectFengProblemQueueById(Long id) {
        return fengProblemQueueMapper.selectFengProblemQueueById(id);
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
        fengProblemQueue.setCreateTime(DateUtils.getNowDate());
        fengProblemQueue.setUserId(SecurityUtils.getUserId());
        return fengProblemQueueMapper.insertFengProblemQueue(fengProblemQueue);
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
}
