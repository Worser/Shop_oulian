/**
 * 
 */
package tst.project.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import tst.project.bean.others.CodeBean;
import tst.project.bean.others.VerificationBean;

/**
 * @author sjb
 * 
 */
public class VerificationCodeUtils {

	public static boolean sendContent(VerificationBean verificationBean, String mobile, String desc) {
		try {
			String format = "yyyy-MM-dd HH:mm:ss";
			String time = TimeUtils.getCurrentTime(format);
			Date date = TimeUtils.getDateFromTime(format, time);

			String url = verificationBean.getVerification_url();
			String username = verificationBean.getVerification_account(); // 账号
			String password = verificationBean.getVerification_password(); // 密码
			String tkey = TimeUtils.getCurrentTime("yyyyMMddHHmmss");
			String productid = "676767"; // 产品id
			String content = desc; // 内容

			// String time="2016-09-06 17:48:22";//定时信息所需参数时间格式为yyyy-MM-dd
			// HH:mm:ss
			String xh = "";
			content = URLEncoder.encode(content, "utf-8");

			String param = "url=" + url + "&username=" + username + "&password="
					+ MD5Util.getMD5(MD5Util.getMD5(password) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile
					+ "&content=" + content + "&productid=" + productid + "&xh=" + xh;
			// String
			// param="url="+url+"&username="+username+"&password="+MD5Gen.getMD5(MD5Gen.getMD5(password)+tkey)+"&tkey="+tkey+"&time="+time+"&mobile="+mobile+"&content="+content+"&xh="+xh;//定时信息url输出
			String ret = HttpRequest.sendPost(url, param);// 定时信息只可为post方式提交
			System.out.println("ret:" + ret);
			System.out.println(param);
			System.out.println("________________________________");
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static CodeBean sendCode(VerificationBean verificationBean, CodeBean codeBean) throws Exception {
		try {
			String format = "yyyy-MM-dd HH:mm:ss";
			String time = TimeUtils.getCurrentTime(format);
			Date date = TimeUtils.getDateFromTime(format, time);
			codeBean.setCreate_time(time);
			codeBean.setEffective_time(
					TimeUtils.getTimeMinuteAfter(format, date, verificationBean.getEffective_time()));

			String url = verificationBean.getVerification_url();
			String username = verificationBean.getVerification_account(); // 账号
			String password = verificationBean.getVerification_password(); // 密码
			String tkey = TimeUtils.getCurrentTime("yyyyMMddHHmmss");
			String mobile = codeBean.getMobile(); // 发送的手机号
			String productid = "676767"; // 产品id
			String content = codeBean.getCode_desc(); // 内容

			// String time="2016-09-06 17:48:22";//定时信息所需参数时间格式为yyyy-MM-dd
			// HH:mm:ss
			String xh = "";
			content = URLEncoder.encode(content, "utf-8");

			String param = "url=" + url + "&username=" + username + "&password="
					+ MD5Util.getMD5(MD5Util.getMD5(password) + tkey) + "&tkey=" + tkey + "&mobile=" + mobile
					+ "&content=" + content + "&productid=" + productid + "&xh=" + xh;
			// String
			// param="url="+url+"&username="+username+"&password="+MD5Gen.getMD5(MD5Gen.getMD5(password)+tkey)+"&tkey="+tkey+"&time="+time+"&mobile="+mobile+"&content="+content+"&xh="+xh;//定时信息url输出
			String ret = HttpRequest.sendPost(url, param);// 定时信息只可为post方式提交
			System.out.println("ret:" + ret);
			System.out.println(param);
			System.out.println("________________________________");
			return codeBean;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
