package tst.project.bean.goods;

import java.util.List;

import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.MemberBean;


public class GoodsFindBuyBean {

	private Integer find_id;
	private String member_id;
	private String merchants_id;
	private String merchants_name;
	private String company_remark;
	private String goods_name;
	private String goods_num;
	private String target_unit_price; //目标单价
	private String data_categories;
	private String create_time;
	private String end_time; //截止日期
	private String is_delete;
	private String totalPrice; //求购总价
	
	private String data_categories_show;
	private String end_time_show;
	
	private String goods_img;
	private String find_ids;
	private String goods_uuid;
	private String brand_name;
	private String brand_img;
	
	private MemberBean memberBean;
	private String is_collection;
	private String collection_id;	
	private List<OfflineQuotationBean> offlineQuotationBeans;
    private List<CustomerServiceBean> customerServiceBeans; //供应商QQ数组
	
    
    
	public String getCompany_remark() {
		return company_remark;
	}
	public GoodsFindBuyBean setCompany_remark(String company_remark) {
		this.company_remark = company_remark;
		return this;
	}
	public String getMerchants_id() {
		return merchants_id;
	}
	public GoodsFindBuyBean setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
		return this;
	}
	public String getMerchants_name() {
		return merchants_name;
	}
	public GoodsFindBuyBean setMerchants_name(String merchants_name) {
		this.merchants_name = merchants_name;
		return this;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public GoodsFindBuyBean setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
		return this;
	}
	public String getIs_collection() {
		return is_collection;
	}
	public GoodsFindBuyBean setIs_collection(String is_collection) {
		this.is_collection = is_collection;
		return this;
	}
	public String getCollection_id() {
		return collection_id;
	}
	public GoodsFindBuyBean setCollection_id(String collection_id) {
		this.collection_id = collection_id;
		return this;
	}
	public List<CustomerServiceBean> getCustomerServiceBeans() {
		return customerServiceBeans;
	}
	public GoodsFindBuyBean setCustomerServiceBeans(
			List<CustomerServiceBean> customerServiceBeans) {
		this.customerServiceBeans = customerServiceBeans;
		return this;
	}
	
	public List<OfflineQuotationBean> getOfflineQuotationBeans() {
		return offlineQuotationBeans;
	}
	public GoodsFindBuyBean setOfflineQuotationBeans(
			List<OfflineQuotationBean> offlineQuotationBeans) {
		this.offlineQuotationBeans = offlineQuotationBeans;
		return this;
	}
	public String getBrand_img() {
		return brand_img;
	}
	public GoodsFindBuyBean setBrand_img(String brand_img) {
		this.brand_img = brand_img;
		return this;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public GoodsFindBuyBean setBrand_name(String brand_name) {
		this.brand_name = brand_name;
		return this;
	}
	public MemberBean getMemberBean() {
		return memberBean;
	}
	public GoodsFindBuyBean setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
		return this;
	}
	public String getGoods_uuid() {
		return goods_uuid;
	}
	public GoodsFindBuyBean setGoods_uuid(String goods_uuid) {
		this.goods_uuid = goods_uuid;
		return this;
	}
	public String getFind_ids() {
		return find_ids;
	}
	public GoodsFindBuyBean setFind_ids(String find_ids) {
		this.find_ids = find_ids;
		return this;
	}
	public String getGoods_img() {
		return goods_img;
	}
	public GoodsFindBuyBean setGoods_img(String goods_img) {
		this.goods_img = goods_img;
		return this;
	}
	public Integer getFind_id() {
		return find_id;
	}
	public GoodsFindBuyBean setFind_id(Integer find_id) {
		this.find_id = find_id;
		return this;
	}
	public String getMember_id() {
		return member_id;
	}
	public GoodsFindBuyBean setMember_id(String member_id) {
		this.member_id = member_id;
		return this;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public GoodsFindBuyBean setGoods_name(String goods_name) {
		this.goods_name = goods_name;
		return this;
	}
	public String getGoods_num() {
		return goods_num;
	}
	public GoodsFindBuyBean setGoods_num(String goods_num) {
		this.goods_num = goods_num;
		return this;
	}
	public String getTarget_unit_price() {
		return target_unit_price;
	}
	public GoodsFindBuyBean setTarget_unit_price(String target_unit_price) {
		this.target_unit_price = target_unit_price;
		return this;
	}
	public String getData_categories() {
		return data_categories;
	}
	public GoodsFindBuyBean setData_categories(String data_categories) {
		this.data_categories = data_categories;
		this.data_categories_show = "1".equals(data_categories)?"现货":
			 ("2".equals(data_categories)?"期货":
				 ("3".equals(data_categories)?"促销":null));
		return this;
	}
	public String getCreate_time() {
		return create_time;
	}
	public GoodsFindBuyBean setCreate_time(String create_time) {
		this.create_time = create_time;
		return this;
	}
	public String getEnd_time() {
		return end_time;
	}
	public GoodsFindBuyBean setEnd_time(String end_time) {
		this.end_time = end_time;
		return this;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public GoodsFindBuyBean setIs_delete(String is_delete) {
		this.is_delete = is_delete;
		return this;
	}
	public String getData_categories_show() {
		return data_categories_show;
	}
	public GoodsFindBuyBean setData_categories_show(String data_categories_show) {
		this.data_categories_show = data_categories_show;
		return this;
	}
	public String getEnd_time_show() {
		return end_time_show;
	}
	public GoodsFindBuyBean setEnd_time_show(String end_time_show) {
		this.end_time_show = end_time_show;
		return this;
	}
	
}
