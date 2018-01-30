package tst.project.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import tst.project.bean.KuaidiBean;
import tst.project.bean.order.LogisticsDetailBean;

public class Kuaidi100Utils {
	public static List<LogisticsDetailBean> getLogistics(String logistics_no,String logistics_pinying){
		try{
			List<LogisticsDetailBean> logisticsDetailBeans=new ArrayList<LogisticsDetailBean>();
			
			String customer="8B34E1B96DEFBC381C6AB9D7BFFD7B54";
			String key="MfyCPLHq7040";
			String param="{\"com\":\""+logistics_pinying+"\",\"num\":\""+logistics_no+"\"}";
			
			String sign=MD5Util.encode(param+key+customer);
			
			//String a=HttpUtils.getDataByPost(Url, nameValuePairs);
			String paramUrl = "&customer="+customer+"&sign="+sign+"&param="+param+"";
			String ret = HttpRequest.sendPost("http://poll.kuaidi100.com/poll/query.do", paramUrl);// 定时信息只可为post方式提交
			System.out.println("ret:" + ret);
			
			String result=ret.replace("ftime", "logistics_time").replace("context", "logistics_context").replace("data", "logisticsDetailBeans");
			KuaidiBean kuaidiBean=new Gson().fromJson(result, KuaidiBean.class);
			if(kuaidiBean!=null&&kuaidiBean.getMessage()!=null&&kuaidiBean.getMessage().equals("ok")){
				if(kuaidiBean.getLogisticsDetailBeans()!=null){
					logisticsDetailBeans=kuaidiBean.getLogisticsDetailBeans();
				}
			}
			return logisticsDetailBeans;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.print("----------------"+e.getMessage());
			return new ArrayList<LogisticsDetailBean>();
		}
	}
}
