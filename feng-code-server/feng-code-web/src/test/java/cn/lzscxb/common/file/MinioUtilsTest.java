package cn.lzscxb.common.file;

import cn.lzscxb.common.utils.file.MinioUtils;
import cn.lzscxb.domain.model.MinioObjectItem;
import io.minio.MinioClient;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MinioUtilsTest {

    @Autowired
    private MinioUtils minioUtils;

    @Test
    public void TestMinioClient() {
        String upload = minioUtils.upload(getMultipartFile(new File("C:\\Users\\Likfees\\Pictures\\wallpaper\\1.png")));
        System.out.println(upload);
    }

    public static MultipartFile getMultipartFile(File file) {
        FileItem item = new DiskFileItemFactory().createItem("file"
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , true
                , file.getName());
        try (InputStream input = new FileInputStream(file);
             OutputStream os = item.getOutputStream()) {
            // 流转移
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(item);
    }

}
