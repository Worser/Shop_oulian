package tst.project.bean.entrust;

public class MemberEntrustBean {

	private int member_entrust_id;
	private String member_id;
	private String entrust_number;
	private String entrust_state;
	private String entrust_type;
	private String accept_time;
	private String create_time;
	private String is_delete;
	
	private String entrust_state_show;
	private String entrust_type_show;
	
	//其他
	private String company_name;
	private String member_level;
	private String member_level_show;
	
	public String getCompany_name() {
		return company_name;
	}
	public MemberEntrustBean setCompany_name(String company_name) {
		this.company_name = company_name;
		return this;
	}
	public String getMember_level() {
		return member_level;
	}
	public MemberEntrustBean setMember_level(String member_level) {
		this.member_level = member_level;
		this.member_level_show = "0".equals(member_level)?"普通会员":
			("1".equals(member_level)?"一星会员":
					"2".equals(member_level)?"二星会员":
						"3".equals(member_level)?"三星会员":
							"4".equals(member_level)?"四星会员":"五星会员");
		return this;
	}
	public String getMember_level_show() {
		return member_level_show;
	}
	public MemberEntrustBean setMember_level_show(String member_level_show) {
		this.member_level_show = member_level_show;
		return this;
	}
	public String getEntrust_type() {
		return entrust_type;
	}
	public MemberEntrustBean setEntrust_type(String entrust_type) {
		this.entrust_type = entrust_type;
		this.entrust_type_show = "buy".equals(entrust_type)?"买":
			("sell".equals(entrust_type)?"卖":"其他");
		return this;
	}
	public String getEntrust_type_show() {
		return entrust_type_show;
	}
	public MemberEntrustBean setEntrust_type_show(String entrust_type_show) {
		this.entrust_type_show = entrust_type_show;
		return this;
	}
	public String getEntrust_state_show() {
		return entrust_state_show;
	}
	public MemberEntrustBean setEntrust_state_show(String entrust_state_show) {
		this.entrust_state_show = entrust_state_show;
		return this;
	}
	public int getMember_entrust_id() {
		return member_entrust_id;
	}
	public MemberEntrustBean setMember_entrust_id(int member_entrust_id) {
		this.member_entrust_id = member_entrust_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public MemberEntrustBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getEntrust_number() {
		return entrust_number;
	}
	public MemberEntrustBean setEntrust_number(String entrust_number) {
		this.entrust_number = entrust_number;
		return this;
	}
	public String getEntrust_state() {
		return entrust_state;
	}
	public MemberEntrustBean setEntrust_state(String entrust_state) {
		this.entrust_state = entrust_state;
		this.entrust_state_show = "wait_confirm".equals(entrust_state)?"待确认":
			("accept".equals(entrust_state)?"接受":
				("refused").equals(entrust_state)?"拒绝":"其他");
		return this;
	}
	public String getAccept_time() {
		return accept_time;
	}
	public MemberEntrustBean setAccept_time(String accept_time) {
		this.accept_time = accept_time;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public MemberEntrustBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public MemberEntrustBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	
}
