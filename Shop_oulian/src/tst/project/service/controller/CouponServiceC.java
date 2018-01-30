package tst.project.service.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.CouponBean;
import tst.project.dao.controller.CouponDaoC;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class CouponServiceC {
	@Resource
	CouponDaoC couponDaoC;
	
	/**
	 * 添加优惠卷
	 * @param couponBean
	 * @return
	 */
	public int insertCoupon(CouponBean couponBean){
		return couponDaoC.insertCoupon(couponBean);
	}
	
	/**
	 * 修改优惠卷
	 * @param couponBean
	 * @return
	 */
	public int updateCoupon(CouponBean couponBean){
		return couponDaoC.updateCoupon(couponBean);
	}
	
	
	/**
	 * 删除优惠卷
	 * @param couponBean
	 * @return
	 */
	public int deleteCoupon(CouponBean couponBean){
		return couponDaoC.deleteCoupon(couponBean);
	}
	
	/**
	 * 更新优惠卷状态
	 * @param couponBean
	 * @return
	 */
	public int updateCouponState(CouponBean couponBean){
		return couponDaoC.updateCouponState(couponBean);
	}
	
	/**
	 * 获得优惠卷列表
	 * @param couponBean
	 * @return
	 */
	public List<CouponBean> getCoupons(CouponBean couponBean,PageBean pageBean){
		return couponDaoC.getCoupons(couponBean, pageBean);
	}
}
