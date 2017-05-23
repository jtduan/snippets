package cn.jtduan.snippets.util;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 模拟请求的封装类
 */
public class OKHttpUtil {
    private static Logger logger = LoggerFactory.getLogger(OKHttpUtil.class);

    public static OkHttpClient client;
    static {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client = HttpsUtil.getUnsafeOkHttpClient().newBuilder().cookieJar(new JavaNetCookieJar(cookieManager))
                .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                        .addHeader("Accept-Encoding", "gzip, deflate")
//                        .addHeader("Connection", "keep-alive")
                        .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                        .addHeader("Accept", "*/*")
                        .build();
                return chain.proceed(request);
            }

        }).build();
    }

    public static String sendPost(String url, String params) {
        logger.debug(url + ":" + params);
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), params);
        Request request = new Request.Builder()
                .url(url).post(body)
                .build();

        return send(request);
    }

    public static String sendGet(String url, String params) {
        if (!params.isEmpty()) {
            url = url + "?" + params;
        }
        Request request = new Request.Builder()
                .url(url).get()
                .build();
        return send(request);
    }

    public static String send(Request request) {
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
//            return CharsetDetector.decode(response.body().bytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    public static byte[] getAndResponse(String url) {
        Request request = new Request.Builder()
                .url(url).get()
                .build();
        try {
            return client.newCall(request).execute().body().bytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
