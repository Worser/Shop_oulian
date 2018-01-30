package tst.project.bean.goods;

import java.util.List;
import tst.project.bean.order.OrderGoodsBean;

public class ShoppingCarBean
{
  private int car_id;
  private String member_id;
  private String goods_id;
  private String goods_num;
  private String is_delete;
  private String merchants_id;
  private String create_time;
  private String car_totla_price;
  private String supply_id;
  private GoodsBean goodsBean;
  private GoodsSupplyDataBean goodsSupplyDataBean;
  private List<OrderGoodsBean> orderGoodsBeans;

  public List<OrderGoodsBean> getOrderGoodsBeans()
  {
    return this.orderGoodsBeans;
  }
  public ShoppingCarBean setOrderGoodsBeans(List<OrderGoodsBean> orderGoodsBeans) {
    this.orderGoodsBeans = orderGoodsBeans;
    return this;
  }
  public GoodsSupplyDataBean getGoodsSupplyDataBean() {
    return this.goodsSupplyDataBean;
  }
  public ShoppingCarBean setGoodsSupplyDataBean(GoodsSupplyDataBean goodsSupplyDataBean) {
    this.goodsSupplyDataBean = goodsSupplyDataBean;
    return this;
  }
  public int getCar_id() {
    return this.car_id;
  }
  public ShoppingCarBean setCar_id(int car_id) {
    this.car_id = car_id;
    return this;
  }
  public String getMember_id() {
    return this.member_id;
  }
  public ShoppingCarBean setMember_id(String member_id) {
    this.member_id = member_id;
    return this;
  }
  public String getGoods_id() {
    return this.goods_id;
  }
  public ShoppingCarBean setGoods_id(String goods_id) {
    this.goods_id = goods_id;
    return this;
  }
  public String getGoods_num() {
    return this.goods_num;
  }
  public ShoppingCarBean setGoods_num(String goods_num) {
    this.goods_num = goods_num;
    return this;
  }
  public String getIs_delete() {
    return this.is_delete;
  }
  public ShoppingCarBean setIs_delete(String is_delete) {
    this.is_delete = is_delete;
    return this;
  }
  public String getMerchants_id() {
    return this.merchants_id;
  }
  public ShoppingCarBean setMerchants_id(String merchants_id) {
    this.merchants_id = merchants_id;
    return this;
  }
  public String getCreate_time() {
    return this.create_time;
  }
  public ShoppingCarBean setCreate_time(String create_time) {
    this.create_time = create_time;
    return this;
  }
  public String getCar_totla_price() {
    return this.car_totla_price;
  }
  public ShoppingCarBean setCar_totla_price(String car_totla_price) {
    this.car_totla_price = car_totla_price;
    return this;
  }
  public String getSupply_id() {
    return this.supply_id;
  }
  public ShoppingCarBean setSupply_id(String supply_id) {
    this.supply_id = supply_id;
    return this;
  }
  public GoodsBean getGoodsBean() {
    return this.goodsBean;
  }
  public ShoppingCarBean setGoodsBean(GoodsBean goodsBean) {
    this.goodsBean = goodsBean;
    return this;
  }
}