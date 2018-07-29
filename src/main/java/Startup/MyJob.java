package Startup;

import IPCheck.HttpTests;
import Model.IPMessage;
import Redis.MyRedis;
import ParseUtils.IPPool;
import ParseUtils.IPThread;
import ParseUtils.URLParse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
public class MyJob {
    MyRedis redis = new MyRedis();
    //想要爬去的代理页数
    int pagenum = 3000;

    public void Start() {
        //首先清空redis数据库中的key
        redis.deleteKey("IPPool");

        //存放爬取下来的ip信息
        List<IPMessage> ipMessagesall = new ArrayList<IPMessage>();
        List<String> urls = new ArrayList<String>();

        //首先使用本机ip爬取xici代理网第一页
        ipMessagesall = URLParse.urlParse(ipMessagesall);

        //对ip进行过滤，将速度在2秒之内的保留
        List<IPMessage> ipMessages = ipMessagesall.stream().filter(w -> Double.parseDouble(w.getIPSpeed()) <= 2.0).collect(Collectors.<IPMessage>toList());

        //对拿到的ip进行质量检测，将质量不合格的ip在List里进行删除
        HttpTests.httpsRequest(ipMessages);

        for (int i = 1; i <= pagenum; i++) {
            urls.add("http://www.xicidaili.com/nn/" + i);
        }

        //解析所有的url，拿到所有代理ip
        IPPool ipPool = new IPPool(ipMessages);

        ThreadPoolExecutor ipthread = new ThreadPoolExecutor(5, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int i = 0; i < 20; i++) {
            //给每个线程进行任务的分配
            IPThread IPThread = new IPThread(urls.subList(i*150, i*150+150), ipPool);
            ipthread.submit(IPThread);
        }

        ipthread.shutdown();

        try {
            //等待线程池任务完成
            ipthread.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //将爬取下来的ip信息写进Redis数据库中(List集合)
        redis.setIPToList(ipMessages);

        redis.close();
    }
}
