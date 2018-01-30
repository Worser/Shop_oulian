package tst.project.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	// 获得MD5摘要算法的 MessageDigest 对象
		private static MessageDigest _mdInst = null;
		private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		private static MessageDigest getMdInst() {
			if (_mdInst == null) {
				try {
					_mdInst = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
			return _mdInst;
		}
	/**
	 * MD5加密
	 * 
	 * @param inStr
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public static String md5Encode(String inStr) throws Exception {
		inStr = "tst" + inStr + "tst";
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	
	/**
	 * MD5加密
	 * 
	 * @param inStr
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public static String md5EncodeOrigin(String inStr) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	public final static String encode(String s) {
		try {
			byte[] btInput = s.getBytes();
			// 使用指定的字节更新摘要
			getMdInst().update(btInput);
			// 获得密文
			byte[] md = getMdInst().digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * MD5加密
     * 
     * @param src
     * @return
     */
    public static String getMD5(String src) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(src.getBytes());
            byte[] s=m.digest();

            return bintoascii(s);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    public static String bintoascii(byte[] bySourceByte) {
        int len, i;
        byte tb;
        char high, tmp, low;
        String result = new String();
        len = bySourceByte.length;
        for (i = 0; i < len; i++) {
            tb = bySourceByte[i];

            tmp = (char) ((tb >>> 4) & 0x000f);
            if (tmp >= 10) {
                high = (char) ('a' + tmp - 10);
            } else {
                high = (char) ('0' + tmp);
            }
            result += high;
            tmp = (char) (tb & 0x000f);
            if (tmp >= 10) {
                low = (char) ('a' + tmp - 10);
            } else {
                low = (char) ('0' + tmp);
            }

            result += low;
        }
        return result;
    }
}
