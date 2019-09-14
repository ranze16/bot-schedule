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
import com.baidu.dueros.data.response.directive.display.templates.*;
import com.baidu.dueros.model.Response;
import com.baidu.dueros.nlu.ConfirmationStatus;
import com.baidu.dueros.nlu.Intent;
import com.baidu.dueros.nlu.Slot;
import com.ranze.schedule.config.BusinessConfig;
import com.ranze.schedule.dueros.SelectSlot;
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

        String taskIdStr = getSessionAttribute(Cons.ATTRI_KEY_LAST_TASK_ID);
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
        List<Task> tasks = taskService.selectCurrentNearbyTasksWithoutClockIn(getUserId(), Cons.HALF_HOUR_MILLIONS);
        if (tasks.isEmpty()) {
            TextCard card = new TextCard("当前没有可以打卡的任务哦");
            setExpectSpeech(false);
            return new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText, "当前没有可以打卡的任务哦"), card);
        }

        String taskId = getSlot("app.task.id");
        if (!Strings.isEmpty(taskId) && getIntent().getConfirmationStatus() == ConfirmationStatus.CONFIRMED) {
            boolean success = taskService.insertClockInToday(cachedData.userInfoMap.get(getUserId()), Long.valueOf(taskId));
            if (success) {
                setExpectSpeech(false);
                return new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText,
                        "打卡成功，增加了" + Cons.POINTS_ONE_CLOCK_IN + "点爱心"));
            }
        }
        setSessionAttribute(Cons.ATTRI_KEY_ACTION, Cons.ATTRI_CLOCK_IN);

        String outputSpeechStr = null;

        ListTemplate2 listTemplate2 = new ListTemplate2();

        SelectSlot selectSlot = new SelectSlot();
        selectSlot.setSlotToSelect("app.task.id");
        List<SelectSlot.Option> options = new ArrayList<>();

        for (int i = 0; i < tasks.size(); ++i) {
            Task task = tasks.get(i);
            ListItem listItem = getListItem(task);
            listItem.setToken(task.getId() + "");
            listTemplate2.addListItem(listItem);

            SelectSlot.Option option = new SelectSlot.Option();
            option.setIndex(i + 1);
            option.setValue(task.getId() + "");

            options.add(option);
        }

        selectSlot.setOptions(options);

        if (tasks.size() == 1) {
            outputSpeechStr = "这是你当前可打卡的任务，确认打卡吗";
            Slot slot = Slot.newBuilder()
                    .setValue(tasks.get(0).getId() + "")
                    .setValues(new ArrayList<>())
                    .setScore(1)
                    .setName("app.task.id")
                    .setConfirmationStatus(ConfirmationStatus.NONE)
                    .build();

            setSlot(slot);
            setConfirmIntent();
        } else {
            outputSpeechStr = "你当前有" + tasks.size() + "个任务, 你要打卡第几个呢？";
            addDirective(selectSlot);
        }
        RenderTemplate renderTemplate = new RenderTemplate(listTemplate2);

        this.addDirective(renderTemplate);
        OutputSpeech outputSpeech = new OutputSpeech(OutputSpeech.SpeechType.PlainText, outputSpeechStr);
        return new Response(outputSpeech, null, new Reprompt(outputSpeech));

    }

    @Override
    protected Response onElementSelectedEvent(ElementSelectedEvent elementSelectedEvent) {
        log.info("Handle onElementSelectedEvent");

        String action = getSessionAttribute(Cons.ATTRI_KEY_ACTION);
        if (action == null) {
            return response;
        }
        if (action.equals(Cons.ATTRI_CLOCK_IN)) {
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
                    if (bindTaskId <= 0) {
                        outputSpeechStr = "打卡成功";

                    }
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

        } else if (action.equals(Cons.ATTRI_CRE_ONCE_TASK)) {
            System.out.println("time start time start time start time start");
            String timeInDay = getSessionAttribute(Cons.ATTRI_CRE_ONCE_TASK);
            log.info("Time in day:{}", timeInDay);
            if (timeInDay == null) {
                return response;
            }
            if (timeInDay.equals(Cons.ATTRI_SELECT_TIME_START)) {
                Slot slot = Slot.newBuilder()
                        .setName("sys.time1")
                        .setValue(elementSelectedEvent.getToken())
                        .setConfirmationStatus(ConfirmationStatus.CONFIRMED)
                        .setScore(1)
                        .setValues(new ArrayList<>())
                        .build();
                System.out.println("000000000000000000");
                Intent intent = getIntent();
                System.out.println("intent == null ?" + (intent == null ? "true" : "false"));
//                setSlot(slot);

                setSessionAttribute(Cons.ATTRI_SELECT_TIME_START, elementSelectedEvent.getToken());


                System.out.println("1111111111111111");

                setSessionAttribute(Cons.ATTRI_CRE_ONCE_TASK, Cons.ATTRI_SELECT_TIME_END);

                ListTemplate4 timeListTemplate = getTimeListTemplate();
                RenderTemplate renderTemplate = new RenderTemplate(timeListTemplate);
                addDirective(renderTemplate);
                System.out.println("2222222222");

                return new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText, "下面选择任务的结束时间吧"));

            } else if (timeInDay.equals(Cons.ATTRI_SELECT_TIME_END)) {
                System.out.println("time end time end time end time end time end");
                Slot slot = Slot.newBuilder()
                        .setName("sys.time2")
                        .setValue(elementSelectedEvent.getToken())
                        .setConfirmationStatus(ConfirmationStatus.CONFIRMED)
                        .setScore(1)
                        .setValues(new ArrayList<>())
                        .build();
//                setSlot(slot);
                setSessionAttribute(Cons.ATTRI_SELECT_TIME_END, elementSelectedEvent.getToken());
                setSessionAttribute(Cons.ATTRI_CRE_ONCE_TASK, "");

                String taskContent = getSessionAttribute(Cons.ATTRI_TASK_CONTENT);
                String taskDate = getSessionAttribute(Cons.ATTRI_ONCE_TASK_DATE);
                String timeInDayStart = getSessionAttribute(Cons.ATTRI_SELECT_TIME_START);
                String timeInDayEnd = elementSelectedEvent.getToken();

                log.info("Task content: {}", taskContent);
                log.info("Task date: {}", taskDate);
                log.info("Task time in day start: {}", timeInDayStart);
                log.info("Task time in day end: {}", timeInDayEnd);

                Task task = taskService.insertOnceTask(getUserId(), Cons.BABY, dateUtil.convertDate(taskDate), dateUtil.convertTime(timeInDayStart),
                        dateUtil.convertTime(timeInDayEnd), taskContent, -1);

                if (task != null) {
                    setSessionAttribute(Cons.ATTRI_KEY_LAST_TASK_ID, task.getId() + "");
                    return new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText, "好的，任务已经创建成功" +
                            "如果你想要为这个任务绑定其它任务可以对我说绑定任务"));
                } else {
                    return new Response(new OutputSpeech(OutputSpeech.SpeechType.PlainText,
                            "哎呀，我遇到点问题"));
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
        } else {
            if (taskType.equals(Cons.TASK_ONCE_STR)) {
                if (Strings.isEmpty(taskContent)) {
                    ask(config.getWildCardSlot());
                    outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                    outputSpeech.setText("告诉我你的任务内容吧，例如：背单词、练字等");
                    response = new Response(outputSpeech);
                    return response;
                }

                if (Strings.isEmpty(onceTaskDateSlot)) {
                    ask(config.getOnceTaskDateSlot());
                    outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                    outputSpeech.setText("哪一天执行任务呢，你可以这样说，今天、明天、后天");
                    response = new Response(outputSpeech);
                    return response;
                }

                setSessionAttribute(Cons.ATTRI_KEY_ACTION, Cons.ATTRI_CRE_ONCE_TASK);
                setSessionAttribute(Cons.ATTRI_CRE_ONCE_TASK, Cons.ATTRI_SELECT_TIME_START);
                setSessionAttribute(Cons.ATTRI_ONCE_TASK_DATE, onceTaskDateSlot);
                setSessionAttribute(Cons.ATTRI_TASK_CONTENT, taskContent);

                ListTemplate4 listTemplate4 = getTimeListTemplate();
                RenderTemplate renderTemplate = new RenderTemplate(listTemplate4);
                addDirective(renderTemplate);

                outputSpeech.setType(OutputSpeech.SpeechType.PlainText);
                outputSpeech.setText("好的，点击屏幕选择任务的开始时间吧");
                setExpectSpeech(false);

                response = new Response(outputSpeech);
                return response;
            } else {
                setSessionAttribute(Cons.ATTRI_KEY_ACTION, Cons.ATTRI_CRE_LONG_TASK);
                return response;
            }

        }

        /**
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
         setSessionAttribute(Cons.ATTRI_KEY_LAST_TASK_ID, task.getId() + "");
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
         **/

    }

    private ListTemplate4 getTimeListTemplate() {
        ListTemplate4 listTemplate4 = new ListTemplate4();
        String timePatternSuffix = ":00:00";
        for (int i = 8; i <= 18; ++i) {
            ListItemWithListTemplate4 template4 = new ListItemWithListTemplate4();
            TextStructure textStructure = new TextStructure(i + "点");
            template4.setContent(textStructure);
            String time = i < 10 ? "0" + i : "" + i;
            template4.setToken(time + timePatternSuffix);
            listTemplate4.addListItem(template4);
        }
        return listTemplate4;
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

    protected void select(final String slot) {
        SelectSlot selectSlot = new SelectSlot();
        selectSlot.setSlotToSelect(slot);
        selectSlot.setUpdatedIntent(getIntent());
        addDirective(selectSlot);
    }

}
