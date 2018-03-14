package net.lemonsoft.AdministratorTerminal.tool;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 工具类 - 日期相关
 * Created by lemonsoft on 2016/8/23.
 */
public class DateTool {

    public static String timeStamp2Date(Long timestamp, String format) {
        return new java.text.SimpleDateFormat(format).format(new java.util.Date(timestamp));
    }

    public static String timeStamp2Date(String timestampString, String format) {
        return timeStamp2Date(Long.parseLong(timestampString) * 1000, format);
    }

    public static Date localDateToUtilDate(LocalDate date) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }
}
