package tst.project.bean.order;

import java.util.List;

public class OrderMerchantsBean {
	private int order_id;
	private String shopping_car_ids;
	private String member_id;
	private String address_id;
	private String order_type;
	private String logistics_pinyin;
	private String logistics_type;
	private List<OrderBean> orderBeans;
	private String member_group_buy_id;
	private String is_deduct_integral;
	private String member_coupon_id;

//	private String invoice_type;//发票类型 no:不开票   paper:纸质发票（个人，单位），electron:电子发票  increment:增值税发票
//	private String invoice_rise_type;//发票抬头类型    personal:个人   company:公司  
//	private String invoice_company_name;//发票抬头是 单位时， 所需要的单位名称   个人时 个人姓名
//	private String invoice_content;//发票内容
//	private String invise_ticket_phone;//收票人手机号
//	private String invise_ticket_email;//接受发票邮箱
//	private String invise_taxpayer_code;//纳税人识别码
//	private String invise_register_time;//注册时间
//	private String invise_register_phone;//注册电话
//	private String invise_bank_name;//开户银行
//	private String invise_bank_code;//银行账号
	
	public String getIs_deduct_integral() {
		return is_deduct_integral;
	}
	public String getLogistics_type() {
		return logistics_type;
	}
	public OrderMerchantsBean setLogistics_type(String logistics_type) {
		this.logistics_type = logistics_type;
		return this;
	}
	public String getLogistics_pinyin() {
		return logistics_pinyin;
	}
	public OrderMerchantsBean setLogistics_pinyin(String logistics_pinyin) {
		this.logistics_pinyin = logistics_pinyin;
		return this;
	}
	public OrderMerchantsBean setIs_deduct_integral(String is_deduct_integral) {
		this.is_deduct_integral = is_deduct_integral;
		return this;
	}
	public String getMember_coupon_id() {
		return member_coupon_id;
	}
	
	public OrderMerchantsBean setMember_coupon_id(String member_coupon_id) {
		this.member_coupon_id = member_coupon_id;
		return this;
	}
	public String getMember_group_buy_id() {
		return member_group_buy_id;
	}
	public OrderMerchantsBean setMember_group_buy_id(String member_group_buy_id) {
		this.member_group_buy_id = member_group_buy_id;
		return this;
	}
	public String getOrder_type() {
		return order_type;
	}
	public OrderMerchantsBean setOrder_type(String order_type) {
		this.order_type = order_type;
		return this;
	}
	public int getOrder_id() {
		return order_id;
	}
	public OrderMerchantsBean setOrder_id(int order_id) {
		this.order_id = order_id;
		return this;
	}
	public String getShopping_car_ids() {
		return shopping_car_ids;
	}
	public OrderMerchantsBean setShopping_car_ids(String shopping_car_ids) {
		this.shopping_car_ids = shopping_car_ids;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public OrderMerchantsBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getAddress_id() {
		return address_id;
	}
	public OrderMerchantsBean setAddress_id(String address_id) {
		this.address_id = address_id;
		return this;
	}
	
	public List<OrderBean> getOrderBeans() {
		return orderBeans;
	}
	public OrderMerchantsBean setOrderBeans(List<OrderBean> orderBeans) {
		this.orderBeans = orderBeans;
		return this;
	}
	
}
