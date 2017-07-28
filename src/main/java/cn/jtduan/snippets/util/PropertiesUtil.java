package cn.jtduan.snippets.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jintaoduan on 17/6/16.
 * maven读取resources目录下文件的方式
 */
public class PropertiesUtil {
    public static Map<String, String> getProperty(String filePath) {
        Map<String, String> map = new HashMap<>();

        try (InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            Properties p = new Properties();
            p.load(in);
            for (Map.Entry entry : p.entrySet()) {
                String key = (String) entry.getKey();
                map.put(key, (String) entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Map<String, String> getProperty(File file) {
        Map<String, String> map = new HashMap<>();
        try (InputStream in = new FileInputStream(file)) {
            Properties p = new Properties();
            p.load(in);
            for (Map.Entry entry : p.entrySet()) {
                String key = (String) entry.getKey();
                map.put(key, (String) entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        Map map = PropertiesUtil.getProperty("application.properties");
        System.out.println(map.size());
    }
}
