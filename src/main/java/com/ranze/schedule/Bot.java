package com.ranze.schedule;

import com.baidu.dueros.bot.BaseBot;
import com.baidu.dueros.data.request.IntentRequest;
import com.baidu.dueros.data.request.LaunchRequest;
import com.baidu.dueros.data.request.SessionEndedRequest;
import com.baidu.dueros.data.request.events.ElementSelectedEvent;
import com.baidu.dueros.data.response.OutputSpeech;
import com.baidu.dueros.data.response.Reprompt;
import com.baidu.dueros.data.response.card.TextCard;
import com.baidu.dueros.data.response.directive.display.Hint;
import com.baidu.dueros.data.response.directive.display.RenderTemplate;
import com.baidu.dueros.data.response.directive.display.templates.ListItem;
import com.baidu.dueros.data.response.directive.display.templates.ListTemplate2;
import com.baidu.dueros.model.Response;
import com.baidu.dueros.nlu.ConfirmationStatus;
import com.baidu.dueros.nlu.Intent;
import com.ranze.schedule.config.BusinessConfig;
import com.ranze.schedule.pojo.Task;
import com.ranze.schedule.pojo.UserInfo;
import com.ranze.schedule.service.TaskService;
import com.ranze.schedule.service.UserInfoService;
import com.ranze.schedule.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class Bot extends BaseBot {
    @Autowired
    CachedData cachedData;
    @Autowired
    DateUtil dateUtil;
    @Autowired
    protected BusinessConfig config;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    TaskService taskService;


    public Bot(HttpServletRequest request) throws IOException {
        super(request);
    }


    @Override
    protected Response onLaunch(LaunchRequest launchRequest) {
        log.info("Launch request, req id = {}, user id = {}", launchRequest.getRequestId(), getUserId());

        TextCard card = null;
        OutputSpeech outputSpeech = new OutputSpeech();

        UserInfo userInfo = getUserInfo(getUserId());
        if (userInfo == null) {
            card = new TextCard("宝宝日程可以帮你管理的任务");
            outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, "欢迎来到宝贝日程，先告诉我你的名字吧");
            setExpectSpeech(true);
            setSessionAttribute(Cons.ATTRI_KEY_ACTION, Cons.ATTRI_SET_NAME);
        } else {
            cachedData.userInfoMap.put(getUserId(), userInfo);

            String outputSpeechStr = null;
            List<Task> todayTasks = taskService.selectTodayTasks(userInfo.getUserId());
            if (todayTasks.isEmpty()) {
                card = new TextCard("快来创建任务吧");
                card.addCueWord("创建日程");
                outputSpeechStr = "你今天没有任务哦";
            } else {
                ListTemplate2 listTemplate = new ListTemplate2();
                listTemplate.setTitle("当前任务");
                listTemplate.setToken("token");
                for (Task task : todayTasks) {
                    ListItem listItem = getListItem(task);

                    listTemplate.addListItem(listItem);
                }
                outputSpeechStr = "你今天有" + todayTasks.size() + "个任务, 记得打卡哦";

                RenderTemplate renderTemplate = new RenderTemplate(listTemplate);
                this.addDirective(renderTemplate);
            }

            ArrayList<String> hints = new ArrayList<>();
            hints.add("创建任务");
            hints.add("我要打卡");
            hints.add("删除任务");
            Hint hint = new Hint(hints);

            // 添加返回的指令
            this.addDirective(hint);

            outputSpeech.setText(outputSpeechStr);
        }
        // 构造返回的Response
        if (card != null) {
            return new Response(outputSpeech, card, new Reprompt(outputSpeech));
        } else {
            return new Response(outputSpeech);
        }
    }

    private ListItem getListItem(Task task) {
        ListItem listItem = new ListItem();
        Date timeInDayStart = task.getTimeInDayStart();
        Date timeInDayEnd = task.getTimeInDayEnd();
        listItem.setPlainPrimaryText(
                dateUtil.convertDate(timeInDayStart) + "~" + dateUtil.convertDate(timeInDayEnd))
                .setPlainSecondaryText(task.getContent())
                .setPlainTertiaryText(taskService.getTaskState(task, Cons.HALF_HOUR_MILLIONS))
                .setImageUrl("https://skillstore.cdn.bcebos.com/icon/100/c709eed1-c07a-be4a-b242-0b0d8b777041.jpg");
        return listItem;
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
        } else if (intentName.equals(Cons.INTENT_CLOCK_IN)) {
            response = handleClockInIntent();
        } else if (intentName.equals(Cons.INTENT_BIND_TASK)) {
            response = handleBindTask();
        } else {
            response = handleDefaultIntent(intentRequest);
        }

        if (response == null) {
            response = new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText, "我听不懂呢"));
        }
        return response;
    }

    private Response handleBindTask() {
        setExpectSpeech(true);
        Response response = null;
        OutputSpeech outputSpeech = new OutputSpeech();
        outputSpeech.setType(OutputSpeech.SpeechType.PlainText);

        String taskIdStr = getSessionAttribute(Cons.ATTRI_KEY_TASK_ID);
        if (taskIdStr == null) {
            return response;
        }

        long masterTaskId = Long.valueOf(taskIdStr);
        String bindTaskTimeInDayStart = getSlot(Cons.SLOT_BIND_TASK_TIME_IN_DAY_START);
        String bindTaskTimeInDayEnd = getSlot(Cons.SLOT_BIND_TASK_TIME_IN_DAY_END);
        String bindTaskOwner = getSlot(Cons.SLOT_BIND_TASK_OWNER);
        String bindTaskContent = getSlot(Cons.SLOT_BIND_TASK_CONTENT);

        log.info("Bind task time in day start: {}", bindTaskTimeInDayStart);
        log.info("Bind task time in day end: {}", bindTaskTimeInDayEnd);
        log.info("Bind task owner: {}", bindTaskOwner);
        log.info("Bind task content: {}", bindTaskContent);

        if (Strings.isEmpty(bindTaskOwner)) {
            ask(Cons.SLOT_BIND_TASK_OWNER);
            outputSpeech.setText("请问你要绑定谁的任务呢？");
            response = new Response(outputSpeech, null, new Reprompt(outputSpeech));
            return response;
        }
        if (Strings.isEmpty(bindTaskTimeInDayStart)) {
            ask(Cons.SLOT_BIND_TASK_TIME_IN_DAY_START);
            outputSpeech.setText("任务的开始时间是什么呢，例如上午10点，下午2点");
            response = new Response(outputSpeech, null, new Reprompt(outputSpeech));
            return response;
        }
        if (Strings.isEmpty(bindTaskTimeInDayEnd)) {
            ask(Cons.SLOT_BIND_TASK_TIME_IN_DAY_END);
            outputSpeech.setText("任务的结束时间是什么呢，例如上午11点，下午3点");
            response = new Response(outputSpeech, null, new Reprompt(outputSpeech));
            return response;
        }
        if (Strings.isEmpty(bindTaskContent)) {
            ask(Cons.SLOT_BIND_TASK_CONTENT);
            outputSpeech.setText("你的任务内容是什么呢，例如看书，写日记等");
            response = new Response(outputSpeech, null, new Reprompt(outputSpeech));
            return response;
        }

        Task task = taskService.selectTaskById(masterTaskId);
        if (task != null) {
            Task bindTask = taskService.insertBindTask(bindTaskOwner, dateUtil.convertTime(bindTaskTimeInDayStart),
                    dateUtil.convertTime(bindTaskTimeInDayEnd), bindTaskContent, task);
            if (bindTask != null) {
                setExpectSpeech(false);
                outputSpeech.setText("好的, 绑定的任务创建成功了，记得和宝贝一起打卡哦");
                response = new Response(outputSpeech, null);
            } else {
                setExpectSpeech(false);
                outputSpeech.setText("哎呀，我遇到了点问题");
                response = new Response(outputSpeech, null);
            }
        }

        return response;
    }

    private Response handleClockInIntent() {
        List<Task> tasks = taskService.selectCurrentNearbyTasks(getUserId(), Cons.HALF_HOUR_MILLIONS);
        if (tasks.isEmpty()) {
            TextCard card = new TextCard("当前没有可以打卡的任务哦");
            return new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText, "当前没有可以打卡的任务哦"), card);
        }
        setSessionAttribute(Cons.ATTRI_KEY_ACTION, Cons.ATTRI_CLOCK_IN);

        String outputSpeechStr = null;
        ListTemplate2 listTemplate2 = new ListTemplate2();
        for (Task task : tasks) {
            ListItem listItem = getListItem(task);
            listItem.setToken(task.getId() + "");
            listTemplate2.addListItem(listItem);
        }
        if (tasks.size() == 1) {
            outputSpeechStr = "请问这是你当前可要打卡的任务，对我说我要打卡";
        } else {
            outputSpeechStr = "你当前有" + tasks.size() + "个任务, 你要打卡第几个呢？";
        }
        RenderTemplate renderTemplate = new RenderTemplate(listTemplate2);
        this.addDirective(renderTemplate);
        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, outputSpeechStr);
        return new Response(outputSpeech, null, new Reprompt(outputSpeech));

    }

    @Override
    protected Response onElementSelectedEvent(ElementSelectedEvent elementSelectedEvent) {

        String action = getSessionAttribute(Cons.ATTRI_KEY_ACTION);
        if (action != null && action.equals(Cons.ATTRI_CLOCK_IN)) {
            String token = elementSelectedEvent.getToken();
            long taskId = Long.valueOf(token);
            log.info("打开任务 id: {}", taskId);
            if (Strings.isEmpty(token)) {
                return super.onElementSelectedEvent(elementSelectedEvent);
            } else {
                UserInfo userInfo = cachedData.userInfoMap.get(getUserId());
                boolean success = taskService.insertClockInToday(userInfo, Long.valueOf(token));
                if (success) {
                    String outputSpeechStr = null;
                    Task task = taskService.selectTaskById(taskId);
                    Long bindTaskId = task.getBindTaskId();
                    log.info("任务 id: {}, 绑定的任务 id:{}", task, bindTaskId);
                    List<Long> clockInTaskIds = taskService.selectClockInTaskIds(getUserId(), dateUtil.today());
                    if (clockInTaskIds.contains(bindTaskId)) {
                        boolean increPointsRet = userInfoService.incrementPoints(userInfo, Cons.POINTS_BONUSES_DOUBLE_CLOCK_IN);
                        if (increPointsRet) {
                            outputSpeechStr = "太棒了，两个绑定的任务都打卡成功, 额外奖励"
                                    + Cons.POINTS_BONUSES_DOUBLE_CLOCK_IN
                                    + "朵爱心哦";
                        } else {
                            outputSpeechStr = "太棒了，两个绑定的任务都打卡成功";
                        }
                    } else {
                        outputSpeechStr = "打卡成功，这个任务还有一个绑定的任务没有打卡，两个任务都打卡成功可以获取额外的爱心哦";
                    }
                    return new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText, outputSpeechStr));
                }
            }

        }

        return super.onElementSelectedEvent(elementSelectedEvent);
    }

    private Response handleCreateScheduleIntent() {
        log.info("Handle create schedule intent");
        OutputSpeech outputSpeech = new OutputSpeech();
        Response response = null;

        String taskType = getSlot(config.getTaskTypeSlot());
        String timeLimitation = getSlot(config.getTaskLimitationSlot());
        String timeInDayStart = getSlot(config.getTaskInDayStartTimeSlot());
        String timeInDayEnd = getSlot(config.getTaskInDayEndTimeSlot());
        String onceTaskDateSlot = getSlot(config.getOnceTaskDateSlot());

        String taskContent = getSlot(config.getWildCardSlot());
        log.info("task type: {}", taskType);
        log.info("time limitation: {}", timeLimitation);
        log.info("time in day start: {}", timeInDayStart);
        log.info("time in day end: {}", timeInDayEnd);
        log.info("once task date: {}", onceTaskDateSlot);
        log.info("task content: {}", taskContent);

        if (Strings.isEmpty(taskType)) {
            ask(config.getTaskTypeSlot());
            outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
            outputSpeech.setText("你想要创建一次性任务还是长期任务呢？");
            response = new Response(outputSpeech);
            return response;
        }

        if (taskType.equals(Cons.TASK_ONCE_STR)) {
            if (Strings.isEmpty(onceTaskDateSlot)) {
                ask(config.getOnceTaskDateSlot());
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("哪一天执行任务呢，你可以这样说，今天、明天、下周一");
                response = new Response(outputSpeech);
                return response;
            }

            if (Strings.isEmpty(timeInDayStart)) {
                ask("sys.time1");
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("你想在几点开始任务呢，你可以这样说，上午10点，下午2点");
                response = new Response(outputSpeech);
                return response;
            }
            if (Strings.isEmpty(timeInDayEnd)) {
                ask("sys.time2");
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("你想在几点结束任务呢，你可以这样说，上午10点，下午2点");
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
                Task task = taskService.insertOnceTask(getUserId(), Cons.BABY, dateUtil.convertDate(onceTaskDateSlot),
                        dateUtil.convertTime(timeInDayStart), dateUtil.convertTime(timeInDayEnd), taskContent, -1);
                if (task != null) {
                    setSessionAttribute(Cons.ATTRI_KEY_TASK_ID, task.getId() + "");
                    outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                    outputSpeech.setText("好的，已经为你制定好计划了");
                    setExpectSpeech(false);
                } else {
                    outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                    outputSpeech.setText("哎呀，我遇到点问题，没能为你保存任务");
                }
            } else if (confirmationStatus == ConfirmationStatus.NONE) {
                setConfirmIntent();
                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("你制定的任务是" + timeInDayStart + "到" + timeInDayEnd + "执行任务" + taskContent + "，确认吗?");
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
        if (action != null && action.equals(Cons.ATTRI_SET_NAME)) {
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
