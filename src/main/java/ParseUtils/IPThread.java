package ParseUtils;

import java.util.List;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
//继承Runnable接口是为了使用线程池
public class IPThread implements Runnable {
    private List<String> urls;
    private IPPool ipPool;

    public IPThread(List<String> urls, IPPool ipPool) {
        this.urls = urls;
        this.ipPool = ipPool;
    }

    public void run() {
        ipPool.getIP(urls);
    }
}
