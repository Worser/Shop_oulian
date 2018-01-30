package tst.project.bean.order;

import java.util.List;
import tst.project.bean.HostBean;
import tst.project.bean.address.AddressBean;
import tst.project.bean.goods.ShoppingCarBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;

public class OrderBean {
	private int order_id;
	private String merchants_id;
	private String merchants_name;
	private String merchants_account_name;
	private String member_id;
	private String nick_name;
	private String member_account;
	private String order_no;
	private String address_id;
	private String mobile;
	private String name;
	private String province;
	private String city;
	private String country;
	private String detailed_address;
	private String zip_code;
	private String order_state;
	private String order_state_show;
	private String create_time;
	private String is_delete;
	private String remark;
	private String assessment_state;
	private String order_total_price;
	private String order_actual_price;
	private String order_pay_no;
	private String order_type;
	private String order_type_show;
	private String member_group_buy_id;
	private String order_charge;
	private String cur_time;
	private String start_time;
	private String end_time;
	private String order_states;
	private String logistics_no;
	private String logistics_pinyin;
	private String logistics_name;
	private String logistics_type;
	private String logistics_common;
	private String give_integral_value;
	private String is_deduct_integral;
	private String is_deduct_integral_show;
	private String deduct_integral_value;
	private String deduct_integral_price;
	private String deduct_integral_percent;
	private String member_coupon_id;
	private String coupon_full_price;
	private String coupon_price;
	private String is_free_express;
	private String express_free_price;
	private String express_price;
	private String refund_price;
	private String cancel_time;
	private String refund_deduct_integral;
	private String refund_give_integral;
	private String refund_coupon_id;
	private String valid_time;
	private String business_id;
	private String merchants_account_id;
	private String member_discount;
	private String is_allopatry;
	private String distribution_price;
	private String is_profit;
	private String invoice_type;
	private String invoice_rise_type;
	private String invoice_company_name;
	private String invoice_content;
	private String invise_ticket_phone;
	private String invise_ticket_email;
	private String invise_taxpayer_code;
	private String invise_register_time;
	private String invise_register_phone;
	private String invise_register_address;
	private String invise_bank_name;
	private String invise_bank_code;
	private String invise_price;
	private String pay_way;
	private String order_source_way;
	private String is_add_assessment;
	private String pay_time;
	private String accept_time;
	private String cancel_tiem;
	private String invise_no;
	private String register_assress;
	private String register_phone;
	private String bank_name;
	private String bank_no;
	private String invise_address;
	private String company_name;
	private String is_invise;
	private String is_invise_show;
	private String min_order_price;
	private String is_min_order;
	private String order_ids;
	private String my_account;
	private String goods_name;
	private String goods_num;
	private String goods_price;
	private String refund_time;
	private String custom_remark;
	private String goods_unit_price;
	private String brand_name;
	private String min_buy_num;
	private String min_pack_num;
	private String give_time;
	private String data_categories;
	private String remark_send_time;
	private String refuse_time;
	private int refuse_time_mis;
	private String receive_time;
	private String is_merchants_invise;
	private String merchants_invise_no;
	private String is_platform_invise;
	private String platform_invise_no;
	private String refuse_reason;
	private HostBean hostBean;
	private MemberBean memberBean;
	private AddressBean addressBean;
	private MerchantsBean merchantsBean;
	private MemberBean memberMerchantsBean;
	private List<OrderGoodsBean> orderGoodsBeans;
	private List<LogisticsDetailBean> logisticsDetailBeans;
	private List<ShoppingCarBean> shoppingCarBeans;
	private ShoppingCarBean shoppingCarBean1;

	public String getIs_merchants_invise() {
		return this.is_merchants_invise;
	}

	public OrderBean setIs_merchants_invise(String is_merchants_invise) {
		this.is_merchants_invise = is_merchants_invise;
		return this;
	}

	public String getMerchants_invise_no() {
		return this.merchants_invise_no;
	}

	public OrderBean setMerchants_invise_no(String merchants_invise_no) {
		this.merchants_invise_no = merchants_invise_no;
		return this;
	}

	public String getIs_platform_invise() {
		return this.is_platform_invise;
	}

	public OrderBean setIs_platform_invise(String is_platform_invise) {
		this.is_platform_invise = is_platform_invise;
		return this;
	}

	public String getPlatform_invise_no() {
		return this.platform_invise_no;
	}

	public OrderBean setPlatform_invise_no(String platform_invise_no) {
		this.platform_invise_no = platform_invise_no;
		return this;
	}

	public String getMember_account() {
		return this.member_account;
	}

	public OrderBean setMember_account(String member_account) {
		this.member_account = member_account;
		return this;
	}

	public String getRemark_send_time() {
		return this.remark_send_time;
	}

	public OrderBean setRemark_send_time(String remark_send_time) {
		this.remark_send_time = remark_send_time;
		return this;
	}

	public String getGoods_unit_price() {
		return this.goods_unit_price;
	}

	public OrderBean setGoods_unit_price(String goods_unit_price) {
		this.goods_unit_price = goods_unit_price;
		return this;
	}

	public String getBrand_name() {
		return this.brand_name;
	}

	public OrderBean setBrand_name(String brand_name) {
		this.brand_name = brand_name;
		return this;
	}

	public String getMin_buy_num() {
		return this.min_buy_num;
	}

	public OrderBean setMin_buy_num(String min_buy_num) {
		this.min_buy_num = min_buy_num;
		return this;
	}

	public String getMin_pack_num() {
		return this.min_pack_num;
	}

	public OrderBean setMin_pack_num(String min_pack_num) {
		this.min_pack_num = min_pack_num;
		return this;
	}

	public String getGive_time() {
		return this.give_time;
	}

	public OrderBean setGive_time(String give_time) {
		this.give_time = give_time;
		return this;
	}

	public String getData_categories() {
		return this.data_categories;
	}

	public String getReceive_time() {
		return this.receive_time;
	}

	public OrderBean setReceive_time(String receive_time) {
		this.receive_time = receive_time;
		return this;
	}

	public OrderBean setData_categories(String data_categories) {
		this.data_categories = data_categories;
		return this;
	}

	public String getCustom_remark() {
		return this.custom_remark;
	}

	public OrderBean setCustom_remark(String custom_remark) {
		this.custom_remark = custom_remark;
		return this;
	}

	public String getRefund_time() {
		return this.refund_time;
	}

	public OrderBean setRefund_time(String refund_time) {
		this.refund_time = refund_time;
		return this;
	}

	public String getGoods_price() {
		return this.goods_price;
	}

	public OrderBean setGoods_price(String goods_price) {
		this.goods_price = goods_price;
		return this;
	}

	public String getLogistics_common() {
		return this.logistics_common;
	}

	public OrderBean setLogistics_common(String logistics_common) {
		this.logistics_common = logistics_common;
		return this;
	}

	public String getGoods_num() {
		return this.goods_num;
	}

	public OrderBean setGoods_num(String goods_num) {
		this.goods_num = goods_num;
		return this;
	}

	public String getGoods_name() {
		return this.goods_name;
	}

	public OrderBean setGoods_name(String goods_name) {
		this.goods_name = goods_name;
		return this;
	}

	public HostBean getHostBean() {
		return this.hostBean;
	}

	public OrderBean setHostBean(HostBean hostBean) {
		this.hostBean = hostBean;
		return this;
	}

	public String getOrder_ids() {
		return this.order_ids;
	}

	public OrderBean setOrder_ids(String order_ids) {
		this.order_ids = order_ids;
		return this;
	}

	public List<LogisticsDetailBean> getLogisticsDetailBeans() {
		return this.logisticsDetailBeans;
	}

	public OrderBean setLogisticsDetailBeans(
			List<LogisticsDetailBean> logisticsDetailBeans) {
		this.logisticsDetailBeans = logisticsDetailBeans;
		return this;
	}

	public String getIs_min_order() {
		return this.is_min_order;
	}

	public OrderBean setIs_min_order(String is_min_order) {
		this.is_min_order = is_min_order;
		return this;
	}

	public String getMin_order_price() {
		return this.min_order_price;
	}

	public OrderBean setMin_order_price(String min_order_price) {
		this.min_order_price = min_order_price;
		return this;
	}

	public String getIs_invise_show() {
		return this.is_invise_show;
	}

	public OrderBean setIs_invise_show(String is_invise_show) {
		this.is_invise_show = is_invise_show;
		return this;
	}

	public String getIs_invise() {
		return this.is_invise;
	}

	public OrderBean setIs_invise(String is_invise) {
		this.is_invise = is_invise;
		this.is_invise_show = ("1".equals(is_invise) ? "是" : "否");
		return this;
	}

	public String getInvise_no() {
		return this.invise_no;
	}

	public OrderBean setInvise_no(String invise_no) {
		this.invise_no = invise_no;
		return this;
	}

	public String getRegister_assress() {
		return this.register_assress;
	}

	public OrderBean setRegister_assress(String register_assress) {
		this.register_assress = register_assress;
		return this;
	}

	public String getRegister_phone() {
		return this.register_phone;
	}

	public OrderBean setRegister_phone(String register_phone) {
		this.register_phone = register_phone;
		return this;
	}

	public String getBank_name() {
		return this.bank_name;
	}

	public OrderBean setBank_name(String bank_name) {
		this.bank_name = bank_name;
		return this;
	}

	public String getBank_no() {
		return this.bank_no;
	}

	public OrderBean setBank_no(String bank_no) {
		this.bank_no = bank_no;
		return this;
	}

	public String getInvise_address() {
		return this.invise_address;
	}

	public OrderBean setInvise_address(String invise_address) {
		this.invise_address = invise_address;
		return this;
	}

	public String getCompany_name() {
		return this.company_name;
	}

	public OrderBean setCompany_name(String company_name) {
		this.company_name = company_name;
		return this;
	}

	public String getInvise_price() {
		return this.invise_price;
	}

	public OrderBean setInvise_price(String invise_price) {
		this.invise_price = invise_price;
		return this;
	}

	public String getIs_deduct_integral_show() {
		return this.is_deduct_integral_show;
	}

	public OrderBean setIs_deduct_integral_show(String is_deduct_integral_show) {
		this.is_deduct_integral_show = is_deduct_integral_show;
		return this;
	}

	public String getAccept_time() {
		return this.accept_time;
	}

	public OrderBean setAccept_time(String accept_time) {
		this.accept_time = accept_time;
		return this;
	}

	public String getLogistics_name() {
		return this.logistics_name;
	}

	public OrderBean setLogistics_name(String logistics_name) {
		this.logistics_name = logistics_name;
		return this;
	}

	public String getCur_time() {
		return this.cur_time;
	}

	public OrderBean setCur_time(String cur_time) {
		this.cur_time = cur_time;
		return this;
	}

	public MemberBean getMemberMerchantsBean() {
		return this.memberMerchantsBean;
	}

	public OrderBean setMemberMerchantsBean(MemberBean memberMerchantsBean) {
		this.memberMerchantsBean = memberMerchantsBean;
		return this;
	}

	public String getPay_time() {
		return this.pay_time;
	}

	public OrderBean setPay_time(String pay_time) {
		this.pay_time = pay_time;
		return this;
	}

	public String getLogistics_type() {
		return this.logistics_type;
	}

	public OrderBean setLogistics_type(String logistics_type) {
		this.logistics_type = logistics_type;
		return this;
	}

	public String getOrder_source_way() {
		return this.order_source_way;
	}

	public OrderBean setOrder_source_way(String order_source_way) {
		this.order_source_way = order_source_way;
		return this;
	}

	public String getIs_add_assessment() {
		return this.is_add_assessment;
	}

	public OrderBean setIs_add_assessment(String is_add_assessment) {
		this.is_add_assessment = is_add_assessment;
		return this;
	}

	public String getPay_way() {
		return this.pay_way;
	}

	public OrderBean setPay_way(String pay_way) {
		this.pay_way = pay_way;
		return this;
	}

	public String getInvise_register_address() {
		return this.invise_register_address;
	}

	public OrderBean setInvise_register_address(String invise_register_address) {
		this.invise_register_address = invise_register_address;
		return this;
	}

	public String getOrder_actual_price() {
		return this.order_actual_price;
	}

	public OrderBean setOrder_actual_price(String order_actual_price) {
		this.order_actual_price = order_actual_price;
		return this;
	}

	public String getInvoice_type() {
		return this.invoice_type;
	}

	public OrderBean setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
		return this;
	}

	public String getInvoice_rise_type() {
		return this.invoice_rise_type;
	}

	public OrderBean setInvoice_rise_type(String invoice_rise_type) {
		this.invoice_rise_type = invoice_rise_type;
		return this;
	}

	public String getInvoice_company_name() {
		return this.invoice_company_name;
	}

	public OrderBean setInvoice_company_name(String invoice_company_name) {
		this.invoice_company_name = invoice_company_name;
		return this;
	}

	public String getInvoice_content() {
		return this.invoice_content;
	}

	public OrderBean setInvoice_content(String invoice_content) {
		this.invoice_content = invoice_content;
		return this;
	}

	public String getInvise_ticket_phone() {
		return this.invise_ticket_phone;
	}

	public OrderBean setInvise_ticket_phone(String invise_ticket_phone) {
		this.invise_ticket_phone = invise_ticket_phone;
		return this;
	}

	public String getInvise_ticket_email() {
		return this.invise_ticket_email;
	}

	public OrderBean setInvise_ticket_email(String invise_ticket_email) {
		this.invise_ticket_email = invise_ticket_email;
		return this;
	}

	public String getInvise_taxpayer_code() {
		return this.invise_taxpayer_code;
	}

	public OrderBean setInvise_taxpayer_code(String invise_taxpayer_code) {
		this.invise_taxpayer_code = invise_taxpayer_code;
		return this;
	}

	public String getInvise_register_time() {
		return this.invise_register_time;
	}

	public OrderBean setInvise_register_time(String invise_register_time) {
		this.invise_register_time = invise_register_time;
		return this;
	}

	public String getInvise_register_phone() {
		return this.invise_register_phone;
	}

	public OrderBean setInvise_register_phone(String invise_register_phone) {
		this.invise_register_phone = invise_register_phone;
		return this;
	}

	public String getInvise_bank_name() {
		return this.invise_bank_name;
	}

	public OrderBean setInvise_bank_name(String invise_bank_name) {
		this.invise_bank_name = invise_bank_name;
		return this;
	}

	public String getInvise_bank_code() {
		return this.invise_bank_code;
	}

	public OrderBean setInvise_bank_code(String invise_bank_code) {
		this.invise_bank_code = invise_bank_code;
		return this;
	}

	public String getNick_name() {
		return this.nick_name;
	}

	public OrderBean setNick_name(String nick_name) {
		this.nick_name = nick_name;
		return this;
	}

	public String getDistribution_price() {
		return this.distribution_price;
	}

	public OrderBean setDistribution_price(String distribution_price) {
		this.distribution_price = distribution_price;
		return this;
	}

	public String getIs_profit() {
		return this.is_profit;
	}

	public OrderBean setIs_profit(String is_profit) {
		this.is_profit = is_profit;
		return this;
	}

	public String getIs_allopatry() {
		return this.is_allopatry;
	}

	public OrderBean setIs_allopatry(String is_allopatry) {
		this.is_allopatry = is_allopatry;
		return this;
	}

	public String getMerchants_account_name() {
		return this.merchants_account_name;
	}

	public OrderBean setMerchants_account_name(String merchants_account_name) {
		this.merchants_account_name = merchants_account_name;
		return this;
	}

	public String getLogistics_pinyin() {
		return this.logistics_pinyin;
	}

	public OrderBean setLogistics_pinyin(String logistics_pinyin) {
		this.logistics_pinyin = logistics_pinyin;
		return this;
	}

	public String getMerchants_name() {
		return this.merchants_name;
	}

	public OrderBean setMerchants_name(String merchants_name) {
		this.merchants_name = merchants_name;
		return this;
	}

	public MemberBean getMemberBean() {
		return this.memberBean;
	}

	public OrderBean setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
		return this;
	}

	public String getOrder_charge() {
		return this.order_charge;
	}

	public OrderBean setOrder_charge(String order_charge) {
		this.order_charge = order_charge;
		return this;
	}

	public String getMember_discount() {
		return this.member_discount;
	}

	public OrderBean setMember_discount(String member_discount) {
		this.member_discount = member_discount;
		return this;
	}

	public String getBusiness_id() {
		return this.business_id;
	}

	public OrderBean setBusiness_id(String business_id) {
		this.business_id = business_id;
		return this;
	}

	public String getMerchants_account_id() {
		return this.merchants_account_id;
	}

	public OrderBean setMerchants_account_id(String merchants_account_id) {
		this.merchants_account_id = merchants_account_id;
		return this;
	}

	public String getValid_time() {
		return this.valid_time;
	}

	public OrderBean setValid_time(String valid_time) {
		this.valid_time = valid_time;
		return this;
	}

	public String getRefund_coupon_id() {
		return this.refund_coupon_id;
	}

	public OrderBean setRefund_coupon_id(String refund_coupon_id) {
		this.refund_coupon_id = refund_coupon_id;
		return this;
	}

	public String getRefund_price() {
		return this.refund_price;
	}

	public OrderBean setRefund_price(String refund_price) {
		this.refund_price = refund_price;
		return this;
	}

	public String getRefund_deduct_integral() {
		return this.refund_deduct_integral;
	}

	public OrderBean setRefund_deduct_integral(String refund_deduct_integral) {
		this.refund_deduct_integral = refund_deduct_integral;
		return this;
	}

	public String getRefund_give_integral() {
		return this.refund_give_integral;
	}

	public OrderBean setRefund_give_integral(String refund_give_integral) {
		this.refund_give_integral = refund_give_integral;
		return this;
	}

	public String getGive_integral_value() {
		return this.give_integral_value;
	}

	public OrderBean setGive_integral_value(String give_integral_value) {
		this.give_integral_value = give_integral_value;
		return this;
	}

	public String getCoupon_full_price() {
		return this.coupon_full_price;
	}

	public OrderBean setCoupon_full_price(String coupon_full_price) {
		this.coupon_full_price = coupon_full_price;
		return this;
	}

	public String getCoupon_price() {
		return this.coupon_price;
	}

	public OrderBean setCoupon_price(String coupon_price) {
		this.coupon_price = coupon_price;
		return this;
	}

	public String getDeduct_integral_price() {
		return this.deduct_integral_price;
	}

	public OrderBean setDeduct_integral_price(String deduct_integral_price) {
		this.deduct_integral_price = deduct_integral_price;
		return this;
	}

	public String getIs_deduct_integral() {
		return this.is_deduct_integral;
	}

	public OrderBean setIs_deduct_integral(String is_deduct_integral) {
		this.is_deduct_integral = is_deduct_integral;
		this.is_deduct_integral_show = ("1".equals(is_deduct_integral) ? "是"
				: "否");
		return this;
	}

	public String getDeduct_integral_value() {
		return this.deduct_integral_value;
	}

	public OrderBean setDeduct_integral_value(String deduct_integral_value) {
		this.deduct_integral_value = deduct_integral_value;
		return this;
	}

	public String getDeduct_integral_percent() {
		return this.deduct_integral_percent;
	}

	public OrderBean setDeduct_integral_percent(String deduct_integral_percent) {
		this.deduct_integral_percent = deduct_integral_percent;
		return this;
	}

	public String getMember_coupon_id() {
		return this.member_coupon_id;
	}

	public OrderBean setMember_coupon_id(String member_coupon_id) {
		this.member_coupon_id = member_coupon_id;
		return this;
	}

	public String getIs_free_express() {
		return this.is_free_express;
	}

	public OrderBean setIs_free_express(String is_free_express) {
		this.is_free_express = is_free_express;
		return this;
	}

	public String getExpress_price() {
		return this.express_price;
	}

	public OrderBean setExpress_price(String express_price) {
		this.express_price = express_price;
		return this;
	}

	public String getOrder_state() {
		return this.order_state;
	}

	public OrderBean setOrder_state(String order_state) {
		this.order_state = order_state;
		this.order_state_show = ("refuse".equals(order_state) ? "已拒绝"
				: "wait_review".equals(order_state) ? "待审核" : "wait_invice"
						.equals(order_state) ? "待平台开票" : "wait_assessment"
						.equals(order_state) ? "待评价" : "wait_receive"
						.equals(order_state) ? "待确认收货" : "wait_send"
						.equals(order_state) ? "待发货" : "wait_group_buy"
						.equals(order_state) ? "待团购人满" : "wait_pay"
						.equals(order_state) ? "待付款" : "cancel"
						.equals(order_state) ? "已取消" : "已完成");
		return this;
	}

	public String getOrder_state_show() {
		return this.order_state_show;
	}

	public OrderBean setOrder_state_show(String order_state_show) {
		return this;
	}

	public String getLogistics_no() {
		return this.logistics_no;
	}

	public OrderBean setLogistics_no(String logistics_no) {
		this.logistics_no = logistics_no;
		return this;
	}

	public String getOrder_states() {
		return this.order_states;
	}

	public OrderBean setOrder_states(String order_states) {
		this.order_states = order_states;
		return this;
	}

	public String getStart_time() {
		return this.start_time;
	}

	public OrderBean setStart_time(String start_time) {
		this.start_time = start_time;
		return this;
	}

	public String getEnd_time() {
		return this.end_time;
	}

	public OrderBean setEnd_time(String end_time) {
		this.end_time = end_time;
		return this;
	}

	public String getMember_group_buy_id() {
		return this.member_group_buy_id;
	}

	public OrderBean setMember_group_buy_id(String member_group_buy_id) {
		this.member_group_buy_id = member_group_buy_id;
		return this;
	}

	public MerchantsBean getMerchantsBean() {
		return this.merchantsBean;
	}

	public OrderBean setMerchantsBean(MerchantsBean merchantsBean) {
		this.merchantsBean = merchantsBean;
		return this;
	}

	public List<OrderGoodsBean> getOrderGoodsBeans() {
		return this.orderGoodsBeans;
	}

	public OrderBean setOrderGoodsBeans(List<OrderGoodsBean> orderGoodsBeans) {
		this.orderGoodsBeans = orderGoodsBeans;
		return this;
	}

	public AddressBean getAddressBean() {
		return this.addressBean;
	}

	public OrderBean setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
		return this;
	}

	public int getOrder_id() {
		return this.order_id;
	}

	public OrderBean setOrder_id(int order_id) {
		this.order_id = order_id;
		return this;
	}

	public String getMerchants_id() {
		return this.merchants_id == null ? "0" : this.merchants_id;
	}

	public OrderBean setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
		return this;
	}

	public String getMember_id() {
		return this.member_id;
	}

	public OrderBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}

	public String getOrder_no() {
		return this.order_no;
	}

	public OrderBean setOrder_no(String order_no) {
		this.order_no = order_no;
		return this;
	}

	public String getAddress_id() {
		return this.address_id;
	}

	public OrderBean setAddress_id(String address_id) {
		this.address_id = address_id;
		return this;
	}

	public String getMobile() {
		return this.mobile;
	}

	public OrderBean setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public OrderBean setName(String name) {
		this.name = name;
		return this;
	}

	public String getProvince() {
		return this.province;
	}

	public OrderBean setProvince(String province) {
		this.province = province;
		return this;
	}

	public String getCity() {
		return this.city;
	}

	public OrderBean setCity(String city) {
		this.city = city;
		return this;
	}

	public String getCountry() {
		return this.country;
	}

	public OrderBean setCountry(String country) {
		this.country = country;
		return this;
	}

	public String getDetailed_address() {
		return this.detailed_address;
	}

	public OrderBean setDetailed_address(String detailed_address) {
		this.detailed_address = detailed_address;
		return this;
	}

	public String getZip_code() {
		return this.zip_code;
	}

	public OrderBean setZip_code(String zip_code) {
		this.zip_code = zip_code;
		return this;
	}

	public String getCreate_time() {
		return this.create_time;
	}

	public OrderBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}

	public String getIs_delete() {
		return this.is_delete;
	}

	public OrderBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}

	public String getRemark() {
		return this.remark;
	}

	public OrderBean setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public String getAssessment_state() {
		return this.assessment_state;
	}

	public OrderBean setAssessment_state(String assessment_state) {
		this.assessment_state = assessment_state;
		return this;
	}

	public String getOrder_total_price() {
		return this.order_total_price;
	}

	public OrderBean setOrder_total_price(String order_total_price) {
		this.order_total_price = order_total_price;
		return this;
	}

	public String getMy_account() {
		return this.my_account;
	}

	public OrderBean setMy_account(String my_account) {
		this.my_account = my_account;
		return this;
	}

	public String getOrder_pay_no() {
		return this.order_pay_no;
	}

	public OrderBean setOrder_pay_no(String order_pay_no) {
		this.order_pay_no = order_pay_no;
		return this;
	}

	public String getExpress_free_price() {
		return this.express_free_price;
	}

	public OrderBean setExpress_free_price(String express_free_price) {
		this.express_free_price = express_free_price;
		return this;
	}

	public String getOrder_type() {
		return this.order_type;
	}

	public OrderBean setOrder_type(String order_type) {
		this.order_type = order_type;
		this.order_type_show = ("zssg".equals(order_type) ? "svip升级"
				: "group_buy".equals(order_type) ? "团购商品" : "time_limit"
						.equals(order_type) ? "促销商品" : "goods"
						.equals(order_type) ? "普通商品" : "普通商品");
		return this;
	}

	public String getOrder_type_show() {
		return this.order_type_show;
	}

	public OrderBean setOrder_type_show(String order_type_show) {
		this.order_type_show = order_type_show;
		return this;
	}

	public List<ShoppingCarBean> getShoppingCarBeans() {
		return this.shoppingCarBeans;
	}

	public OrderBean setShoppingCarBeans(List<ShoppingCarBean> shoppingCarBeans) {
		this.shoppingCarBeans = shoppingCarBeans;
		return this;
	}

	public ShoppingCarBean getShoppingCarBean1() {
		return this.shoppingCarBean1;
	}

	public OrderBean setShoppingCarBean1(ShoppingCarBean shoppingCarBean1) {
		this.shoppingCarBean1 = shoppingCarBean1;
		return this;
	}

	public String getRefuse_reason() {
		return refuse_reason;
	}

	public OrderBean setRefuse_reason(String refuse_reason) {
		this.refuse_reason = refuse_reason;
		return this;
	}

	public String getRefuse_time() {
		return refuse_time;
	}

	public OrderBean setRefuse_time(String refuse_time) {
		this.refuse_time = refuse_time;
		return this;
	}

	public int getRefuse_time_mis() {
		return refuse_time_mis;
	}

	public OrderBean setRefuse_time_mis(int refuse_time_mis) {
		this.refuse_time_mis = refuse_time_mis;
		return this;
	}

	public String getCancel_tiem() {
		return cancel_tiem;
	}

	public OrderBean setCancel_tiem(String cancel_tiem) {
		this.cancel_tiem = cancel_tiem;
		return this;
	}

	public String getCancel_time() {
		return cancel_time;
	}

	public OrderBean setCancel_time(String cancel_time) {
		this.cancel_time = cancel_time;
		return this;
	}

}