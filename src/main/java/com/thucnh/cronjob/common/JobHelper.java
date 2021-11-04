package com.thucnh.cronjob.common;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 1:23 PM
 */
public class JobHelper {

    private JobHelper(){}

    private static  final   String prefixCron = "cron";

    public static Map<String, Object> getTextCron(String expression, CronType cronType, Locale... locales){
        Map<String, Object> result = new HashMap<>();
        ResourceBundle resourceBundle;
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(cronType);
        CronParser parser = new CronParser(cronDefinition);
        for (Locale locale : locales){
            if ( null == locale ){
                resourceBundle  =  ResourceBundle.getBundle("i18n/cronmessage", DEFAULT);
            }else {
                resourceBundle  =  ResourceBundle.getBundle("i18n/cronmessage", locale);
            }
            CronDescriptor descriptor = new CronDescriptor(resourceBundle);
            String  crontext = descriptor.describe(parser.parse(expression));
            result.put(prefixCron.concat(locale.getLanguage()),crontext);
        }
        return  result;
    }

    public static String getTextCron(String expression, CronType cronType, Locale locale){
        try {

        ResourceBundle resourceBundle;
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(cronType);
        CronParser parser = new CronParser(cronDefinition);
        if ( null == locale ){
            resourceBundle  =  ResourceBundle.getBundle("i18n/cronmessage", DEFAULT);
        }else {
            resourceBundle  =  ResourceBundle.getBundle("i18n/cronmessage", locale);
        }
        CronDescriptor descriptor = new CronDescriptor(resourceBundle);
        return  descriptor.describe(parser.parse(expression));

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static Set<String> getListClassCronFromSource(String packageName){
        try {
            //packageName = "com.thucnh.cronjob.job";
            InputStream stream = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(packageName.replaceAll("[.]", "/"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            Set<String> classes  = reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName).getName())
                    .collect(Collectors.toSet());
            return  classes;
        }catch (Exception e){
        }
        return new HashSet<>();
    }
    public static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
    public static Locale VIETNAM = new Locale("vi", "VN"), DEFAULT = Locale.getDefault();

}
