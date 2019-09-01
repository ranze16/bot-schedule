package com.ranze.schedule.config;

import com.ranze.schedule.Bot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class BeanConfig {
    @Bean
    @Scope("prototype")
    public Bot bot(HttpServletRequest request) throws IOException {
        return new Bot(request);
    }

}
