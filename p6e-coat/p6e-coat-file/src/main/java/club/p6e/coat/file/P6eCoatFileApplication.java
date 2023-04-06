package club.p6e.coat.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 应用入口
 *
 * @author lidahsuang
 * @version 1.0
 */
@EnableScheduling
@SpringBootApplication
public class P6eCoatFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(P6eCoatFileApplication.class, args);
    }

}
