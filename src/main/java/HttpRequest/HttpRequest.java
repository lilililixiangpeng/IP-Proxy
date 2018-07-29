package HttpRequest;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
public class HttpRequest {

    //使用代理进行爬取
    public static String getHtml( String url, String ip, String port) {
        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置代理访问和超时处理
        out.println("此时线程: " + Thread.currentThread().getName() + " 爬取所使用的代理为: "
                + ip + ":" + port);
        HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(3000).
                setSocketTimeout(3000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        setConfig(httpGet);

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }

            httpResponse.close();
            httpClient.close();
        } catch (ClientProtocolException e) {
            entity = null;
        } catch (IOException e) {
            entity = null;
        }

        return entity;
    }

    //对上一个方法的重载，使用本机ip进行网站爬取
    public static String getHtml(String url) {
        String entity = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置超时处理
        RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).
                setSocketTimeout(3000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        setConfig(httpGet);

        try {
            //客户端执行httpGet方法，返回响应
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

            //得到服务响应状态码
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                entity = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            }

            httpResponse.close();
            httpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entity;
    }

    public static void setConfig(HttpGet httpGet){

        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                "q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "no-cache");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "www.xicidaili.com");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
    }


}
