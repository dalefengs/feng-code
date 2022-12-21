package cn.lzscxb.common.utils.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.async.ResultCallbackTemplate;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.github.dockerjava.api.model.*;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.Port;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Likfees
 * @Date: 2022/12/9 17:25
 * @Description: Docker Java Api
 */
@Component
public class DockerClientUtils {


    @Autowired
    private DockerClient dockerClient;


    /**
     * 通过 Dockerfile 文件编译镜像文件
     * List<String> dockerfile = Arrays.asList(
     * "FROM alpine:3.2",
     * "RUN echo 'hello from Docker build process'",
     * "CMD yes"
     * );
     *
     * @param: 镜像名称, Dockerfile 文件路径
     * @return: 镜像Id
     */
    public String buildImage(String imageName, File dockerfile) {
        String imageTag = "v1";
        ImmutableSet<String> tagSet = ImmutableSet.of(imageName + ":" + imageTag);
        String imageId = this.dockerClient.buildImageCmd(dockerfile)
                .withTags(tagSet)
                .start()
                .awaitImageId();
        return imageId;
    }

    public DockerClient getDockerClient() {
        return dockerClient;
    }

    /**
     * 创建容器
     *
     * @param: 镜像名称
     * @return: CreateContainerResponse
     */
    public CreateContainerResponse createContainer(String imageName) {
        try {
            dockerClient.inspectImageCmd(imageName).exec();
        } catch (NotFoundException e) {
            System.out.printf("%s 镜像不存在，正在拉取... \n", imageName);
            if (pullImage(imageName)) {
                throw new RuntimeException(String.format("%s 拉取镜像失败, err_msg: %s", imageName, e.getMessage()));
            }
        }
        // 创建容器需要使用的命令
        return dockerClient.createContainerCmd(imageName).exec();

    }


    /**
     * 创建容器-并映射端口
     *
     * @param: 镜像名称，端口映射列表
     * @return: CreateContainerResponse
     */
    public CreateContainerResponse createContainer(String imageName, Map<String, Port> portMap) {
        try {
            dockerClient.inspectImageCmd(imageName).exec();
        } catch (NotFoundException e) {
            System.out.printf("%s 镜像不存在，正在拉取... \n", imageName);
            if (pullImage(imageName)) {
                throw new RuntimeException(String.format("%s 拉取镜像失败, err_msg: %s", imageName, e.getMessage()));
            }
        }
        // 创建容器需要使用的命令
        CreateContainerCmd ccm = dockerClient.createContainerCmd(imageName);
        // 封装端口映射
        List<PortBinding> list = new ArrayList<>();
        for (String hostPort : portMap.keySet()) {
            // 暴露端口
            ccm = ccm.withExposedPorts(ExposedPort.parse(portMap.get(hostPort) + "/tcp"));
            // 绑定主机端⼝ -> docker容器端⼝
            list.add(PortBinding.parse(hostPort + ":" + portMap.get(hostPort)));
        }
        HostConfig hostConfig = HostConfig.newHostConfig()
                .withPortBindings(list);
        // 执行创建命令

        return ccm
                .withHostConfig(hostConfig)
                .exec();
    }

    /**
     * 拉取镜像
     *
     * @param: 镜像名称
     * @return:
     */
    public Boolean pullImage(String imageName) {
        try {
            return dockerClient.pullImageCmd(imageName)
                    .start()
                    .awaitCompletion(40, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println(imageName + "拉取镜像失败：" + e.getMessage());
            return false;
        }

    }

    /**
     * 获取容器日志
     *
     * @param: 容器Id
     * @return:
     */
    public String getContainerLogs(String id) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            this.dockerClient.logContainerCmd(id)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .withSince(0)
                    .exec(new ResultCallbackTemplate<ResultCallback<Frame>, Frame>() {
                        @Override
                        public void onNext(Frame frame) {
                            stringBuffer.append(new String(frame.getPayload()));
                        }
                    }).awaitCompletion();
        } catch (InterruptedException e) {
            throw new RuntimeException(id + " ,获取日志失败:" + e.getMessage());
        }
        return stringBuffer.toString();
    }

    /**
     * 获取所有容器列表
     *
     * @return: List<Container>
     */
    public List<Container> getDockerContaineList() {
        return this.dockerClient.listContainersCmd().exec();
    }


    /**
     * 启动容器
     *
     * @param: 容器Id
     */
    public void startContainer(String id) {
        this.dockerClient.startContainerCmd(id).exec();
    }

    /**
     * 停止容器
     *
     * @param: 容器Id
     */
    public void stopContainer(String id) {
        this.dockerClient.stopContainerCmd(id).exec();
    }

    /**
     * 重启容器
     *
     * @param: 容器Id
     */
    public void restartContainer(String id) {
        this.dockerClient.restartContainerCmd(id).exec();
    }

    /**
     * 删除容器
     *
     * @param: 容器Id
     */
    public void deleteContainer(String id) {
        try {
            // 先关闭在删除
            this.stopContainer(id);
        } catch (NotModifiedException ignored) {

        }
        this.dockerClient.removeContainerCmd(id).exec();
    }


}
