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
import org.apache.logging.log4j.util.Strings;
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

        TextCard textCard = new TextCard("欢迎来到宝贝日程");
        OutputSpeech outputSpeech = null;

        UserInfo userInfo = getUserInfo(getUserId());
        if (userInfo == null) {
            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "欢迎来到宝贝日程，先告诉我你的名字吧");
            setSessionAttribute(Cons.ATTRI_KEY_ACTION, Cons.ATTRI_SET_NAME);

        } else {
            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText,
                    userInfo.getNickName() + "你好, 你是要打卡还是创建日程呢");
        }

        // 构造返回的Response
        return new Response(outputSpeech, textCard);
    }

    @Override
    protected Response onSessionEnded(SessionEndedRequest sessionEndedRequest) {
        log.info("Session end, req id =  {}, user id = {} ", sessionEndedRequest.getRequestId(), getUserId());
        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "再见，记得来打卡哦");
        return new Response(outputSpeech);
    }


    @Override
    protected Response onInent(IntentRequest intentRequest) {
        String intentName = intentRequest.getIntentName();
        log.info("Intent request, user id = {}, intent = {}, raw word = {}", intentRequest.getRequestId(), getUserId(),
                intentName, intentRequest.getQuery());

        Response response = null;

        String createBabyNameIntent = config.getCreateBabyNameIntent();
        System.out.println("create baby name intent = " + createBabyNameIntent);
        System.out.println("create_baby_name".equals(createBabyNameIntent));

        switch (intentName) {
            case "create_baby_name":
                response = handleCreateBabyNameIntent(null);
                break;
            case "create_baby_schedule":
                response = handleCreateScheduleIntent();
                break;
            case Cons.DEFAULT_INTENT:
                response = handleDefaultIntent(intentRequest);
                break;
            default:
                response = handleDefaultIntent(intentRequest);
                break;
        }

        return response == null ? onDefaultEvent() : response;
    }

    private Response handleCreateScheduleIntent() {
        Response response = null;
//        getSlot("")
        return response;
    }

    private Response handleDefaultIntent(IntentRequest intentRequest) {
        // 默认意图
        log.info("Handle default intent");

        Response response = null;

        String action = getSessionAttribute(Cons.ATTRI_KEY_ACTION);
        if (action.equals(Cons.ATTRI_SET_NAME)) {
            response = handleCreateBabyNameIntent(intentRequest.getQuery().getOriginal());
            setSessionAttribute(Cons.ATTRI_KEY_ACTION, "");
        }
        return response;
    }

    private Response handleCreateBabyNameIntent(String nickName) {
        log.info("Handle create baby name intent");

        OutputSpeech outputSpeech = null;
        TextCard textCard = new TextCard();

        UserInfo userInfo = getUserInfo(getUserId());
        if (userInfo != null) {
            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "你已经有名字啦");
        } else {
            nickName = Strings.isEmpty(nickName) ? getSlot("sys.wildcard-slot") : nickName;
            log.info("Nick name: {}", nickName);
            userInfoService.insertUserInfo(getUserId(), nickName);

            textCard.setContent("好的, " + nickName + ", 你现在可以创建你的计划了");

            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText,
                    "好的, " + nickName + ", 你现在可以创建你的计划了");
        }
        return new Response(outputSpeech, textCard);
    }

    private UserInfo getUserInfo(String userId) {
        return userInfoService.getUserInfo(userId);
    }

}
