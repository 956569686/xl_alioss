package tech.jitao.aly_oss;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

    //获取编码后的String
    public static String getEncryptString(String str) {
        if (!TextUtils.isEmpty(str)) {
            //一次编码
            String newStr = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
            //一次反转
            newStr = new StringBuilder(newStr).reverse().toString();
            //二次编码
            newStr = Base64.encodeToString(newStr.getBytes(), Base64.DEFAULT);
            //二次反转
            newStr = new StringBuilder(newStr).reverse().toString();
            //三次编码
            return Base64.encodeToString(newStr.getBytes(), Base64.DEFAULT);
        }
        return "";
    }

    //获取解码后的String
    public static String getDecryptString(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                //一次解码
                byte bytes[] = Base64.decode(str, Base64.DEFAULT);
                //一次反转
                bytes = new StringBuilder(new String(bytes, "UTF-8")).reverse().toString().getBytes();
                //二次解码
                bytes = Base64.decode(bytes, Base64.DEFAULT);
                //二次反转
                bytes = new StringBuilder(new String(bytes, "UTF-8")).reverse().toString().getBytes();
                //三次解码
                return new String(Base64.decode(bytes, Base64.DEFAULT), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    public static byte[] calculateSignBytes(String uri, String secret, String algorithm) {

        try {
            Mac mac = Mac.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), algorithm);
            mac.init(secretKeySpec);
            return mac.doFinal(uri.getBytes());

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }


    public static String stringToMD5(String data) {
        byte[] tmp = toRawMD5(data);
        return tmp == null ? "" : byteToHex(tmp);
    }

    private static String byteToHex(byte[] data) {
        StringBuilder buffer = new StringBuilder();
        for (byte aData : data) {
            buffer.append(String.format("%02x", aData));
        }
        return buffer.toString();
    }

    private static byte[] toRawMD5(String data) {
        byte[] md5;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("utf-8"));
            md5 = md.digest();
        } catch (Exception e) {
            md5 = null;
        }
        return md5;
    }

}
