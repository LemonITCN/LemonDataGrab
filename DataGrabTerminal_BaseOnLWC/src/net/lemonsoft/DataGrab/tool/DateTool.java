package net.lemonsoft.DataGrab.tool;

/**
 * 工具类 - 日期相关
 * Created by lemonsoft on 2016/8/23.
 */
public class DateTool {
    public static String timeStamp2Date(String timestampString, String format){
        Long timestamp = Long.parseLong(timestampString)*1000;
        String date = new java.text.SimpleDateFormat(format).format(new java.util.Date(timestamp));
        return date;
    }
}
