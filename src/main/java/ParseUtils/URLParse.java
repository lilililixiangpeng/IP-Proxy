package ParseUtils;

import Model.IPMessage;
import HttpRequest.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import static java.lang.System.out;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
public class URLParse {
    //使用代理进行爬取
    public static boolean urlParse(String url, String ip, String port,
                                           List<IPMessage> ipMessages1) {
        //调用一个类使其返回html源码
        String html = HttpRequest.getHtml(url, ip, port);

        if(html != null) {
            //将html解析成DOM结构
            Document document = Jsoup.parse(html);

            //提取所需要的数据
            Elements trs = document.select("table[id=ip_list]").select("tbody").select("tr");
            addmessage(trs,ipMessages1);
            return true;
        } else {
            out.println(ip+ ": " + port + " 代理不可用");

            return false;
        }
    }

    //使用本机IP爬取xici代理网站的第一页
    public static List<IPMessage> urlParse(List<IPMessage> ipMessages) {
        String url = "http://www.xicidaili.com/nn/1";
        String html = HttpRequest.getHtml(url);

        //将html解析成DOM结构
        Document document = Jsoup.parse(html);

        //提取所需要的数据
        Elements trs = document.select("table[id=ip_list]").select("tbody").select("tr");
        addmessage(trs,ipMessages);
        return ipMessages;
    }

    public static void addmessage(Elements trs,List<IPMessage> ipMessages){
        for (int i = 1; i < trs.size(); i++) {
            IPMessage ipMessage = new IPMessage();
            String ipAddress = trs.get(i).select("td").get(1).text();
            String ipPort = trs.get(i).select("td").get(2).text();
            String ipType = trs.get(i).select("td").get(5).text();
            String ipSpeed = trs.get(i).select("td").get(6).select("div[class=bar]").
                    attr("title");

            ipMessage.setIPAddress(ipAddress);
            ipMessage.setIPPort(ipPort);
            ipMessage.setIPType(ipType);
            ipMessage.setIPSpeed(ipSpeed);

            ipMessages.add(ipMessage);
        }
    }
}
