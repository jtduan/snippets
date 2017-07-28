package cn.jtduan.snippets;

import cn.jtduan.snippets.util.HttpsUtil;
import cn.jtduan.snippets.util.OKHttpUtil;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableScheduling
public class SnippetsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SnippetsApplication.class, args);
    }
}

@Controller
class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaderNames();
        String str = null;
        while ((str = headers.nextElement()) != null) {
            System.out.println(str + ":" + request.getHeader(str));
        }
        return "hello!";
    }

}
