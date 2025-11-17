package kr.kro.photoliner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PhotoLinerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoLinerApplication.class, args);
    }

}
