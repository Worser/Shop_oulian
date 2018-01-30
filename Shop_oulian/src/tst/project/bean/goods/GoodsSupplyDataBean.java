package tst.project.bean.goods;

public class GoodsSupplyDataBean
{
  private Integer supply_id;
  private String goods_name;
  private String goods_id;
  private int goods_stock;
  private String min_buy_num;
  private String goods_unit_price;
  private String data_categories_show;
  private String data_categories;
  private String give_time;
  private String is_delete;
  private String create_time;
  private String update_time;
  private String validity_day;
  private String validity_hour;
  private String validity_minute;
  private String price;
  private String merchants_id;

  public String getMerchants_id()
  {
    return this.merchants_id;
  }
  public GoodsSupplyDataBean setMerchants_id(String merchants_id) {
    this.merchants_id = merchants_id;
    return this;
  }
  public String getPrice() {
    return this.price;
  }
  public GoodsSupplyDataBean setPrice(String price) {
    this.price = price;
    return this;
  }
  public int getGoods_stock() {
    return this.goods_stock;
  }
  public GoodsSupplyDataBean setGoods_stock(int goods_stock) {
    this.goods_stock = goods_stock;
    return this;
  }
  public String getData_categories_show() {
    return this.data_categories_show;
  }
  public GoodsSupplyDataBean setData_categories_show(String data_categories_show) {
    this.data_categories_show = data_categories_show;
    return this;
  }
  public String getGoods_name() {
    return this.goods_name;
  }
  public GoodsSupplyDataBean setGoods_name(String goods_name) {
    this.goods_name = goods_name;
    return this;
  }
  public String getGoods_id() {
    return this.goods_id;
  }
  public GoodsSupplyDataBean setGoods_id(String goods_id) {
    this.goods_id = goods_id;
    return this;
  }
  public Integer getSupply_id() {
    return this.supply_id;
  }
  public GoodsSupplyDataBean setSupply_id(Integer supply_id) {
    this.supply_id = supply_id;
    return this;
  }
  public String getMin_buy_num() {
    return this.min_buy_num;
  }
  public GoodsSupplyDataBean setMin_buy_num(String min_buy_num) {
    this.min_buy_num = min_buy_num;
    return this;
  }
  public String getGoods_unit_price() {
    return this.goods_unit_price;
  }
  public GoodsSupplyDataBean setGoods_unit_price(String goods_unit_price) {
    this.goods_unit_price = ("0.0".equals(goods_unit_price) ? "询议" : goods_unit_price);
    return this;
  }
  public String getData_categories() {
    return this.data_categories;
  }
  public GoodsSupplyDataBean setData_categories(String data_categories) {
    this.data_categories = data_categories;
    this.data_categories_show = 
      ("4".equals(data_categories) ? "调货" : "3".equals(data_categories) ? "促销" : "2".equals(data_categories) ? "期货" : "1".equals(data_categories) ? "现货" : 
      "其他");
    return this;
  }
  public String getGive_time() {
    return this.give_time;
  }
  public GoodsSupplyDataBean setGive_time(String give_time) {
    this.give_time = give_time;
    return this;
  }
  public String getIs_delete() {
    return this.is_delete;
  }
  public GoodsSupplyDataBean setIs_delete(String isDelete) {
    this.is_delete = isDelete;
    return this;
  }
  public String getCreate_time() {
    return this.create_time;
  }
  public GoodsSupplyDataBean setCreate_time(String create_time) {
    this.create_time = create_time;
    return this;
  }
  public String getUpdate_time() {
    return this.update_time;
  }
  public GoodsSupplyDataBean setUpdate_time(String update_time) {
    this.update_time = update_time;
    return this;
  }
  public String getValidity_day() {
    return this.validity_day;
  }
  public GoodsSupplyDataBean setValidity_day(String validity_day) {
    this.validity_day = validity_day;
    return this;
  }
  public String getValidity_hour() {
    return this.validity_hour;
  }
  public GoodsSupplyDataBean setValidity_hour(String validity_hour) {
    this.validity_hour = validity_hour;
    return this;
  }
  public String getValidity_minute() {
    return this.validity_minute;
  }
  public GoodsSupplyDataBean setValidity_minute(String validity_minute) {
    this.validity_minute = validity_minute;
    return this;
  }
}