package cn.lzscxb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 * 
 * @author Likfees
 */
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableTransactionManagement
public class FengCodeApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(FengCodeApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  FengCode 在线编程实践平台启动成功   ლ(´ڡ`ლ)ﾞ \n" +
                        "  _____                   ____          _      \n" +
                        " |  ___|__ _ __   __ _   / ___|___   __| | ___ \n" +
                        " | |_ / _ \\ '_ \\ / _` | | |   / _ \\ / _` |/ _ \\\n" +
                        " |  _|  __/ | | | (_| | | |__| (_) | (_| |  __/\n" +
                        " |_|  \\___|_| |_|\\__, |  \\____\\___/ \\__,_|\\___|\n" +
                        "                 |___/                         "
                );
    }
}
