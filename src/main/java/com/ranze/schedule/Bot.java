package com.ranze.schedule;

import com.baidu.dueros.bot.BaseBot;
import com.baidu.dueros.data.request.IntentRequest;
import com.baidu.dueros.data.request.LaunchRequest;
import com.baidu.dueros.data.request.SessionEndedRequest;
import com.baidu.dueros.data.response.OutputSpeech;
import com.baidu.dueros.data.response.Reprompt;
import com.baidu.dueros.data.response.card.TextCard;
import com.baidu.dueros.model.Response;
import com.baidu.dueros.nlu.ConfirmationStatus;
import com.baidu.dueros.nlu.Intent;
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
        Response response = null;

        UserInfo userInfo = getUserInfo(getUserId());
        if (userInfo == null) {
            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "欢迎来到宝贝日程，先告诉我你的名字吧");
            setSessionAttribute(Cons.ATTRI_KEY_ACTION, Cons.ATTRI_SET_NAME);

        } else {
            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText,
                    userInfo.getNickName() + "你好, 你是要打卡还是创建日程呢");

        }
        setExpectSpeech(true);
        // 构造返回的Response
        return new Response(outputSpeech, textCard, new Reprompt(outputSpeech));
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
        log.info("Intent request, user id = {}, intent = {}, raw word = {}", getUserId(),
                intentName, intentRequest.getQuery().getOriginal());

        Response response = null;
        setExpectSpeech(true);

        if (intentName.equals(config.getCreateBabyNameIntent())) {
            response = handleCreateBabyNameIntent(null);
        } else if (intentName.equals(config.getCreateScheduleIntent())) {
            response = handleCreateScheduleIntent();
        } else {
            response = handleDefaultIntent(intentRequest);
        }

        if (response == null) {
            response = new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText, "我听不懂呢"));
        }
        return response;
    }

    private Response handleCreateScheduleIntent() {
        log.info("Handle create schedule intent");
        OutputSpeech outputSpeech = new OutputSpeech();
        Response response = null;

        String taskType = getSlot(config.getTaskTypeSlot());
        String timeLimitation = getSlot(config.getTaskLimitationSlot());
        String timeInDayStart = getSlot(config.getTaskInDayStartTimeSlot());
        String timeInDayEnd = getSlot(config.getTaskInDayEndTimeSlot());
        String onceTaskSlot = getSlot(config.getOnceTaskDateSlot());

        String taskContent = getSlot(config.getWildCardSlot());
        log.info("task type: {}", taskType);
        log.info("time limitation: {}", timeLimitation);
        log.info("time in day start: {}", timeInDayStart);
        log.info("time in day end: {}", timeInDayEnd);
        log.info("once task date: {}", onceTaskSlot);
        log.info("task content: {}", taskContent);

        if (Strings.isEmpty(taskType)) {
            ask(config.getTaskTypeSlot());
            outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
            outputSpeech.setText("你想要创建一次性任务还是长期任务呢？");
            response = new Response(outputSpeech);
            return response;
        }

        if (taskType.equals(Cons.TASK_ONCE_STR)) {
            if (Strings.isEmpty(onceTaskSlot)) {
                ask(config.getOnceTaskDateSlot());
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("哪一天执行任务呢，你可以这样说，今天、明天、下周一");
                response = new Response(outputSpeech);
                return response;
            }

            if (Strings.isEmpty(timeInDayStart)) {
                ask("sys.time1");
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("你想在几点开始任务呢，你可以说，上午10点");
                response = new Response(outputSpeech);
                return response;
            }

            if (Strings.isEmpty(taskContent)) {
                ask(config.getWildCardSlot());
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("告诉我你的任务内容吧，例如：背单词、练字等");
                response = new Response(outputSpeech);
                return response;
            }

            Intent intent = getIntent();
            ConfirmationStatus confirmationStatus = intent.getConfirmationStatus();
            if (confirmationStatus == ConfirmationStatus.CONFIRMED) {
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("好的，已经为你指定好计划了");
            } else if (confirmationStatus == ConfirmationStatus.NONE) {
                setConfirmIntent();
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("你制定的任务是" + timeInDayStart + "执行任务" + taskContent + "，确认吗?");
            } else {
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("好的，任务已经取消");
            }

        } else if (taskType.equals(Cons.TASK_LONG_STR)) {

        }

        return new Response(outputSpeech);

    }

    private Response handleDefaultIntent(IntentRequest intentRequest) {
        // 默认意图
        log.info("Handle default intent");

        Response response = new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText, "我没有听懂呢"));

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
