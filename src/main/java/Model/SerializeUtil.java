package Model;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by lixiangpeng on 2018/7/21.
 */
public class SerializeUtil {
    private final static Logger logger = Logger.getLogger(SerializeUtil.class.getName());

    public static String serialize(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            return new BASE64Encoder().encode(bos.toByteArray());
        }catch (IOException e) {
            logger.warning("序列化错误！");
        }finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object unserialize( String object) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(object));
            ois = new ObjectInputStream(bis);
            Object o = ois.readObject();
            return o;
        } catch (IOException e) {
            logger.warning("反序列化错误:IO异常");
        } catch (ClassNotFoundException e) {
            logger.warning("反序列化错误:类找不到");
        }finally {
            try {
                if(bis!=null) {
                    bis.close();
                }
                if(ois!=null){
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
