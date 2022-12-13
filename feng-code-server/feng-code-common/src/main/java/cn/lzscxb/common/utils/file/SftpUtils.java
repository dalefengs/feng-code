package cn.lzscxb.common.utils.file;

import cn.lzscxb.common.config.DockerConfig;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * @Auther: Likfees
 * @Date: 2022/12/13 12:23
 * @Description: Sftp 工具类
 */
@Component
public class SftpUtils {

    @Autowired
    private DockerConfig dockerConfig;

    private Session session = null;

    public void connect() {

        JSch jSch = new JSch();
        try {
            session = jSch.getSession(dockerConfig.getUsername(), dockerConfig.getDockerHost());
            session.setPassword(dockerConfig.getPassword());
            Properties config = new Properties();
            // SSH 公钥检查机制 no、ask、yes
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);// 为Session对象设置properties
            session.setTimeout(30000);
            session.connect();// 通过Session建立连接
        } catch (JSchException e) {
            throw new RuntimeException(String.format("SFTP getSession() 失败! host: %s; username: %s, password: %s, err_msg: %s", dockerConfig.getDockerHost(), dockerConfig.getUsername(), dockerConfig.getPassword(), e.getMessage()));
        }

    }

    /**
     * 执行相关的命令,
     * 但是部分情况不可用
     * @param command 命令
     * @throws JSchException
     */
    public void execCmd(String command) throws JSchException {
        BufferedReader reader = null;

        Channel channel = null;
        try {
            if (command != null) {
                channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                // ((ChannelExec) channel).setErrStream(System.err);
                channel.connect();

                InputStream in = channel.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                String buf = null;
                while ((buf = reader.readLine()) != null) {
                    System.out.println(buf);
                }
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }



    /**
     * 上传文件
     *
     * @param uploadFile 要上传的文件
     * @param directory  上传的目录
     * @throws JSchException
     * @throws SftpException
     * @throws FileNotFoundException
     */
    public void upload(String uploadFile, String directory) throws JSchException, SftpException, FileNotFoundException {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(directory);
            File file = new File(uploadFile);
            channelSftp.put(new FileInputStream(file), file.getName());
        } finally {
            assert channelSftp != null;
            channelSftp.disconnect();
        }

    }

    /**
     * 下载文件
     *
     * @param src linux服务器文件地址
     * @param dst 本地存放地址
     * @throws JSchException
     * @throws SftpException
     */
    public void download(String src, String dst) throws Exception {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.get(src, dst);
        } finally {
            assert channelSftp != null;
            channelSftp.disconnect();
        }
    }

    /**
     * 查看路径下的所有文件
     *
     * @param path 路径
     */
    public Vector listFiles(String path) throws Exception {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            return channelSftp.ls(path);
        } finally {
            assert channelSftp != null;
            channelSftp.disconnect();
        }
    }


    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @throws SftpException
     * @throws JSchException
     */
    public void delete(String directory, String deleteFile) throws Exception {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(directory);
            channelSftp.rm(deleteFile);
        } finally {
            assert channelSftp != null;
            channelSftp.disconnect();
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (session != null) {
            session.disconnect();
        }
    }
}
