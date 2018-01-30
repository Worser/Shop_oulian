package tst.project.service.interfaces;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.dao.interfaces.GoodsSupplyDataDao;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsSupplyDataService {
	
	@Resource
	private GoodsSupplyDataDao dao;
	
	/**
	 * 添加商品供应数据
	 * @param goodsSupplyDataBean
	 * @return
	 */
	@Transactional
	public int addSupplyDatas(GoodsBean goodsBean)
	{
		return dao.addSupplyDatas(goodsBean);
	}
	
	
	/**
	 * 添加商品供应数据
	 * @param goodsSupplyDataBean
	 * @return
	 */
	@Transactional
	public int addSupplyData(GoodsSupplyDataBean goodsSupplyDataBean)
	{
		return dao.addSupplyData(goodsSupplyDataBean);
	}
	
	public int updateSupplyData(GoodsSupplyDataBean goodsSupplyDataBean){
		return dao.updateSupplyData(goodsSupplyDataBean);
	}
	/**
	 * 查询商品的供应数据
	 * @param goodsSupplyDataBean
	 * @return
	 */
	public GoodsSupplyDataBean getGoodsSupplyDataByID(GoodsSupplyDataBean goodsSupplyDataBean)
	{
		return dao.getGoodsSupplyDataByID(goodsSupplyDataBean);
	}
	
	/**
	 * 查询商品的供应数据
	 * @param goodsSupplyDataBean
	 * @return
	 */
	public List<GoodsSupplyDataBean> getGoodsSupplyData(GoodsSupplyDataBean goodsSupplyDataBean)
	{
		return dao.getGoodsSupplyData(goodsSupplyDataBean);
	}
	
	/**
	 * 删除商品供应数据
	 */
	@Transactional
	public int deleteSupplyData(GoodsSupplyDataBean goodsSupplyDataBean)
	{
		return dao.deleteSupplyData(goodsSupplyDataBean);
	}
	
	/**
	 * 查询该型号供应数据是否有重复
	 */
	public List<GoodsSupplyDataBean> isSupplyDataRepeat(GoodsSupplyDataBean goodsSupplyDataBean)
	{
		return dao.isSupplyDataRepeat(goodsSupplyDataBean);
	}
	
	/**
	 * 删除供应商所有供应数据
	 */
	public int deleteSupplyDataAll(GoodsSupplyDataBean goodsSupplyDataBean)
	{
		return dao.deleteSupplyDataAll(goodsSupplyDataBean);
	}
	
	/**
	 * 修改供应数据库存
	 */
	public int updateSupplyStock(GoodsSupplyDataBean goodsSupplyDataBean)
	{
		return dao.updateSupplyStock(goodsSupplyDataBean);
	}
	
	/**
	 * 修改供应数据库存
	 */
	public int updateSupplyStockV2(GoodsSupplyDataBean goodsSupplyDataBean)
	{
		return dao.updateSupplyStockV2(goodsSupplyDataBean);
	}
}
