package IPCheck;

import Model.IPMessage;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
public class HttpTests {

    public static void httpsRequest(List<IPMessage> ipMessages1){
        for (int i = 0;i < ipMessages1.size();i++){
            String ipaddress = ipMessages1.get(i).getIPAddress();
            int port = Integer.parseInt(ipMessages1.get(i).getIPPort());
            try{
                //创建SSLContext
                SSLContext sslContext=SSLContext.getInstance("SSL");
                TrustManager[] tm={new MyX509TrustManager()};
                //初始化
                sslContext.init(null, tm, new java.security.SecureRandom());
                //获取SSLSocketFactory对象
                SSLSocketFactory ssf=sslContext.getSocketFactory();
                URL url=new URL("https://www.baidu.com");
                InetSocketAddress address = new InetSocketAddress(ipaddress,port);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
                HttpsURLConnection conn=(HttpsURLConnection)url.openConnection(proxy);
                conn.setUseCaches(false);
                conn.setRequestMethod("GET");
                //设置当前实例使用的SSLSoctetFactory
                conn.setSSLSocketFactory(ssf);
                conn.setSSLSocketFactory(sslContext.getSocketFactory());
                conn.connect();

                //读取服务器端返回的内容
                InputStream is=conn.getInputStream();
                InputStreamReader isr=new InputStreamReader(is,"utf-8");
                BufferedReader br=new BufferedReader(isr);
            }catch(Exception e){
                e.printStackTrace();
                ipMessages1.remove(ipMessages1.get(i));
                i--;
            }
        }

    }


}
