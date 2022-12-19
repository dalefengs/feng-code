package cn.lzscxb.common.file;

import cn.lzscxb.common.utils.file.SftpUtils;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Vector;

/**
 * @Auther: Likfees
 * @Date: 2022/12/13 15:12
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SftpUtilsTest {

    @Autowired
    private SftpUtils sftpUtils;

    @Test
    public void testUpload() throws JSchException, SftpException, FileNotFoundException {
        sftpUtils.login();
        sftpUtils.uploadDirectory("/root/temptest","D:\\Code\\Java\\feng-code\\feng-code-server\\feng-code-business\\src\\main\\resources\\template");
        sftpUtils.logout();
    }


    @Test
    public void testListFiles() throws Exception {
        sftpUtils.login();
        Vector lsEntries = sftpUtils.listFiles("/root");
        sftpUtils.logout();
    }

}
