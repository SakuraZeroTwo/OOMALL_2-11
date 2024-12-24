package cn.edu.xmu.oomall.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.oomall.comment", "cn.edu.xmu.javaee.core"})
@EnableDiscoveryClient
@EnableJpaRepositories
@EnableFeignClients(basePackages = "cn.edu.xmu.oomall.comment.mapper.openfeign")
public class CommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class, args);
        }
    }
