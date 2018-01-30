package tst.project.service.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsClassBean;
import tst.project.bean.goods.GoodsParameterBean;
import tst.project.bean.goods.GoodsRelationClassBean;
import tst.project.bean.goods.TagBean;
import tst.project.dao.controller.GoodsDao2;
import tst.project.page.PageBean;

/**
 * 商品管理
 * 版本2.0
 * 改善 一个商品 可在多个分类下  规格对应库存的问题
 * @author shenjiabo
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsService2 {
	@Resource
	GoodsDao2 goodsDao2;
	
	/**
	 * 搜索商品列表
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> searchGoods(GoodsBean goodsBean,PageBean pageBean){
		return goodsDao2.searchGoods(goodsBean,pageBean);
	}
	
	/**
	 * 添加商品tag
	 * @return
	 */
	public int insertGoodsTag(TagBean tagBean){
		return goodsDao2.insertGoodsTag(tagBean);
	}
	
	/**
	 * 修改商品tag
	 * @return
	 */
	public int updateGoodsTag(TagBean tagBean){
		return goodsDao2.updateGoodsTag(tagBean);
	}
	
	/**
	 * 删除商品tag
	 * @return
	 */
	public int deleteGoodsTag(TagBean tagBean){
		return goodsDao2.deleteGoodsTag(tagBean);
	}
	
	/**
	 * 获得商品Tag
	 * @param tagBean
	 * @param pageBean
	 * @return
	 */
	public List<TagBean> getGoodsTags(TagBean tagBean,PageBean pageBean){
		return goodsDao2.getGoodsTags(tagBean, pageBean);
	}
	
	
	/**
	 * 修改商品
	 * @return
	 */
	public int updateGoodsDetail(GoodsBean goodsBean){
		return goodsDao2.updateGoodsDetail(goodsBean);
	}
	/**
	 * 保存商品
	 * @param goodsBean
	 * @return
	 * @throws Exception 
	 */
	public int insertGoodsDetail(GoodsBean goodsBean,List<GoodsParameterBean> goodsParameterBeans) throws Exception{
		String  goods_parameters="";
		if(goodsParameterBeans!=null){
			String goods_no="";
			if(goodsBean.getGoods_no()==null||"".equals(goodsBean.getGoods_no())){
				goods_no=UUID.randomUUID().toString();
				goodsBean.setGoods_no(goods_no);
			}else{
				goods_no=goodsBean.getGoods_no();
			}
			
			for (int i = 0; i < goodsParameterBeans.size(); i++) {
				GoodsParameterBean goodsParameterBean=goodsParameterBeans.get(i);
				if(goodsParameterBean.getParameter_id()==0){
					goodsParameterBean.setGoods_no(goods_no);
					goodsParameterBean.setParent_id("-1");
					goodsParameterBean.setParameter_type("1");
					int num=goodsDao2.insertGoodsParameter(goodsParameterBean);
					if(num<=0){
						throw new Exception("添加失败");
					}
				}
				
				List<GoodsParameterBean> goodsParameterBeans2=goodsParameterBean.getGoodsParameterBeans();
				if(goodsParameterBeans2!=null){
					for (int j = 0; j < goodsParameterBeans2.size(); j++) {
						GoodsParameterBean goodsParameterBean2=goodsParameterBeans2.get(j);
						if(goodsParameterBean2.getParameter_id()==0){
							goodsParameterBean2.setGoods_no(goods_no);
							goodsParameterBean2.setParent_id(goodsParameterBean.getParameter_id()+"");
							goodsParameterBean2.setParameter_type("2");
							int h=goodsDao2.insertGoodsParameter(goodsParameterBean2);
							if(h<=0){
								throw new Exception("添加失败1");
							}		
						}
						if("1".equals(goodsParameterBean2.getIs_select())){
							goods_parameters+=goodsParameterBean2.getParameter_id()+",";
						}	
					}
				}
			}
		}
		if(goods_parameters.length()>0){
			goods_parameters=goods_parameters.substring(0, goods_parameters.length()-1);
		}
		return goodsDao2.insertGoodsDetail(goodsBean.setGoods_parameters(goods_parameters));
	}
	
	/**
	 * 获得商品 根据no
	 * @param goodsBean
	 * @return
	 */
	public List<GoodsBean> getGoodsByNo(GoodsBean goodsBean){
		return goodsDao2.getGoodsByNo(goodsBean);
	}
	
	/**
	 * 获得参数 根据no
	 * @param goodsParameterBean
	 * @return
	 */
	public List<GoodsParameterBean> getGoodsParametersByNo(GoodsParameterBean goodsParameterBean){
		List<GoodsParameterBean> goodsParameterBeans=goodsDao2
				.getGoodsParametersByNo(goodsParameterBean.setParent_id("-1"));
		if(goodsParameterBeans!=null){
			for (int i = 0; i < goodsParameterBeans.size(); i++) {
				GoodsParameterBean goodsParameterBean2=goodsParameterBeans.get(i);
				List<GoodsParameterBean> goodsParameterBeans2=goodsDao2
						.getGoodsParametersByNo(goodsParameterBean.setParent_id(goodsParameterBean2.getParameter_id()+""));
				goodsParameterBean2.setGoodsParameterBeans(goodsParameterBeans2);
			}
		}	
		return goodsParameterBeans;
	}
	
	/**
	 * 添加商品参数
	 * @param goodsParameterBean
	 * @return
	 * @throws Exception 
	 */
	public String insertGoodsParameters(List<GoodsParameterBean> goodsParameterBeans,String goods_no) throws Exception{
		if(goods_no==null||"".equals(goods_no)){
			goods_no=UUID.randomUUID().toString();
		}
		if(goodsParameterBeans!=null){
			for (int i = 0; i < goodsParameterBeans.size(); i++) {
				GoodsParameterBean goodsParameterBean=goodsParameterBeans.get(i);
				
				if(goodsParameterBean.getParameter_id()==0){
					goodsParameterBean.setGoods_no(goods_no);
					goodsParameterBean.setParent_id("-1");
					goodsParameterBean.setParameter_type("1");
					int num=goodsDao2.insertGoodsParameter(goodsParameterBean);
					if(num<=0){
						throw new Exception("添加失败");
					}
				}
				
				List<GoodsParameterBean> goodsParameterBeans2=goodsParameterBean.getGoodsParameterBeans();
				if(goodsParameterBeans2!=null){
					for (int j = 0; j < goodsParameterBeans2.size(); j++) {
						GoodsParameterBean goodsParameterBean2=goodsParameterBeans2.get(j);
						if(goodsParameterBean2.getParameter_id()==0){
							goodsParameterBean2.setGoods_no(goods_no);
							goodsParameterBean2.setParent_id(goodsParameterBean.getParameter_id()+"");
							goodsParameterBean2.setParameter_type("2");
							int h=goodsDao2.insertGoodsParameter(goodsParameterBean2);
							if(h<=0){
								throw new Exception("添加失败1");
							}		
						}
					}
				}
			}
		}
		return goods_no;
	}
	
	/**
	 * 通过分类id和商品id 获得商品信息
	 * @param goodsRelationClassBean
	 * @return
	 */
	public GoodsRelationClassBean getClassGoodsByClassAndGodosId(GoodsRelationClassBean goodsRelationClassBean){
		return goodsDao2.getClassGoodsByClassAndGodosId(goodsRelationClassBean);
	}
	
	/**
	 * 删除分类下商品
	 * @param goodsRelationClassBean
	 * @return
	 */
	public int deleteClassGoods(GoodsRelationClassBean goodsRelationClassBean){
		return goodsDao2.deleteClassGoods(goodsRelationClassBean);
	}
	
	/**
	 * 添加分类下商品
	 * @param goodsClassBean
	 * @return
	 */
	public int insertClassGoods(GoodsRelationClassBean goodsRelationClassBean){
		String uuid=UUID.randomUUID().toString();
		return goodsDao2.insertClassGoods(goodsRelationClassBean.setUuid(uuid));
	}
	
	/**
	 * 分类下商品列表
	 * @return
	 */
	public  List<GoodsBean> getClassGoods(GoodsBean goodsBean,PageBean pageBean){
		return goodsDao2.getClassGoods(goodsBean, pageBean);
	}
	/**
	 * 添加商品分类
	 * @return
	 */
	public int insertGoodsClass(GoodsClassBean goodsClassBean){
		String class_uuid=UUID.randomUUID().toString();
		return goodsDao2.insertGoodsClass(goodsClassBean.setClass_uuid(class_uuid));
	}
	
	/**
	 * 修改商品分类
	 * @return
	 */
	public int updateGoodsClass(GoodsClassBean goodsClassBean){
		return goodsDao2.updateGoodsClass(goodsClassBean);
	}
	
	/**
	 * 删除商品分类
	 * @return
	 */
	public int deleteGoodsClass(GoodsClassBean goodsClassBean){
		return goodsDao2.deleteGoodsClass(goodsClassBean);
	}
	

	/**
	 * 获得商品分类
	 * @return
	 */
	public List<GoodsClassBean> getGoodsClasss(GoodsClassBean goodsClassBean,PageBean pageBean){
		return goodsDao2.getGoodsClasss(goodsClassBean, pageBean);
	}
	
	/**
	 * 获得商品分类 不分页
	 * @return
	 */
	public List<GoodsClassBean> getGoodsClasssNoPage(GoodsClassBean goodsClassBean){
		return goodsDao2.getGoodsClasssNoPage(goodsClassBean);
	}
}
