package cn.lzscxb.business.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import cn.lzscxb.common.utils.DateUtils;
import cn.lzscxb.common.utils.SecurityUtils;
import cn.lzscxb.common.utils.docker.DockerClientUtils;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import cn.lzscxb.business.mapper.FengProblemQueueMapper;
import cn.lzscxb.domain.entity.FengProblemQueue;
import cn.lzscxb.business.service.IFengProblemQueueService;
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
@Service
public class FengProblemQueueServiceImpl implements IFengProblemQueueService 
{
    @Autowired
    private FengProblemQueueMapper fengProblemQueueMapper;

    @Autowired
    private DockerClientUtils dockerClientUtils;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationContext applicationContext;

    public void excuteQueue(long id) {
        // 查看运行的容器
        List<Container> dockerContaineList = dockerClientUtils.getDockerContaineList();
        System.out.println(dockerContaineList);

        // 创建容器
        CreateContainerResponse container = dockerClientUtils.createContainer("hello-world");
        // 启动容器
        dockerClientUtils.startContainer(container.getId());
        // 查看容器启动日志
        String containerLogs = dockerClientUtils.getContainerLogs(container.getId());
        System.out.println(containerLogs);

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
    public FengProblemQueue selectFengProblemQueueById(Long id)
    {
        return fengProblemQueueMapper.selectFengProblemQueueById(id);
    }

    /**
     * 查询任务管理列表
     * 
     * @param fengProblemQueue 任务管理
     * @return 任务管理
     */
    @Override
    public List<FengProblemQueue> selectFengProblemQueueList(FengProblemQueue fengProblemQueue)
    {
        return fengProblemQueueMapper.selectFengProblemQueueList(fengProblemQueue);
    }

    /**
     * 新增任务管理
     * 
     * @param fengProblemQueue 任务管理
     * @return 结果
     */
    @Override
    public int insertFengProblemQueue(FengProblemQueue fengProblemQueue)
    {
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
    public int updateFengProblemQueue(FengProblemQueue fengProblemQueue)
    {
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
    public int deleteFengProblemQueueByIds(Long[] ids)
    {
        return fengProblemQueueMapper.deleteFengProblemQueueByIds(ids);
    }

    /**
     * 删除任务管理信息
     * 
     * @param id 任务管理主键
     * @return 结果
     */
    @Override
    public int deleteFengProblemQueueById(Long id)
    {
        return fengProblemQueueMapper.deleteFengProblemQueueById(id);
    }
}
