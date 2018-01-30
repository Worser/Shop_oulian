package tst.project.service.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.collection.CollectionBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsFindBuyBean;
import tst.project.bean.information.InformationBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsBean;
import tst.project.dao.controller.GoodsDao;
import tst.project.dao.interfaces.CollectionDao;
import tst.project.dao.interfaces.GoodsDaoI;
import tst.project.dao.interfaces.GoodsFindBuyDao;
import tst.project.dao.interfaces.MemberDao;
import tst.project.page.PageBean;
import tst.project.service.controller.GoodsService;
import tst.project.service.controller.MerchantsService;


@Service
@Transactional(rollbackFor = { Exception.class })
public class CollectionService {
	@Resource
	CollectionDao collectionDao;

	@Resource
	MerchantsService merchantsService;

	@Resource
	GoodsServiceI goodsServiceI;
	
	@Resource
	InformationService informationService;
	
	@Resource
	MemberService memberService;
	
	@Resource
	MemberDao memberDao;
	
	@Resource
	GoodsDaoI goodsDaoI; 
	
	@Resource
	GoodsFindBuyDao findBuyDao;
	
	/**
	 * 添加收藏
	 * 
	 * @param collectionBean
	 * @return
	 */
	public int insertCollection(CollectionBean collectionBean) {
		return collectionDao.insertCollection(collectionBean);
	}

	/**
	 * 通过各种条件 搜索 判断是否收藏过
	 * 
	 * @param collectionBean
	 * @return
	 */
	public CollectionBean getCollectionBySearch(CollectionBean collectionBean) {
		return collectionDao.getCollectionBySearch(collectionBean);
	}

	/**
	 * 改变收藏状态 删除或者恢复
	 * 
	 * @return
	 */
	public int updateCollectionState(CollectionBean collectionBean) {
		return collectionDao.updateCollectionState(collectionBean);
	}

	/**
	 * 批量取消收藏
	 * @param collect_ids
	 * @return
	 * @throws Exception
	 */
	public int cancelAllCollection(String member_id,String[] collect_ids) throws Exception{
		for (int i = 0; i < collect_ids.length; i++) {
			int num= collectionDao.updateCollectionState(new CollectionBean().
					setMember_id(member_id).
					setCollection_id(Integer.valueOf(collect_ids[i]))
					.setIs_delete("1"));
			if(num<=0){
				throw new Exception("取消失败");
			}
		}
		return 1;
	}
	/**
	 * 用户获得收藏列表
	 * 
	 * @param collectionBean
	 * @return
	 */
	public List<CollectionBean> getCollection(CollectionBean collectionBean,
			PageBean pageBean) {
		List<CollectionBean> collectionBeans = new ArrayList<CollectionBean>();
		if(collectionBean.getCollection_type()!=null &&
				!collectionBean.getCollection_type().equals(""))
		{
			collectionBeans = collectionDao
			.getCollection(collectionBean,pageBean);
		}else
		{
			collectionBeans = collectionDao
			.getCollectionNoType(collectionBean,pageBean);
		}
		if(collectionBeans!=null){
			for (int i = 0; i < collectionBeans.size(); i++) {
				CollectionBean collectionBean2 = collectionBeans.get(i);
				
				if(collectionBean2.getCollection_type().equals("goods_supply")){
					GoodsBean goodsBean = goodsServiceI.getMerchantsGoodsData(new GoodsBean()
					          .setGoods_id(Integer.valueOf(collectionBean2.getRelation_id())));
					if(goodsBean!=null)
					{
						//查询平台提供型号的资料
						GoodsBean goodsBean1 = goodsServiceI.getGoodsData(new GoodsBean()
						      .setGoods_name(goodsBean.getGoods_name()));
						if(goodsBean1!=null)
						{
						    goodsBean.setGoods_img(goodsBean1.getGoods_img());	
						    goodsBean.setMin_pack_num(goodsBean1.getMin_pack_num());
						    goodsBean.setMin_buy_num(goodsBean1.getMin_buy_num());
						}
						
						//查询供应商名称
						MemberBean memberBean = memberService.getMemberByMerchants(new MemberBean()
	                       .setMerchants_id(goodsBean.getMerchants_id()));
						
						goodsBean.setMemberBean(memberBean);
					}
					collectionBean2.setGoodsBean(goodsBean);
				}else if(collectionBean2.getCollection_type().equals("goods_find_buy")){
					
				    GoodsFindBuyBean findBuyBean = findBuyDao.getfindBuyWhetherOverdue(new GoodsFindBuyBean()
				          .setFind_id(Integer.valueOf(collectionBean2.getRelation_id())));
				    collectionBean2.setFindBuyBean(findBuyBean);
				    
				    //查询平台提供型号的资料
					GoodsBean goodsBean1 = goodsDaoI.getGoodsData(new GoodsBean()
					      .setGoods_name(findBuyBean.getGoods_name()));
					
					findBuyBean.setBrand_name(goodsBean1.getBrand_name());
					findBuyBean.setGoods_img(goodsBean1.getGoods_img());
					
				    //查询供应商名称
					MemberBean memberBean = memberDao.getMemberByID(new MemberBean()
					      .setMember_id(Integer.valueOf(collectionBean2.getMember_id())));
					collectionBean2.setMemberBean(memberBean);
					
				}else if(collectionBean2.getCollection_type().equals("merchants")){
					MerchantsBean merchantsBean=merchantsService.getOneMerchantsDetail(new MerchantsBean()
					.setMerchants_id(Integer.valueOf(collectionBean2
							.getRelation_id())));
					collectionBean2.setMerchantsBean(merchantsBean);	
				}else if(collectionBean2.getCollection_type().equals("goods")){
					GoodsBean goodsBean=goodsServiceI.getOneGoodsDetail(new GoodsBean().
							setGoods_id(Integer.valueOf(collectionBean2.getRelation_id())));
					collectionBean2.setGoodsBean(goodsBean);
				}
				if(collectionBean2.getCollection_type().equals("information")){
					InformationBean informationBean=informationService
							.getInformationDetail(new InformationBean()
									.setInformation_id(Integer.valueOf(collectionBean2.getRelation_id())));
					collectionBean2.setInformationBean(informationBean);
				}
			}	
		}
		return collectionBeans;
	}
	
}
