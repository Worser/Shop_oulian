package tst.project.service.interfaces;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsClassBean;
import tst.project.bean.home.ActivityBean;
import tst.project.bean.home.HomeGoodsBean;
import tst.project.bean.home.LabelBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.member.ShareBean;
import tst.project.dao.interfaces.HBRDao;
import tst.project.page.PageBean;


@Service
@Transactional(rollbackFor = Exception.class)
public class HBRService {
	@Resource
	HBRDao hbrDao;
	
	@Resource
	MemberService memberService;
	
	/**
	 * 用户分享过的某一个商品
	 * @param shareBean
	 * @return
	 * @throws Exception 
	 */
	public ShareBean getMemberShareByMemberIDAndGoodsID(ShareBean shareBean) throws Exception{	
		return hbrDao.getMemberShareByMemberIDAndGoodsID(shareBean);
	}
	/**
	 * 用户分享商品得积分
	 * @param shareBean
	 * @return
	 * @throws Exception 
	 * @throw 
	 */
	public int insertMemberShare(ShareBean shareBean) throws Exception {
		MemberBean memberBean=memberService.getMemberByID(new MemberBean()
				.setMember_id(shareBean.getMember_id()));
		if(memberBean==null){
			throw new Exception("该用户不存在");
		}
		
		int num=memberService.updateMemberDetail(new MemberBean()
				.setMember_id(shareBean.getMember_id())
				.setIntegral((Float.valueOf(memberBean.getIntegral())+shareBean.getShare_integral())+""));
		if(num<=0){
			throw new Exception("更新用户积分失败");
		}
		
		num=hbrDao.insertMemberShare(shareBean);
		if(num<=0){
			throw new Exception("分享入库失败");	
		}
		return num;
	}
	/**
	 * 可分享的商品列表
	 * @param goodsBean
	 * @param pageBean
	 * @return
	 */
	public List<GoodsBean> getShareGoodss(GoodsBean goodsBean,PageBean pageBean){
		return hbrDao.getShareGoodss(goodsBean, pageBean);
	}
	
	/**
	 * 首页商品（何柏瑞）
	 * @return
	 */
	public List<HomeGoodsBean> getHomeGoods(HomeGoodsBean homeGoodsBean){
		List<HomeGoodsBean> goodsBeans=hbrDao.getHomeGoods(homeGoodsBean.setParent_id("-1"));
		if(goodsBeans!=null){
			for (int i = 0; i < goodsBeans.size(); i++) {
				List<HomeGoodsBean> goodsBeans2=hbrDao.
						getHomeGoods(homeGoodsBean.setParent_id(goodsBeans.get(i).getHome_goods_id()+""));
				goodsBeans.get(i).setHomeGoodsBeans(goodsBeans2);
			}
		}
		return goodsBeans;
	}
	/**
	 * 首页分类
	 * @return
	 */
	public List<GoodsBean> getHomeClasss(GoodsBean goodsBean){
		return hbrDao.getHomeClasss(goodsBean);
	}
	
	/**
	 * 首页分类
	 * @return
	 */
	public List<GoodsClassBean> getHomeClasss2(GoodsClassBean goodsClassBean){
		return hbrDao.getHomeClasss2(goodsClassBean);
	}
	/**
	 * 首页标签（何柏瑞）
	 * @param labelBean
	 * @return
	 */
	public List<LabelBean> getHomeLabels(LabelBean labelBean){
		return hbrDao.getHomeLabels(labelBean);
	}
	
	/**
	 * 首页活动（）
	 * @param activityBean
	 * @return
	 */
	public List<ActivityBean> getHomeActivitys(ActivityBean activityBean){
		List<ActivityBean> activityBeans=hbrDao.getHomeActivitys(activityBean.setParent_id("-1"));
		if(activityBeans!=null){
			for (int i = 0; i < activityBeans.size(); i++) {
				List<ActivityBean> activityBeans2=hbrDao.
						getHomeActivitys(new ActivityBean().setParent_id(activityBeans.get(i).getActivity_id()+""));
				activityBeans.get(i).setActivityBeans(activityBeans2);
			}		
		}
		return activityBeans;
	}
}
