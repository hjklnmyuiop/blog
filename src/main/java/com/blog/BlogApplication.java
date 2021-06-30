package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//加载mapper接口
@MapperScan("com.blog.dao")
public class BlogApplication {

    public static void main(String[] args) {
        //看这里，加上这句话
        SpringApplication.run(BlogApplication.class, args);

    }


}
