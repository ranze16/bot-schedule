package com.ranze.schedule;

import com.ranze.schedule.config.BeanConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/schedule")
public class DuerOSController {
    @Autowired
    BeanConfig beanConfig;

    @PostMapping("/dueros")

    public void request(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Receive request: {}", request);
        Bot bot;
        try {
            bot = beanConfig.bot(request);
            System.out.println(bot.toString());
            // 线下调试时，可以关闭签名验证
            // bot.enableVerify();
            bot.disableVerify();

            // 调用bot的run方法
            String responseJson = bot.run();
            // 设置response的编码UTF-8
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            // 返回response
            log.info("Response: {}", responseJson);
            response.getWriter().append(responseJson);

            // 打开签名验证
            // bot.enableVerify();

        } catch (Exception e) {
            log.warn("Bot exception: {}", e.getMessage());
            response.getWriter().append(e.toString());
        }
    }


}
