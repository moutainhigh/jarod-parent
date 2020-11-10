package pri.jarod.java.core.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 前后端数据传输加密工具类
 *
 * @author monkey
 */
public class AesEncryptUtils {
    //可配置到Constant中，并读取配置文件注入,16位,自己定义
    private static final String KEY = "BLUEv80r0605MOON";
    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";
    private static final String IVPARAM = "BLUEv80r0605MOON";

    /**
     * 加密
     *
     * @param content    加密的字符串
     * @param encryptKey key值
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String encryptKey, String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        SecretKeySpec skeySpec = new SecretKeySpec(encryptKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        // 采用base64算法进行转码,避免出现中文乱码
        return Base64.encodeBase64String(b);

    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @param decryptKey 解密的key值
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String decryptKey, String ivParameter) throws Exception {

        SecretKeySpec skeySpec = new SecretKeySpec(decryptKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        // 采用base64算法进行转码,避免出现中文乱码
        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        String originalString = new String(decryptBytes);
        //String originalString = new String(original,"utf-8");
        return originalString;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static String encrypt(String content) throws Exception {
        return encrypt(content, KEY, IVPARAM);
    }

    public static String decrypt(String encryptStr) throws Exception {
        return decrypt(encryptStr, KEY, IVPARAM);
    }


//    public static void main(String[] args) throws Exception {
//
//        String content = "qq123123";
//        System.out.println("加密前：" + content);
//
//        String encrypt = encrypt(content);
//        System.out.println("加密后：" + encrypt);
//
//        String decrypt = decrypt(encrypt);
//        System.out.println("解密后：" + decrypt);
//
//        String i_password = DigestUtils.md5Hex("qq123123");
//
//        String aaa = DigestUtils.md5Hex(i_password);
//        System.out.println(i_password);
//        System.out.println(aaa);
//    }
}

