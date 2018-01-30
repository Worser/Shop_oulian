package tst.project.bean.others;

import java.util.List;

public class AdviceBean {
	private int advice_id;
	private String advice_title;
	private String advice_desc;
	private String member_id;
	private String nick_name;
	private String company_name;
	private String create_time;
	private String advice_type;
	private String advice_type_show;
	private String is_delete;
	
	private List<AdviceImgBean> adviceImgBeans;
		
	public String getCompany_name() {
		return company_name;
	}
	public AdviceBean setCompany_name(String company_name) {
		this.company_name = company_name;
		return this;
	}
	public List<AdviceImgBean> getAdviceImgBeans() {
		return adviceImgBeans;
	}
	public AdviceBean setAdviceImgBeans(List<AdviceImgBean> adviceImgBeans) {
		this.adviceImgBeans = adviceImgBeans;
		return this;
	}
	public String getNick_name() {
		return nick_name;
	}
	public AdviceBean setNick_name(String nick_name) {
		this.nick_name = nick_name;
		return this;
	}
	public String getAdvice_type_show() {
		return advice_type_show;
	}
	public AdviceBean setAdvice_type_show(String advice_type_show) {
		this.advice_type_show = advice_type_show;
		return this;
	}
	public String getAdvice_title() {
		return advice_title;
	}
	public AdviceBean setAdvice_title(String advice_title) {
		this.advice_title = advice_title;
		return this;
	}
	public int getAdvice_id() {
		return advice_id;
	}
	public AdviceBean setAdvice_id(int advice_id) {
		this.advice_id = advice_id;
		return this;
	}
	public String getAdvice_desc() {
		return advice_desc;
	}
	public AdviceBean setAdvice_desc(String advice_desc) {
		this.advice_desc = advice_desc;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public AdviceBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public AdviceBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public String getAdvice_type() {
		return advice_type;
	}
	public AdviceBean setAdvice_type(String advice_type) {
		this.advice_type = advice_type;
		this.advice_type_show="technical_consulting".equals(advice_type)?"技术咨询":
			  ("technical_opinion".equals(advice_type)?"技术建议":
				  "feedback_opinion".equals(advice_type)?"反馈意见":"其他");
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public AdviceBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}

	
}
