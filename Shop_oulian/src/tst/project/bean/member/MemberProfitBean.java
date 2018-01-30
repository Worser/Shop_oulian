package tst.project.bean.member;

public class MemberProfitBean {
	private int member_profit_id;
	private String member_id;
	private String order_id;
	private String profit_time;
	private String is_delete;
	private String create_time;
	private String profit_money;
	private String merchants_id;
	private String profit_type;
	private String profit_count;
	
	public String getProfit_count() {
		return profit_count;
	}
	public MemberProfitBean setProfit_count(String profit_count) {
		this.profit_count = profit_count;
		return this;
	}
	public String getMerchants_id() {
		return merchants_id;
	}
	public MemberProfitBean setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
		return this;
	}
	public String getProfit_type() {
		return profit_type;
	}
	public MemberProfitBean setProfit_type(String profit_type) {
		this.profit_type = profit_type;
		return this;
	}
	public int getMember_profit_id() {
		return member_profit_id;
	}
	public MemberProfitBean setMember_profit_id(int member_profit_id) {
		this.member_profit_id = member_profit_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public MemberProfitBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getOrder_id() {
		return order_id;
	}
	public MemberProfitBean setOrder_id(String order_id) {
		this.order_id = order_id;
		return this;
	}
	public String getProfit_time() {
		return profit_time;
	}
	public MemberProfitBean setProfit_time(String profit_time) {
		this.profit_time = profit_time;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public MemberProfitBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public MemberProfitBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public String getProfit_money() {
		return profit_money;
	}
	public MemberProfitBean setProfit_money(String profit_money) {
		this.profit_money = profit_money;
		return this;
	}
}
