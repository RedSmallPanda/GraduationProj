package utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * 获取当前时间小时整点时间
     *
     * @param
     * @return
     */
    public static Long getCurrentHourTime() {
        return getHourTime(new Date(), 0, "=");
    }

    /**
     * 获取秒数对应当前小时数
     *
     * @param timestamp
     * @return
     */

    public static int getHour(Long timestamp) {
        Date date = new Date();
        date.setTime(timestamp * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定时间上n个小时整点时间
     *
     * @param timestamp
     * @return
     */
    public static Long getLastHourTime(Long timestamp, int n) {
        Date date = new Date();
        date.setTime(timestamp * 1000);
        return getHourTime(date, n, "-");
    }

    /**
     * 获取指定时间下n个小时整点时间
     *
     * @param timestamp
     * @return
     */
    public static Long getNextHourTime(Long timestamp, int n) {
        Date date = new Date();
        date.setTime(timestamp * 1000);
        return getHourTime(date, n, "+");
    }

    /**
     * 获取指定时间n个小时整点时间
     *
     * @param date
     * @return
     */
    public static Long getHourTime(Date date, int n, String direction) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        switch (direction) {
            case "+":
                ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY) + n);
                break;
            case "-":
                ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY) - n);
                break;
            case "=":
                ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY));
                break;
            default:
                ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY));
        }

        date = ca.getTime();
        Long second = date.getTime()/1000;

        return second;
    }
}
