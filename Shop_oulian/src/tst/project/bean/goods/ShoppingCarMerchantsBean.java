package tst.project.bean.goods;

import java.util.ArrayList;
import java.util.List;

import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;

public class ShoppingCarMerchantsBean {
	private String merchants_id;
	private String express_price;
	private MerchantsBean merchantsBean;
	private MemberBean memberBean;
	private List<ShoppingCarBean> shoppingCarBeans;		
	private List<ShoppingCarBean> shoppingNoCrossBorderCarBeans;//非跨境商品	
	
	public MemberBean getMemberBean() {
		return memberBean;
	}
	public ShoppingCarMerchantsBean setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
		return this;
	}
	public String getExpress_price() {
		return express_price;
	}
	public ShoppingCarMerchantsBean setExpress_price(String express_price) {
		this.express_price = express_price;
		return this;
	}
	public List<ShoppingCarBean> getShoppingNoCrossBorderCarBeans() {
		return shoppingNoCrossBorderCarBeans;
	}
	public ShoppingCarMerchantsBean setShoppingNoCrossBorderCarBeans(List<ShoppingCarBean> shoppingNoCrossBorderCarBeans) {
		this.shoppingNoCrossBorderCarBeans = shoppingNoCrossBorderCarBeans;
		return this;
	}
	public MerchantsBean getMerchantsBean() {
		return merchantsBean;
	}
	public ShoppingCarMerchantsBean setMerchantsBean(MerchantsBean merchantsBean) {
		this.merchantsBean = merchantsBean;
		return this;
	}
	public String getMerchants_id() {
		return merchants_id;
	}
	public ShoppingCarMerchantsBean setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
		return this;
	}
	public List<ShoppingCarBean> getShoppingCarBeans() {
		if(shoppingCarBeans==null){
			shoppingCarBeans=new ArrayList<ShoppingCarBean>();
		}
		return shoppingCarBeans;
	}
	public ShoppingCarMerchantsBean setShoppingCarBeans(List<ShoppingCarBean> shoppingCarBeans) {
		this.shoppingCarBeans = shoppingCarBeans;
		return this;
	}
	
	
}
