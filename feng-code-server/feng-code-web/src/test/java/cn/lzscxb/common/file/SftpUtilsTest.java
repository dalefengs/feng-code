package cn.lzscxb.common.file;

import cn.lzscxb.common.utils.file.SftpUtils;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
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
    public void testExec() throws JSchException {
        sftpUtils.connect();
        sftpUtils.execCmd("ls");
        sftpUtils.close();
    }

    @Test
    public void testUpload() throws JSchException, SftpException, FileNotFoundException {
        sftpUtils.connect();

        sftpUtils.upload("D:\\Code\\Java\\feng-code\\feng-code-server\\feng-code-web\\src\\main\\resources\\banner.txt", "/root");

        sftpUtils.close();
    }


    @Test
    public void testListFiles() throws Exception {
        sftpUtils.connect();
        Vector lsEntries = sftpUtils.listFiles("/root");
        System.out.println(lsEntries);
        sftpUtils.close();
    }

}
