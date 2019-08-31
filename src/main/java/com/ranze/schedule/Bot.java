package com.ranze.schedule;

import com.baidu.dueros.bot.BaseBot;
import com.baidu.dueros.data.request.LaunchRequest;
import com.baidu.dueros.data.response.OutputSpeech;
import com.baidu.dueros.data.response.card.TextCard;
import com.baidu.dueros.model.Response;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class Bot extends BaseBot {


    protected Bot(HttpServletRequest request) throws IOException, JsonMappingException {
        super(request);
    }

    @Override
    protected Response onLaunch(LaunchRequest launchRequest) {
        log.info("Launch request: {}", launchRequest.getRequestId());

        TextCard textCard = new TextCard("宝贝日程");
        textCard.addCueWord("你可以创建你的日程");

        // 新建返回的语音内容
        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "欢迎使用宝贝日程");

        // 构造返回的Response
        Response response = new Response(outputSpeech, textCard);
        return response;

    }


}
