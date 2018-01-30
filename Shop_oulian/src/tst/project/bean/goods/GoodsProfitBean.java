package tst.project.bean.goods;

import tst.project.bean.member.MemberBean;

public class GoodsProfitBean {
	private int goods_profit_id;
	private String goods_id;
	private String member_id;
	private String order_goods_id;
	private String order_id;
	private String profit_time;
	private String is_delete;
	private String create_time;
	private String profit_money;
	private String profit_type;
	private String profit_count;
	private String merchants_id;
	private String parent_id;
	private String is_authorization;
	private String goods_name;
	private String sales_no;
	private String progress_no;
	private String count_no;
	
	private String start_time;
	private String end_time;
	
	private MemberBean memberBean;
	private GoodsBean goodsBean;
	
	public String getStart_time() {
		return start_time;
	}
	public GoodsProfitBean setStart_time(String start_time) {
		this.start_time = start_time;
		return this;
	}
	public String getEnd_time() {
		return end_time;
	}
	public GoodsProfitBean setEnd_time(String end_time) {
		this.end_time = end_time;
		return this;
	}
	public String getSales_no() {
		return sales_no;
	}
	public GoodsProfitBean setSales_no(String sales_no) {
		this.sales_no = sales_no;
		return this;
	}
	public String getProgress_no() {
		return progress_no;
	}
	public GoodsProfitBean setProgress_no(String progress_no) {
		this.progress_no = progress_no;
		return this;
	}
	public String getCount_no() {
		return count_no;
	}
	public GoodsProfitBean setCount_no(String count_no) {
		this.count_no = count_no;
		return this;
	}
	public GoodsBean getGoodsBean() {
		return goodsBean;
	}
	public GoodsProfitBean setGoodsBean(GoodsBean goodsBean) {
		this.goodsBean = goodsBean;
		return this;
	}
	public String getIs_authorization() {
		return is_authorization;
	}
	public GoodsProfitBean setIs_authorization(String is_authorization) {
		this.is_authorization = is_authorization;
		return this;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public GoodsProfitBean setGoods_name(String goods_name) {
		this.goods_name = goods_name;
		return this;
	}
	public MemberBean getMemberBean() {
		return memberBean;
	}
	public GoodsProfitBean setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
		return this;
	}
	public String getParent_id() {
		return parent_id;
	}
	public GoodsProfitBean setParent_id(String parent_id) {
		this.parent_id = parent_id;
		return this;
	}
	public String getMerchants_id() {
		return merchants_id;
	}
	public GoodsProfitBean setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
		return this;
	}
	public String getProfit_count() {
		return profit_count;
	}
	public GoodsProfitBean setProfit_count(String profit_count) {
		this.profit_count = profit_count;
		return this;
	}
	public String getProfit_type() {
		return profit_type;
	}
	public GoodsProfitBean setProfit_type(String profit_type) {
		this.profit_type = profit_type;
		return this;
	}
	public int getGoods_profit_id() {
		return goods_profit_id;
	}
	public GoodsProfitBean setGoods_profit_id(int goods_profit_id) {
		this.goods_profit_id = goods_profit_id;
		return this;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public GoodsProfitBean setGoods_id(String goods_id) {
		this.goods_id = goods_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public GoodsProfitBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getOrder_goods_id() {
		return order_goods_id;
	}
	public GoodsProfitBean setOrder_goods_id(String order_goods_id) {
		this.order_goods_id = order_goods_id;
		return this;
	}
	public String getOrder_id() {
		return order_id;
	}
	public GoodsProfitBean setOrder_id(String order_id) {
		this.order_id = order_id;
		return this;
	}
	public String getProfit_time() {
		return profit_time;
	}
	public GoodsProfitBean setProfit_time(String profit_time) {
		this.profit_time = profit_time;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public GoodsProfitBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public GoodsProfitBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public String getProfit_money() {
		return profit_money;
	}
	public GoodsProfitBean setProfit_money(String profit_money) {
		this.profit_money = profit_money;
		return this;
	}

}
