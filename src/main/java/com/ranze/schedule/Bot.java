package com.ranze.schedule;

import com.baidu.dueros.bot.BaseBot;
import com.baidu.dueros.data.request.IntentRequest;
import com.baidu.dueros.data.request.LaunchRequest;
import com.baidu.dueros.data.request.SessionEndedRequest;
import com.baidu.dueros.data.response.OutputSpeech;
import com.baidu.dueros.data.response.card.TextCard;
import com.baidu.dueros.model.Response;
import com.ranze.schedule.config.BusinessConfig;
import com.ranze.schedule.pojo.UserInfo;
import com.ranze.schedule.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class Bot extends BaseBot {
    @Autowired
    protected BusinessConfig config;
    @Autowired
    UserInfoService userInfoService;


    public Bot(HttpServletRequest request) throws IOException {
        super(request);
    }


    @Override
    protected Response onLaunch(LaunchRequest launchRequest) {
        log.info("Launch request, req id = {}, user id = {}", launchRequest.getRequestId(), getUserId());

        TextCard textCard = new TextCard("宝贝日程");
        textCard.addCueWord("创建宝贝的日程");

        // 新建返回的语音内容
        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "欢迎来到宝贝日程," +
                " 告诉我你的名字吧");

        // 构造返回的Response
        return new Response(outputSpeech, textCard);
    }

    @Override
    protected Response onInent(IntentRequest intentRequest) {
        String intentName = intentRequest.getIntentName();
        log.info("Intent request, req id = {}, user id = {}, intent = {}", intentRequest.getRequestId(), getUserId(),
                intentName);
        Response response = null;
        String createBabyNameIntent = config.getCreateBabyNameIntent();
        System.out.println("create baby name intent = " + createBabyNameIntent);
        System.out.println("create_baby_name".equals(createBabyNameIntent));
        if (intentName.equals("create_baby_name")) {
            response = handleCreateBabyNameIntent();
        }
        return response;
    }

    @Override
    protected Response onSessionEnded(SessionEndedRequest sessionEndedRequest) {
        log.info("Session end, req id =  {}, user id = {} ", sessionEndedRequest.getRequestId(), getUserId());
        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "再见，记得来打卡哦");
        return new Response(outputSpeech);
    }

    private Response handleCreateBabyNameIntent() {
        log.info("Handle create baby name intent");

        OutputSpeech outputSpeech = null;
        TextCard textCard = new TextCard();

        UserInfo userInfo = userInfoService.getUserInfo(getUserId());
        if (userInfo != null) {
            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "你已经有名字啦");
        } else {
            String nickName = getSlot("sys.wildcard-slot");
            log.info("Nick name: {}", nickName);
            userInfoService.insertUserInfo(getUserId(), nickName);

            textCard.setContent("好的, " + nickName + ", 你现在可以创建你的计划了");

            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText,
                    "好的, " + nickName + ", 你现在可以创建你的计划了");
        }
        return new Response(outputSpeech, textCard);
    }

}
