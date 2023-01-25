package cl.sourcecode.kubernetes.microservice.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroserviceCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCourseApplication.class, args);
    }

}
