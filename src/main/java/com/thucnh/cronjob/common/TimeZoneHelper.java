package com.thucnh.cronjob.common;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :29/10/2021 - 11:00 AM
 */
public class TimeZoneHelper {

    private TimeZoneHelper(){ }


    public static LinkedHashMap<String,Object> getTimeZoneAvailables(){
       return  getTimeZoneMeta();
    }
    public static TimeZone fromTimeZone(String input){
        Objects.requireNonNull(input);
        TimeZone timeZone = TimeZone.getTimeZone(input);
        return  timeZone;
    }

    private static LinkedHashMap<String,Object> getTimeZoneMeta() {
        LinkedHashMap<String,Object> result = new LinkedHashMap<>();
        String[] ids = TimeZone.getAvailableIDs();
        for (String id : ids){
            TimeZone tz =   TimeZone.getTimeZone(id);
            long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
            long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
                    - TimeUnit.HOURS.toMinutes(hours);
            // avoid -4:-30 issue
            minutes = Math.abs(minutes);
            String idKey = "";
            String valueKey = "";
            if (hours > 0) {
                idKey = String.format("%s",tz.getID());
                valueKey = String.format("(GMT+%d:%02d)", hours, minutes);
            } else {
                idKey = String.format("%s",tz.getID());
                valueKey = String.format("(GMT%d:%02d)", hours, minutes);
            }
            result.put(idKey,valueKey);
        }
        return result;
    }
}
