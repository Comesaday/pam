package cn.comesaday.coe.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import java.text.ParseException;
import java.util.Date;

/**
 * <Describe>
 *
 * @author: ChenWei
 * @CreateAt: 2020-08-10 17:27
 */
public class DateUtil {

    private static final String L_DATE = "yyyy-MM-dd HH:mm:ss";

    private static final String M_DATE = "yyyy-MM-dd HH:mm";

    private static final String S_DATE = "yyyy-MM-dd";

    public static String formatLDate(Date date) {
        return DateFormatUtils.format(date, L_DATE);
    }

    public static String formatMDate(Date date) {
        return DateFormatUtils.format(date, M_DATE);
    }

    public static String formatSDate(Date date) {
        return DateFormatUtils.format(date, S_DATE);
    }

    public static String formatLDate(Long time) {
        return DateFormatUtils.format(time, L_DATE);
    }

    public static String formatMDate(Long time) {
        return DateFormatUtils.format(time, M_DATE);
    }

    public static String formatSDate(Long time) {
        return DateFormatUtils.format(time, S_DATE);
    }

    public static Date strToLDate(String dateStr)
            throws ParseException {
        return DateUtils.parseDate(dateStr, L_DATE);
    }

    public static Date strToMDate(String dateStr)
            throws ParseException {
        return DateUtils.parseDate(dateStr, M_DATE);
    }

    public static Date strToSDate(String dateStr)
            throws ParseException {
        return DateUtils.parseDate(dateStr, S_DATE);
    }
}
