package tst.project.service.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsParameterBean;
import tst.project.bean.goods.ShoppingCarBean;
import tst.project.bean.goods.ShoppingCarMerchantsBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.dao.controller.ShoppingCarDaoC;
import tst.project.dao.interfaces.ShoppingCarDao;
import tst.project.page.PageBean;
@Service
@Transactional(rollbackFor = { SQLException.class })
public class ShoppingCarServiceC {
	@Resource
	ShoppingCarDaoC shoppingCarDaoC;

	@Resource
	GoodsService goodsService;

	@Resource
	MerchantsService merchantsService;
	

	/**
	 * 查看该商品是否已经加过购物车
	 * 
	 * @return
	 */
	public ShoppingCarBean getShoppingCarByGoodsIdAndMemberId(
			ShoppingCarBean shoppingCarBean) {
		ShoppingCarBean shoppingCarBean2=shoppingCarDaoC
				.getShoppingCarByGoodsIdAndMemberId(shoppingCarBean);
	
		return shoppingCarBean2;
	}
	
	
	/**
	 * 获得自己的购物车列表(B2B)
	 * 
	 * @param shoppingCarBean
	 * @return
	 */
	public List<ShoppingCarBean> getShoppingCarB2C(ShoppingCarBean shoppingCarBean) {
		List<ShoppingCarBean> shoppingCarBeans=shoppingCarDaoC.getShoppingCarB2C(shoppingCarBean);
		for (int i = 0; i < shoppingCarBeans.size(); i++) {
			GoodsBean goodsBean=goodsService.getOneGoodsDetail(new GoodsBean().
					setGoods_id(Integer.valueOf(shoppingCarBeans.get(i).getGoods_id())));
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
		return shoppingCarDaoC.deleteShoppingCar(shoppingCarBean);
	}

	public int deleteShoppingCars(String[] car_ids,MemberBean memberBen) throws Exception{
		for (int i = 0; i < car_ids.length; i++) {
			int num=shoppingCarDaoC.deleteShoppingCar(new ShoppingCarBean().
							setCar_id(Integer.valueOf(car_ids[i])).
							setMember_id(memberBen.getMember_id()+""));	
			if(num<=0){
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
		return shoppingCarDaoC.updateShoppingCarNum(shoppingCarBean);
	}
}
