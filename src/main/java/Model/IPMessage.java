package Model;

import java.io.Serializable;


/**
 * Created by lixiangpeng on 2018/7/21.
 */
//想要将该对象存储倒Redis List中，必须对其实现序列化于反序列化，操作Serializable接口
public class IPMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String IPAddress;
    private String IPPort;
    private String IPType;
    private String IPSpeed;

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getIPPort() {
        return IPPort;
    }

    public void setIPPort(String IPPort) {
        this.IPPort = IPPort;
    }

    public String getIPType() {
        return IPType;
    }

    public void setIPType(String IPType) {
        this.IPType = IPType;
    }

    public String getIPSpeed() {
        return IPSpeed;
    }

    public void setIPSpeed(String IPSpeed) {
        this.IPSpeed = IPSpeed;
    }

    @Override
    public String toString() {
        return IPAddress + ":" + IPPort;
    }

}
