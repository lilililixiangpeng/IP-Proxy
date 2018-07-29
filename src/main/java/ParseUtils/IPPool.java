package ParseUtils;

import IPCheck.HttpTests;
import Model.IPMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
public class IPPool {
    //成员变量（非线程安全）
    private List<IPMessage> ipMessages;

    public IPPool(List<IPMessage> ipMessages) {
        this.ipMessages = ipMessages;
    }

    public void getIP(List<String> urls) {
        String ipAddress;
        String ipPort;

        for (int i = 0; i < urls.size(); i++) {
            //每个线程先将自己抓取下来的ip保存下来并进行过滤与检测
            List<IPMessage> ipMessages1 = new ArrayList<IPMessage>();
            String url = urls.get(i);

            synchronized (ipMessages) {
                int rand = (int) (Math.random()*ipMessages.size());
                out.println("当前线程 " + Thread.currentThread().getName() + " rand值: " + rand +
                        " ipMessages 大小: " + ipMessages.size());

                ipAddress = ipMessages.get(rand).getIPAddress();
                ipPort = ipMessages.get(rand).getIPPort();
            }

            //这里要注意Java中非基本类型的参数传递方式，实际上都是同一个对象
            boolean status = URLParse.urlParse(url, ipAddress, ipPort, ipMessages1);
            //如果ip代理池里面的ip不能用，则切换下一个IP对本页进行重新抓取
            if (status == false) {
                i--;
                continue;
            } else {
                out.println("线程：" + Thread.currentThread().getName() + "已成功抓取 " +
                        url + " ipMessage1：" + ipMessages1.size());
            }

            //对ip进行过滤，将速度在2秒之内的保留
            List<IPMessage> ipMessages2 = ipMessages1.stream().filter(w -> Double.parseDouble(w.getIPSpeed()) <= 2.0).collect(Collectors.<IPMessage>toList());

            //对ip进行质量检测，将质量不合格的ip在List里进行删除
            HttpTests.httpsRequest(ipMessages2);

            //将质量合格的ip合并到共享变量ipMessages中，进行合并的时候保证原子性
            synchronized (ipMessages) {
                out.println("线程" + Thread.currentThread().getName() + "已进入合并区 " +
                        "待合并大小 ipMessages1：" + ipMessages2.size());
                ipMessages.addAll(ipMessages2);
            }
        }
    }
}

