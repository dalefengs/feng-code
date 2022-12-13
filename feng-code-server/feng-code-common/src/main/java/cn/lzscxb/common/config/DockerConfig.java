package cn.lzscxb.common.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Auther: Likfees
 * @Date: 2022/12/13 09:22
 * @Description:
 */


@Data
@Configuration
public class DockerConfig {

    @Value("${docker-config.host}")
    private String dockerHost;
    @Value("${docker-config.port}")
    private int dockerPort;

    @Value(("${docker-config.isRemote}"))
    private boolean isRemote;

    @Value(("${docker-config.uploadPath}"))
    private String uploadPath;

    @Value(("${docker-config.username}"))
    private String username;

    @Value(("${docker-config.password}"))
    private String password;

    /**
     * 创建 DockerClient 对象
     *
     * @param:
     * @return: DockerClient
     */
    @Bean
    public DockerClient dockerClient() {
            DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(String.format("tcp://%s:%d", dockerHost, dockerPort))
                    .build();
            return DockerClientBuilder.getInstance(config).build();
    }

}
