package tst.project.bean.order;

import java.util.List;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsImgBean;

public class OrderGoodsBean
{
  private int order_goods_id;
  private String order_id;
  private String order_no;
  private String goods_id;
  private int goods_num;
  private double total_price;
  private String is_deduct_integral;
  private String assessment_state;
  private String goods_price;
  private String deduct_integral_value;
  private String deduct_integral_price;
  private String is_express;
  private String express_price;
  private String is_give_integral;
  private String give_integral_value;
  private String goods_name;
  private String goods_img;
  private String merchants_id;
  private String goods_url;
  private String goods_address;
  private String group_buy_price;
  private String promotion_price;
  private String promotion_goods_id;
  private String promotion_id;
  private String is_pre_sale;
  private String send_goods_time;
  private String refund_state;
  private String business_id;
  private String merchants_account_id;
  private String goods_sku;
  private String is_refund;
  private String refund_id;
  private String invoice_type;
  private float profits_price;
  private String activity_type;
  private int give_need_count;
  private int give_count;
  private float reduce_need_price;
  private float reduce_price;
  private String gift_desc;
  private String is_delete;
  private String is_profit;
  private int is_cross_border;
  private float cross_border_tax;
  private String goods_parameters_name;
  private String supply_id;
  private String goods_unit_price;
  private String data_categories;
  private String data_categories_show;
  private String give_time;
  private String min_buy_num;
  private String min_pack_num;
  private String brand_name;
  private String goods_desc;
  private String goods_stock;
  private String validity_day;
  private String validity_hour;
  private String validity_minute;
  private GoodsBean goodsBean;
  private List<GoodsImgBean> goodsImgBeans;
  private List<OrderParameterBean> orderParameterBeans;
  private List<OrderServiceBean> orderServiceBeans;

  public String getData_categories_show()
  {
    return this.data_categories_show;
  }
  public OrderGoodsBean setData_categories_show(String data_categories_show) {
    this.data_categories_show = data_categories_show;
    return this;
  }
  public String getBrand_name() {
    return this.brand_name;
  }
  public OrderGoodsBean setBrand_name(String brand_name) {
    this.brand_name = brand_name;
    return this;
  }
  public String getInvoice_type() {
    return this.invoice_type;
  }
  public OrderGoodsBean setInvoice_type(String invoice_type) {
    this.invoice_type = invoice_type;
    return this;
  }
  public String getGoods_unit_price() {
    return this.goods_unit_price;
  }
  public OrderGoodsBean setGoods_unit_price(String goods_unit_price) {
    this.goods_unit_price = goods_unit_price;
    return this;
  }
  public String getData_categories() {
    return this.data_categories;
  }
  public OrderGoodsBean setData_categories(String data_categories) {
    this.data_categories = data_categories;
    this.data_categories_show = 
      ("4".equals(data_categories) ? "调货" : "3".equals(data_categories) ? "促销" : "2".equals(data_categories) ? "期货" : "1".equals(data_categories) ? "现货" : 
      "其他");
    return this;
  }
  public String getGive_time() {
    return this.give_time;
  }
  public OrderGoodsBean setGive_time(String give_time) {
    this.give_time = give_time;
    return this;
  }
  public String getMin_buy_num() {
    return this.min_buy_num;
  }
  public OrderGoodsBean setMin_buy_num(String min_buy_num) {
    this.min_buy_num = min_buy_num;
    return this;
  }
  public String getMin_pack_num() {
    return this.min_pack_num;
  }
  public OrderGoodsBean setMin_pack_num(String min_pack_num) {
    this.min_pack_num = min_pack_num;
    return this;
  }
  public String getSupply_id() {
    return this.supply_id;
  }
  public OrderGoodsBean setSupply_id(String supply_id) {
    this.supply_id = supply_id;
    return this;
  }
  public String getGoods_parameters_name() {
    return this.goods_parameters_name;
  }
  public OrderGoodsBean setGoods_parameters_name(String goods_parameters_name) {
    this.goods_parameters_name = goods_parameters_name;
    return this;
  }
  public int getIs_cross_border() {
    return this.is_cross_border;
  }
  public OrderGoodsBean setIs_cross_border(int is_cross_border) {
    this.is_cross_border = is_cross_border;
    return this;
  }
  public float getCross_border_tax() {
    return this.cross_border_tax;
  }
  public OrderGoodsBean setCross_border_tax(float cross_border_tax) {
    this.cross_border_tax = cross_border_tax;
    return this;
  }
  public String getIs_profit() {
    return this.is_profit;
  }
  public OrderGoodsBean setIs_profit(String is_profit) {
    this.is_profit = is_profit;
    return this;
  }
  public String getGift_desc() {
    return this.gift_desc;
  }
  public OrderGoodsBean setGift_desc(String gift_desc) {
    this.gift_desc = gift_desc;
    return this;
  }
  public String getActivity_type() {
    return this.activity_type;
  }
  public OrderGoodsBean setActivity_type(String activity_type) {
    this.activity_type = activity_type;
    return this;
  }
  public int getGive_need_count() {
    return this.give_need_count;
  }
  public OrderGoodsBean setGive_need_count(int give_need_count) {
    this.give_need_count = give_need_count;
    return this;
  }
  public int getGive_count() {
    return this.give_count;
  }
  public OrderGoodsBean setGive_count(int give_count) {
    this.give_count = give_count;
    return this;
  }
  public float getReduce_need_price() {
    return this.reduce_need_price;
  }
  public OrderGoodsBean setReduce_need_price(float reduce_need_price) {
    this.reduce_need_price = reduce_need_price;
    return this;
  }
  public float getReduce_price() {
    return this.reduce_price;
  }
  public OrderGoodsBean setReduce_price(float reduce_price) {
    this.reduce_price = reduce_price;
    return this;
  }

  public float getProfits_price()
  {
    return this.profits_price;
  }
  public OrderGoodsBean setProfits_price(float profits_price) {
    this.profits_price = profits_price;
    return this;
  }
  public String getOrder_no() {
    return this.order_no;
  }
  public OrderGoodsBean setOrder_no(String order_no) {
    this.order_no = order_no;
    return this;
  }
  public double getTotal_price() {
    return this.total_price;
  }
  public OrderGoodsBean setTotal_price(double total_price) {
    this.total_price = total_price;
    return this;
  }
  public String getMerchants_account_id() {
    return this.merchants_account_id;
  }
  public OrderGoodsBean setMerchants_account_id(String merchants_account_id) {
    this.merchants_account_id = merchants_account_id;
    return this;
  }
  public String getIs_refund() {
    return this.is_refund;
  }
  public OrderGoodsBean setIs_refund(String is_refund) {
    this.is_refund = is_refund;
    return this;
  }

  public String getRefund_id() {
    return this.refund_id;
  }
  public OrderGoodsBean setRefund_id(String refund_id) {
    this.refund_id = refund_id;
    return this;
  }
  public String getGoods_sku() {
    return this.goods_sku;
  }
  public OrderGoodsBean setGoods_sku(String goods_sku) {
    this.goods_sku = goods_sku;
    return this;
  }
  public String getBusiness_id() {
    return this.business_id;
  }
  public OrderGoodsBean setBusiness_id(String business_id) {
    this.business_id = business_id;
    return this;
  }
  public String getRefund_state() {
    return this.refund_state;
  }
  public OrderGoodsBean setRefund_state(String refund_state) {
    this.refund_state = refund_state;
    return this;
  }
  public String getIs_pre_sale() {
    return this.is_pre_sale;
  }
  public OrderGoodsBean setIs_pre_sale(String is_pre_sale) {
    this.is_pre_sale = is_pre_sale;
    return this;
  }
  public String getSend_goods_time() {
    return this.send_goods_time;
  }
  public OrderGoodsBean setSend_goods_time(String send_goods_time) {
    this.send_goods_time = send_goods_time;
    return this;
  }
  public String getPromotion_id() {
    return this.promotion_id;
  }
  public OrderGoodsBean setPromotion_id(String promotion_id) {
    this.promotion_id = promotion_id;
    return this;
  }
  public String getGroup_buy_price() {
    return this.group_buy_price;
  }
  public OrderGoodsBean setGroup_buy_price(String group_buy_price) {
    this.group_buy_price = group_buy_price;
    return this;
  }
  public String getPromotion_price() {
    return this.promotion_price;
  }
  public OrderGoodsBean setPromotion_price(String promotion_price) {
    this.promotion_price = promotion_price;
    return this;
  }
  public String getPromotion_goods_id() {
    return this.promotion_goods_id;
  }
  public OrderGoodsBean setPromotion_goods_id(String promotion_goods_id) {
    this.promotion_goods_id = promotion_goods_id;
    return this;
  }
  public List<GoodsImgBean> getGoodsImgBeans() {
    return this.goodsImgBeans;
  }
  public OrderGoodsBean setGoodsImgBeans(List<GoodsImgBean> goodsImgBeans) {
    this.goodsImgBeans = goodsImgBeans;
    return this;
  }
  public String getGoods_price() {
    return this.goods_price;
  }
  public OrderGoodsBean setGoods_price(String goods_price) {
    this.goods_price = goods_price;
    return this;
  }
  public String getDeduct_integral_value() {
    return this.deduct_integral_value;
  }
  public OrderGoodsBean setDeduct_integral_value(String deduct_integral_value) {
    this.deduct_integral_value = deduct_integral_value;
    return this;
  }
  public String getDeduct_integral_price() {
    return this.deduct_integral_price;
  }
  public OrderGoodsBean setDeduct_integral_price(String deduct_integral_price) {
    this.deduct_integral_price = deduct_integral_price;
    return this;
  }
  public String getIs_express() {
    return this.is_express;
  }
  public OrderGoodsBean setIs_express(String is_express) {
    this.is_express = is_express;
    return this;
  }
  public String getExpress_price() {
    return this.express_price;
  }
  public OrderGoodsBean setExpress_price(String express_price) {
    this.express_price = express_price;
    return this;
  }
  public String getIs_give_integral() {
    return this.is_give_integral;
  }
  public OrderGoodsBean setIs_give_integral(String is_give_integral) {
    this.is_give_integral = is_give_integral;
    return this;
  }
  public String getGive_integral_value() {
    return this.give_integral_value;
  }
  public OrderGoodsBean setGive_integral_value(String give_integral_value) {
    this.give_integral_value = give_integral_value;
    return this;
  }
  public String getGoods_name() {
    return this.goods_name;
  }
  public OrderGoodsBean setGoods_name(String goods_name) {
    this.goods_name = goods_name;
    return this;
  }
  public String getGoods_img() {
    return this.goods_img;
  }
  public OrderGoodsBean setGoods_img(String goods_img) {
    this.goods_img = goods_img;
    return this;
  }
  public String getMerchants_id() {
    return this.merchants_id;
  }
  public OrderGoodsBean setMerchants_id(String merchants_id) {
    this.merchants_id = merchants_id;
    return this;
  }
  public String getGoods_url() {
    return this.goods_url;
  }
  public OrderGoodsBean setGoods_url(String goods_url) {
    this.goods_url = goods_url;
    return this;
  }
  public String getGoods_address() {
    return this.goods_address;
  }
  public OrderGoodsBean setGoods_address(String goods_address) {
    this.goods_address = goods_address;
    return this;
  }

  public List<OrderServiceBean> getOrderServiceBeans()
  {
    return this.orderServiceBeans;
  }
  public OrderGoodsBean setOrderServiceBeans(List<OrderServiceBean> orderServiceBeans) {
    this.orderServiceBeans = orderServiceBeans;
    return this;
  }
  public List<OrderParameterBean> getOrderParameterBeans() {
    return this.orderParameterBeans;
  }
  public OrderGoodsBean setOrderParameterBeans(List<OrderParameterBean> orderParameterBeans) {
    this.orderParameterBeans = orderParameterBeans;
    return this;
  }
  public String getIs_deduct_integral() {
    return this.is_deduct_integral;
  }
  public OrderGoodsBean setIs_deduct_integral(String is_deduct_integral) {
    this.is_deduct_integral = is_deduct_integral;
    return this;
  }
  public int getOrder_goods_id() {
    return this.order_goods_id;
  }
  public OrderGoodsBean setOrder_goods_id(int order_goods_id) {
    this.order_goods_id = order_goods_id;
    return this;
  }
  public String getOrder_id() {
    return this.order_id;
  }
  public OrderGoodsBean setOrder_id(String order_id) {
    this.order_id = order_id;
    return this;
  }
  public String getGoods_id() {
    return this.goods_id;
  }
  public OrderGoodsBean setGoods_id(String goods_id) {
    this.goods_id = goods_id;
    return this;
  }
  public int getGoods_num() {
    return this.goods_num;
  }
  public OrderGoodsBean setGoods_num(int goods_num) {
    this.goods_num = goods_num;
    return this;
  }
  public GoodsBean getGoodsBean() {
    return this.goodsBean;
  }
  public OrderGoodsBean setGoodsBean(GoodsBean goodsBean) {
    this.goodsBean = goodsBean;
    return this;
  }
  public String getAssessment_state() {
    return this.assessment_state;
  }
  public OrderGoodsBean setAssessment_state(String assessment_state) {
    this.assessment_state = assessment_state;
    return this;
  }
  public String getIs_delete() {
    return this.is_delete;
  }
  public OrderGoodsBean setIs_delete(String is_delete) {
    this.is_delete = is_delete;
    return this;
  }
  public String getValidity_day() {
    return this.validity_day;
  }
  public OrderGoodsBean setValidity_day(String validity_day) {
    this.validity_day = validity_day;
    return this;
  }
  public String getValidity_hour() {
    return this.validity_hour;
  }
  public OrderGoodsBean setValidity_hour(String validity_hour) {
    this.validity_hour = validity_hour;
    return this;
  }
  public String getValidity_minute() {
    return this.validity_minute;
  }
  public OrderGoodsBean setValidity_minute(String validity_minute) {
    this.validity_minute = validity_minute;
    return this;
  }
  public String getGoods_desc() {
    return this.goods_desc;
  }
  public OrderGoodsBean setGoods_desc(String goods_desc) {
    this.goods_desc = goods_desc;
    return this;
  }
  public String getGoods_stock() {
    return this.goods_stock;
  }
  public OrderGoodsBean setGoods_stock(String goods_stock) {
    this.goods_stock = goods_stock;
    return this;
  }
}