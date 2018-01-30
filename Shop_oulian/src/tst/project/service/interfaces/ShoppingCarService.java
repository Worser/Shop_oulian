package tst.project.service.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.activity.ActivityBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsParameterBean;
import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.bean.goods.ShoppingCarBean;
import tst.project.bean.goods.ShoppingCarMerchantsBean;
import tst.project.bean.member.CustomerServiceBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.dao.interfaces.ShoppingCarDao;
import tst.project.page.PageBean;
import tst.project.utils.NumberUtils;


@Service
@Transactional(rollbackFor = { SQLException.class })
public class ShoppingCarService {
	@Resource
	ShoppingCarDao shoppingCarDao;

	@Resource
	GoodsServiceI goodsServiceI;

	@Resource
	MerchantsServiceI merchantsServiceI;

	@Resource
	ActivityService activityService;
	
	@Resource
	GoodsSupplyDataService goodsSupplyDataService;
	
	@Resource
	MemberService memberService;
	/**
	 * 添加购物车
	 * 
	 * @param shoppingCarBean
	 * @return
	 * @throws Exception
	 */
	public int insertShoppingCar(ShoppingCarBean shoppingCarBean) throws Exception {
		if (shoppingCarBean != null) {		
			GoodsBean goodsBean = goodsServiceI
					.getOneGoodsDetail(new GoodsBean().setGoods_id(Integer.valueOf(shoppingCarBean.getGoods_id())));
			if (goodsBean == null) {
				throw new Exception("商品已下架");
			} 
		}
		return shoppingCarDao.insertShoppingCar(shoppingCarBean);
	}

	/**
	 * 用户购物车商品
	 * 
	 * @param memberBean
	 * @return
	 */
	public int getMemberShoppingCarCount(MemberBean memberBean) {
		return shoppingCarDao.getMemberShoppingCarCount(memberBean);
	}

	/**
	 * 查看该商品是否已经加过购物车
	 * 
	 * @return
	 */
	public ShoppingCarBean getShoppingCarByGoodsIdAndMemberId(ShoppingCarBean shoppingCarBean) {
		ShoppingCarBean shoppingCarBean2 = shoppingCarDao.getShoppingCarByGoodsIdAndMemberId(shoppingCarBean);

		return shoppingCarBean2;
	}

	/**
	 * 获得自己的购物车列表(B2B)
	 * 
	 * @param shoppingCarBean
	 * @return
	 */
	public List<ShoppingCarBean> getShoppingCarB2C(ShoppingCarBean shoppingCarBean) {
		List<ShoppingCarBean> shoppingCarBeans = shoppingCarDao.getShoppingCarB2C(shoppingCarBean);
		for (int i = 0; i < shoppingCarBeans.size(); i++) {

			GoodsBean goodsBean = goodsServiceI.getOneGoodsDetail(
					new GoodsBean().setGoods_id(Integer.valueOf(shoppingCarBeans.get(i).getGoods_id())));
			shoppingCarBeans.get(i).setGoodsBean(goodsBean);
		}
		return shoppingCarBeans;
	}

	/**
	 * 获得自己的购物车列表(B2B2C)
	 * 
	 * @param shoppingCarBean
	 * @return
	 * @throws Exception 
	 */
	public List<ShoppingCarMerchantsBean> getShoppingCars(ShoppingCarBean shoppingCarBean, PageBean pageBean) throws Exception {
		List<ShoppingCarMerchantsBean> shoppingCarMerchantsBeans = shoppingCarDao.getShoppingCars(shoppingCarBean,
				pageBean);
		for (int i = 0; i < shoppingCarMerchantsBeans.size(); i++) {
			if (shoppingCarMerchantsBeans.get(i).getMerchants_id() != null
					&& !shoppingCarMerchantsBeans.get(i).getMerchants_id().equals("")) {
				List<ShoppingCarBean> shoppingCarBeans = getShoppingCarsByMerchants(
						shoppingCarBean.setMerchants_id(shoppingCarMerchantsBeans.get(i).getMerchants_id()));
				
				MerchantsBean merchantsBean = merchantsServiceI.getOneMerchantsDetail(new MerchantsBean()
						.setMerchants_id(Integer.valueOf(shoppingCarMerchantsBeans.get(i).getMerchants_id())));
				shoppingCarMerchantsBeans.get(i).setMerchantsBean(merchantsBean);
				
				MemberBean memberBean=memberService.getMemberByMerchants(new MemberBean()
						.setMerchants_id(shoppingCarMerchantsBeans.get(i).getMerchants_id()));
				
				List<CustomerServiceBean> customerServiceBeans=memberService.getServiceQQByMerchantsId(new CustomerServiceBean().setMerchants_id(shoppingCarMerchantsBeans.get(i).getMerchants_id()));		
				memberBean.setCustomerServiceBeans(customerServiceBeans);
				
				shoppingCarMerchantsBeans.get(i).setMemberBean(memberBean);
				
				for (int j = 0; j < shoppingCarBeans.size(); j++) {
					ShoppingCarBean shoppingCarBean2=shoppingCarBeans.get(j);
					
					float car_total_price = 0;					
					GoodsBean goodsBean = goodsServiceI.getMerchantsGoodsData(
							new GoodsBean().setGoods_id(Integer.valueOf(shoppingCarBean2.getGoods_id())));
					if (goodsBean == null) {
						throw new Exception("商品已下架");
					}
					
					GoodsSupplyDataBean goodsSupplyDataBean=goodsSupplyDataService.getGoodsSupplyDataByID(new GoodsSupplyDataBean()
																	.setSupply_id(NumberUtils.Integer(shoppingCarBean2.getSupply_id())));
					if(goodsSupplyDataBean==null){
						throw new Exception("此类型已下架");
					}else{
						car_total_price += NumberUtils.Integer(shoppingCarBean2.getGoods_num())
								*NumberUtils.Float(goodsSupplyDataBean.getGoods_unit_price());
					}
					shoppingCarBean2.setCar_totla_price(car_total_price + "");
					shoppingCarBean2.setGoodsBean(goodsBean);
					shoppingCarBean2.setGoodsSupplyDataBean(goodsSupplyDataBean);
				}
				shoppingCarMerchantsBeans.get(i).setShoppingCarBeans(shoppingCarBeans);
			}
		}
		return shoppingCarMerchantsBeans;
	}
	


	public List<ShoppingCarMerchantsBean> getShoppingCarsWithCarids(ShoppingCarBean shoppingCarBean, String[] car_ids) throws Exception {
		List<ShoppingCarMerchantsBean> shoppingCarMerchantsBeansTemp = new ArrayList<ShoppingCarMerchantsBean>();

		List<ShoppingCarMerchantsBean> shoppingCarMerchantsBeans = shoppingCarDao.getShoppingCars(shoppingCarBean);
		for (int i = 0; i < shoppingCarMerchantsBeans.size(); i++) {
		
			List<ShoppingCarBean> shoppingCarBeansTemp = new ArrayList<ShoppingCarBean>();
			
			MerchantsBean merchantsBean=merchantsServiceI
					.getOneMerchantsDetail(new MerchantsBean().setMerchants_id(NumberUtils.Integer(shoppingCarMerchantsBeans.get(i).getMerchants_id())));
						
			
			
			List<ShoppingCarBean> shoppingCarBeans = getShoppingCarsByMerchants(
					shoppingCarBean.setMerchants_id(shoppingCarMerchantsBeans.get(i).getMerchants_id()));
			boolean is_hava = false;
			for (int j = 0; j < car_ids.length; j++) {
				for (int l = 0; l < shoppingCarBeans.size(); l++) {
					if (car_ids[j].equals(shoppingCarBeans.get(l).getCar_id() + "")) {
						is_hava = true;
						shoppingCarBeansTemp.add(shoppingCarBeans.get(l));
					}
				}
			}
			
			float express_price=0;
			for (int j = 0; j < shoppingCarBeansTemp.size(); j++) {
				float car_total_price = 0;
				float car_total_pc_price = 0;
				
				GoodsBean goodsBean = goodsServiceI.getMerchantsGoodsData(new GoodsBean().setGoods_id(Integer.valueOf(shoppingCarBeansTemp.get(j).getGoods_id())));
				if (goodsBean == null) {
					throw new Exception("商品已下架");
				}
				
				GoodsSupplyDataBean goodsSupplyDataBean=goodsSupplyDataService.getGoodsSupplyDataByID(new GoodsSupplyDataBean()
						.setSupply_id(NumberUtils.Integer(shoppingCarBeansTemp.get(j).getSupply_id())));
				if(goodsSupplyDataBean==null){
					throw new Exception("此类型已下架");
				}else {
					car_total_price +=NumberUtils.Integer(shoppingCarBeansTemp.get(j)
							.getGoods_num())*Float.valueOf(goodsSupplyDataBean.getGoods_unit_price());
					car_total_pc_price+=NumberUtils.Integer(shoppingCarBeansTemp.get(j)
							.getGoods_num())*Float.valueOf(goodsSupplyDataBean.getGoods_unit_price());
					if(!"1".equals(goodsBean.getIs_express())){
						if(merchantsBean==null){
							throw new Exception("该商家已不存在");
						}
						if(car_total_pc_price<NumberUtils.Float(merchantsBean.getExpress_free_price())){
							express_price+=NumberUtils.Float(goodsBean.getExpress_price());
						}
					}
				}
			
				
				shoppingCarBeansTemp.get(j).setCar_totla_price(car_total_price + "");
				shoppingCarBeansTemp.get(j).setGoodsBean(goodsBean);
				shoppingCarBeansTemp.get(j).setGoodsSupplyDataBean(goodsSupplyDataBean);
			}
			shoppingCarMerchantsBeans.get(i).setExpress_price(express_price+"");
			shoppingCarMerchantsBeans.get(i).setShoppingCarBeans(shoppingCarBeansTemp);
			if (is_hava) {
				shoppingCarMerchantsBeans.get(i).setMerchantsBean(merchantsBean);
				MemberBean memberBean=memberService.getMemberByMerchants(new MemberBean()
						.setMerchants_id(shoppingCarMerchantsBeans.get(i).getMerchants_id()));
				
				List<CustomerServiceBean> customerServiceBeans=memberService.getServiceQQByMerchantsId(new CustomerServiceBean().setMerchants_id(shoppingCarMerchantsBeans.get(i).getMerchants_id()));		
				memberBean.setCustomerServiceBeans(customerServiceBeans);
				
				shoppingCarMerchantsBeans.get(i).setMemberBean(memberBean);
				
				shoppingCarMerchantsBeansTemp.add(shoppingCarMerchantsBeans.get(i));
			}

		}

		return shoppingCarMerchantsBeansTemp;
	}

	/**
	 * 获得自己的购物车 商家列表(B2B2C)
	 * 
	 * @param shoppingCarBean
	 * @return
	 */
	public List<ShoppingCarBean> getShoppingCarsByMerchants(ShoppingCarBean shoppingCarBean) {
		List<ShoppingCarBean> shoppingCarBeans = shoppingCarDao.getShoppingCarsByMerchants(shoppingCarBean);
		for (int i = 0; i < shoppingCarBeans.size(); i++) {
			GoodsBean goodsBean = goodsServiceI.getOneGoodsDetail(
					new GoodsBean().setGoods_id(Integer.valueOf(shoppingCarBeans.get(i).getGoods_id())));
			shoppingCarBeans.get(i).setGoodsBean(goodsBean);
		}
		return shoppingCarBeans;
	}

	/**
	 * 删除购物车
	 * 
	 * @param shoppingCarBean
	 * @return
	 */
	public int deleteShoppingCar(ShoppingCarBean shoppingCarBean) {
		return shoppingCarDao.deleteShoppingCar(shoppingCarBean);
	}

	public int deleteShoppingCars(String[] car_ids, MemberBean memberBen) throws Exception {
		for (int i = 0; i < car_ids.length; i++) {
			int num = shoppingCarDao.deleteShoppingCar(new ShoppingCarBean().setCar_id(Integer.valueOf(car_ids[i]))
					.setMember_id(memberBen.getMember_id() + ""));
			if (num <= 0) {
				throw new Exception("删除失败");
			}
		}
		return 1;
	}

	/**
	 * 修改购物车商品数量
	 * 
	 * @param shoppingCarBean
	 * @return
	 */
	public int updateShoppingCarNum(ShoppingCarBean shoppingCarBean) {
		return shoppingCarDao.updateShoppingCarNum(shoppingCarBean);
	}
	
	/**
	 * 修改购物车商品数量
	 * 
	 * @param shoppingCarBean
	 * @return
	 * @throws Exception 
	 */
	public int updateShoppingCarNums(List<ShoppingCarBean> shoppingCarBeans,MemberBean memberBean) throws Exception {
		if(shoppingCarBeans!=null){
			for (int i = 0; i < shoppingCarBeans.size(); i++) {
				ShoppingCarBean shoppingCarBean=shoppingCarBeans.get(i);
				int num=shoppingCarDao.updateShoppingCarNum(shoppingCarBean.setMember_id(memberBean.getMember_id()+""));
				if(num<=0){
					throw new Exception("修改失败");
				}
			}
		}
		return 1;
	}
}
