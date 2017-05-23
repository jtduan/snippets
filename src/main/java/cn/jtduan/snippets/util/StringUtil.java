package cn.jtduan.snippets.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by summer on 2017/4/15.
 */
public class StringUtil {
    public static String getMatch(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        String result = content;
        try {
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                result = matcher.group(1);
            } else {
                System.out.println(content + "===" + regex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //Unicode转中文
    public static String decodeUnicode(final String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int pos = 0;

        while (utfString.indexOf("\\u", pos) != -1) {
            i = utfString.indexOf("\\u", pos);
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
                i = pos;
            }
        }
        sb.append(utfString.substring(i));
        return sb.toString();
    }
}
