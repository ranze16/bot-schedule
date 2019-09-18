package com.ranze.schedule;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@Data
public class DynamicData {
    private static final String URL_BACKGROUND_DAY = "http://dbp-resource.gz.bcebos.com/e07bac25-a0b9-6eef-8fd2-b32387308650/day.png?authorization=bce-auth-v1%2Fa4d81bbd930c41e6857b989362415714%2F2019-09-15T13%3A44%3A01Z%2F-1%2F%2F4a26eb6bccb6d18de2c60e7372e8e783cc3799fa85e78761f571c4a4072951f7";
    private static final String URL_BACKGROUND_NIGHT = "http://dbp-resource.gz.bcebos.com/e07bac25-a0b9-6eef-8fd2-b32387308650/night.png?authorization=bce-auth-v1%2Fa4d81bbd930c41e6857b989362415714%2F2019-09-15T13%3A44%3A01Z%2F-1%2F%2Ff2ce292351b1bc0633e7070c52e36f5392e9c4a726483fd02e6cb724bdb1f1da";

    public static final String URL_TASK_ALREADY_CLOCK_IN = "http://dbp-resource.gz.bcebos.com/e07bac25-a0b9-6eef-8fd2-b32387308650/already_clock_in.png?authorization=bce-auth-v1%2Fa4d81bbd930c41e6857b989362415714%2F2019-09-18T11%3A01%3A03Z%2F-1%2F%2F47895cd650ec8098179fd195b05d40a153e94580f2ecc91e05a086c40d04278b";
    public static final String URL_TASK_NEED_CLOCK_IN = "http://dbp-resource.gz.bcebos.com/e07bac25-a0b9-6eef-8fd2-b32387308650/wait_clock.png?authorization=bce-auth-v1%2Fa4d81bbd930c41e6857b989362415714%2F2019-09-18T10%3A58%3A53Z%2F-1%2F%2Fa16b1ff8fb199a2d9a0843f17e62adff775e3cc60829d97c177afe98d4e5d894";


    public String getHomeBackgroundUrl() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour <= 6 || hour >= 18) {
            return URL_BACKGROUND_NIGHT;
        } else {
            return URL_BACKGROUND_DAY;
        }
    }

    public String getTaskItem() {
        return URL_TASK_ALREADY_CLOCK_IN;
    }

//    public String getTaskListItemLogo(String state) {
//        if (state.equals(Cons.TASK_STATE_WAIT_CLOCKED_IN)) {
//
//
//        }
//
//
//    }
//
}
