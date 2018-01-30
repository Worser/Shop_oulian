package tst.project.bean;

import java.util.List;

import tst.project.bean.order.LogisticsDetailBean;

public class KuaidiBean {
	private String message;
	private List<LogisticsDetailBean> logisticsDetailBeans;
	public String getMessage() {
		return message;
	}
	public KuaidiBean setMessage(String message) {
		this.message = message;
		return this;
	}
	public List<LogisticsDetailBean> getLogisticsDetailBeans() {
		return logisticsDetailBeans;
	}
	public KuaidiBean setLogisticsDetailBeans(List<LogisticsDetailBean> logisticsDetailBeans) {
		this.logisticsDetailBeans = logisticsDetailBeans;
		return this;
	} 
	
	
}
