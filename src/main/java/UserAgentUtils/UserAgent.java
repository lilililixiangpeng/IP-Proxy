package UserAgentUtils;

import java.util.Random;

/**
 * Created by lixiangpeng on 2018/8/5.
 */
public class UserAgent {
    private static String USER_AGENT_1 = "Mozilla/5.0 (Windows NT 6.2; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0";
    private static String USER_AGENT_2 = "Mozilla/5.0 (Android; Mobile; rv:14.0) Gecko/14.0 Firefox/14.0";
    private static String USER_AGENT_3 = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.94 Safari/537.36";
    private static String USER_AGENT_4 = "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19";
    private static String USER_AGENT_5 = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";

    public static String GetUserAgent(){
        String useragent = null;
        int randow = (int)(1+Math.random()*(5-1+1));
        switch (randow){
            case 1: useragent = USER_AGENT_1; break;
            case 2: useragent = USER_AGENT_2; break;
            case 3: useragent = USER_AGENT_3; break;
            case 4: useragent = USER_AGENT_4; break;
            case 5: useragent = USER_AGENT_5; break;
            default: useragent = USER_AGENT_1;
        }
        return useragent;
    }
}
