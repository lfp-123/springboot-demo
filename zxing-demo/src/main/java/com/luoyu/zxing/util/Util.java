package com.luoyu.zxing.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 非对称加密工具类
 */
@Slf4j
public class Util {
    /**
     * 字符集
     */
    public static String CHARSET = "utf-8";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_INSTANCE = "SHA1WithRSA";

    // 测试秘钥公钥
    static String privateKey =
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCiWxiQH/MNXqKtN9DC9o4RxImoBSAXl2oaC+EvXlpD70S5HTnHMYemOcc5fSym5/U59NwRuvZMeMVdmN3/Nz6vH0lCketk+Gdpgn89dS8lis5nKPeEyBfFx0uLyc4OldGJe/98dv7CyjP4L6H5aZfUSxOWEgVx7Yuy7o2z2BMhuygw+yso1o9OqoBVEiLiUmldWkp7WwADh2qfRlRde+TSxlNbn5Isb4xXeIuUrmeNsmXFmuNiWChcGMmIjpw96u8ZsWWSCGDWEZyYeNc7plI4Mx6DR4+YRUD90FndckxG+BxgoGwlqWgIAv53blQe6sk7HaDFnRsstr8yY/THku7TAgMBAAECggEARjJ8/FPUEwPH8n0flixkYOpJWoxcw9aD2Z8pTgHNpXHelOs+ABI6RHTW3tRwafoA3X4xaLkdpRLSwxVFZwPL5ovwcQgGbzG4BLaXrUrth53sgE4GNzMMlEcHMvmeTlAYuQkT/gy1OiOTNMvjjKNLMk8DuO8xy4CZ1lOukQPrwbklYxtsD0WYBfQV0wuuynha9e42wCoA4SphUsCJ17fSiuGsR1gNMr5AEEWc1llqtedPbjbqaDizTQUDr9lOq5vEoVVykvupZvc6bpwF9qr6ms8CvXZ2OBRRFIg/bfUwOFJ8dH+4vaQ1dbR2TyDzivrw6eOWSRNPiZ2Z9oHkYuSxAQKBgQD/pSFN1E3RetIUcQ6zIKn14POSF6SXUQ8mW5CWIHw8lj8m06kfW1TYeY+sfz26uoSC2/okJWVeITN5HayREW7qKMbfqezReja1TT2RpteNksBf2nh08IkLAz4zlzYa2Qk8UjRA1KHAA1I9ZGJToJrfgcL/4cWKxx5VSgdoOJOaawKBgQCilM5O2cuaTuS/FnK5bATqLYm7S5DQ9k6dC3Z141ynx0JNjUWfGLK+uLVv9d8aQZkYWLr6OQR3NL/ed6FxU7VCZsdcoikuMlz8ontpQta0+4OI6xMniI/AO/fgsHA/eS8DQ4Hgdn8HCxsJB0XMWOMaZOVAXCqaZg4LxIzgEN3nOQKBgQCxgDZfk8l0aZL0HovWVBeuYUK9bS0a421hdLIvit64Mpomm1ASNZfztnPLkY2K2XaoZZSJBG4drW/P6IqpLzGT5DwxkAqeZ/0UZU0alH2WDw8Dfu2kRF7jBp1ptNQcUBXZjDo7yWeFqsivV8PtzNW72p1KjbhbY2XsUjpSYvYZgQKBgDYikmeTAcaJyoUONnkjTKelLh1dVgUeXqLQNEugvzNml9820R3enXYADvoOxpD5kr6WnPvAjIoc2F59OxZ/JvXLP0rK/CdTwb6lY65zAQcWUIAAtkoNoMADt8/rMBUShuLxUYeqLTXtLr1c3JLkbgpV2jgJ+G2c2cR1V+sr8peRAoGBAKCYNUzALjpLaSkk4K4VAq2MXMPf1d5GsRbMl04Jldz1szPIlU5euWKNSOmVllzscsqscKux1WotB58Gd06LFllzsEBX/RG+D5t6jY1TmAaRoEN0y2lcmwSYGluxAPHyRQeJYTFMtoBeE1NP9TbjRKQHa4oa4RQzyde1hyJbH1fb";
    static String publicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAolsYkB/zDV6irTfQwvaOEcSJqAUgF5dqGgvhL15aQ+9EuR05xzGHpjnHOX0spuf1OfTcEbr2THjFXZjd/zc+rx9JQpHrZPhnaYJ/PXUvJYrOZyj3hMgXxcdLi8nODpXRiXv/fHb+wsoz+C+h+WmX1EsTlhIFce2Lsu6Ns9gTIbsoMPsrKNaPTqqAVRIi4lJpXVpKe1sAA4dqn0ZUXXvk0sZTW5+SLG+MV3iLlK5njbJlxZrjYlgoXBjJiI6cPervGbFlkghg1hGcmHjXO6ZSODMeg0ePmEVA/dBZ3XJMRvgcYKBsJaloCAL+d25UHurJOx2gxZ0bLLa/MmP0x5Lu0wIDAQAB";

    /**
     * RSA长度
     */
    private static final int KEY_LENGTH = 2048;

    /**
     * RSA最大加密明文大小
     * (KEY_LENGTH / 8) - 11
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /**
     * RSA最大解密密文大小
     * KEY_LENGTH / 8
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    private static final String APP_ID = "appId";
    private static final String SIGN = "sign";
    private static final String TIMESTAMP = "timestamp";
    private static final String DATA = "data";

    public static void main(String[] args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder("http://e.test.bank.ecitic.com/citiccard/aps-openapi-gateway/aps-openapi/aps-openapi/user/sync/info" +
                "?clientIp=127.0.0.1&userAgent=3&deviceType=3");
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String appId = "0103680";
        // &timestamp=202104131750&appId=0103680
        String signParam = new StringBuffer("&timestamp=").append(timestamp).append("&appId=").append(appId).toString();

        String postParam = "{\"users\":[{\"agentId\":\"680\",\"cid\":\"370883198512280000\",\"education\":\"4\",\"email\":\"\",\"gender\":\"1\"," +
                "\"officePhone\":\"\",\"officeTel\":\"55515536\",\"phone\":\"13173000000\",\"position\":\"1\",\"qq\":\"\",\"status\":\"0\"," +
                "\"type\":\"1\",\"userId\":\"test000\",\"userName\":\"test01\",\"weixin\":\"\",\"workArea\":\"济南\",\"workStartDate\":\"2020-08-19\"}]}";
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(postParam);
        // 重新赋值，本地生成签名时用到ResponseBody值要跟传过去的相同
        postParam = jsonObject.toJSONString();

        String sign = Util.getSign(signParam, "post", postParam);
        url.append(signParam).append("&sign=").append(sign);
        URI uri = new URI(url.toString());

        String result = restTemplate.postForObject(uri, jsonObject, String.class);

        String decryptRes = Util.decryptByPrivateKey(result, Util.privateKey);
        System.out.println(decryptRes);
    }

    /**
     * 生成密钥对
     *
     * @param keyLength
     * @return
     * @throws Exception
     */
    public static KeyPair getKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 公钥字符串转PublicKey实例
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 私钥字符串转PrivateKey实例
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 公钥加密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = content.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(content, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(content, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] encryptByPublicKey(byte[] content, String publicKey) throws Exception {
        return encryptByPublicKey(content, getPublicKey(publicKey));
    }

    public static String encryptByPublicKey(String content, String publicKey) throws Exception {
        return new String(Base64.getEncoder().encode(encryptByPublicKey(content.getBytes(CHARSET), getPublicKey(publicKey))));
    }

    /**
     * 私钥解密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = content.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(content, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static byte[] decryptByPrivateKey(byte[] content, String privateKey) throws Exception {
        return decryptByPrivateKey(content, getPrivateKey(privateKey));
    }

    public static String decryptByPrivateKey(String content, String privateKey) throws Exception {
        return new String(decryptByPrivateKey(Base64.getDecoder().decode(content), getPrivateKey(privateKey)), CHARSET);
    }

    /**
     * 私钥加密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    public static byte[] encryptByPrivateKey(byte[] content, String privateKey) throws Exception {
        return encryptByPrivateKey(content, getPrivateKey(privateKey));
    }

    public static String encryptByPrivateKey(String content, String privateKey) throws Exception {
        return new String(Base64.getEncoder().encode(encryptByPrivateKey(content.getBytes(CHARSET),
                getPrivateKey(privateKey))), CHARSET);
    }

    /**
     * 公钥解密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypByPublicKey(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    public static byte[] decrypByPublicKey(byte[] content, String publicKey) throws Exception {
        return decrypByPublicKey(content, getPublicKey(publicKey));
    }

    public static String decrypByPublicKey(String content, String publicKey) throws Exception {
        return new String(decrypByPublicKey(Base64.getDecoder().decode(content), getPublicKey(publicKey)), CHARSET);

    }

    /**
     * 签名
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] content, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
        signature.initSign(privateKey);
        signature.update(content);
        return signature.sign();
    }

    public static String sign(String content, String privateKey) throws Exception {
        byte[] sign = sign(content.getBytes(CHARSET), getPrivateKey(privateKey));
        //转换string长度
        return new String(Base64.getEncoder().encode(sign), CHARSET);
    }


    /**
     * rsa签名
     *
     * @param params
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String rsaSign(Map<String, String> params, String privateKey) throws Exception {
        String signContent = getSignContent(params);
        System.out.println("签名字符串:" + signContent);
        return sign(signContent, privateKey);
    }

    /**
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (String key : keys) {
            String value = sortedParams.get(key);
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }


    /**
     * 验签
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] content, byte[] sign, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_INSTANCE);
        signature.initVerify(publicKey);
        signature.update(content);
        return signature.verify(sign);
    }

    public static boolean verify(String content, String sign, String publicKey) throws Exception {
        return verify(content.getBytes(CHARSET), Base64.getDecoder().decode(sign), getPublicKey(publicKey));
    }

    /**
     * 如果是RSA签名，请调用此方法进行验签
     *
     * @param params    待验签的收到的参数Map
     * @param publicKey 公钥
     * @return true：验签通过；false：验签不通过
     * @throws Exception
     */
    public static boolean rsaVerify(Map<String, String> params, String publicKey) throws Exception {
        String sign = params.get(SIGN);
        String content = getSignCheckContentV1(params);

        System.out.println("验签字符串: {}"+ content);
        return verify(content, sign, publicKey);
    }

    public static String getSignCheckContentV1(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove(SIGN);
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    /**
     * 验证签名
     *
     * @param request
     * @param appId
     * @param requestJson
     * @param publicKey
     * @return
     * @throws Exception
     */
    public boolean verifySign(HttpServletRequest request, String appId, String requestJson, String publicKey) throws Exception {
        // 获取全部参数(包括URL和body上的)
        SortedMap<String, String> signParams = new TreeMap<>();
        signParams.put(APP_ID, appId);
        signParams.put(SIGN, request.getParameter(SIGN));
        signParams.put(TIMESTAMP, request.getParameter(TIMESTAMP));
        signParams.put(DATA, requestJson);
        // 对参数进行签名验证
        boolean isSigned = rsaVerify(signParams, publicKey);
        if (isSigned) {
            System.out.println("签名通过");
            return true;
        } else {
            System.out.println("参数校验出错");
            return false;
        }
    }

    /**
     * 生成公私钥
     */
    public static void testGetKeyPair() {
        try {
            KeyPair keyPair = Util.getKeyPair(2048);
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            System.out.println(new String(Base64.getEncoder().encode(publicKey.getEncoded())));
            System.out.println(new String(Base64.getEncoder().encode(privateKey.getEncoded())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取字符串签名
     *
     * @param method
     * @return
     */
    private static String getSign(String urlParam, String method, String jsonData) throws Exception {
        String result;
        Map<String, String> data = new HashMap<>();

        String[] split = urlParam.split("&");
        for (String s : split) {
            String[] split1 = s.split("=");
            if (split1.length > 1) {
                data.put(split1[0], split1[1]);
            }
        }
        Map<String, String> signMap = new HashMap<>();
        signMap.put(APP_ID, data.get(APP_ID));
        signMap.put(TIMESTAMP, data.get(TIMESTAMP));

        if ("post".equals(method)) {
            String s = jsonData.replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "");
            signMap.put(DATA, s);
        }

        result = rsaSign(signMap, privateKey);
        String encode = URLEncoder.encode(result);
        return encode;
    }
}