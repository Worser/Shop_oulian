package tst.project.dao.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsSupplyDataBean;
import tst.project.bean.member.MainBrandBean;

public interface GoodsSupplyDataDao {
	
	public int addSupplyDatas(GoodsBean goodsBean);
	
	/**
	 * 添加商品供应数据
	 * @param goodsSupplyDataBean
	 * @return
	 */
	public int addSupplyData(GoodsSupplyDataBean goodsSupplyDataBean);
	public int updateSupplyData(GoodsSupplyDataBean goodsSupplyDataBean);

	/**
	 * 查询商品的供应数据
	 * @param goodsSupplyDataBean
	 * @return
	 */
	public GoodsSupplyDataBean getGoodsSupplyDataByID(GoodsSupplyDataBean goodsSupplyDataBean);
	
	/**
	 * 查询商品的供应数据
	 * @param goodsSupplyDataBean
	 * @return
	 */
	public List<GoodsSupplyDataBean> getGoodsSupplyData(GoodsSupplyDataBean goodsSupplyDataBean);
	
	/**
	 * 删除商品供应数据
	 */
	public int deleteSupplyData(GoodsSupplyDataBean goodsSupplyDataBean);
	
	/**
	 * 查询该型号供应数据是否有重复
	 */
	public List<GoodsSupplyDataBean> isSupplyDataRepeat(GoodsSupplyDataBean goodsSupplyDataBean);
	
	/**
	 * 删除供应商所有供应数据
	 */
	public int deleteSupplyDataAll(GoodsSupplyDataBean goodsSupplyDataBean);
	
	/**
	 * 修改供应数据库存
	 */
	public int updateSupplyStock(GoodsSupplyDataBean goodsSupplyDataBean);
	
	public int updateSupplyStockV2(GoodsSupplyDataBean goodsSupplyDataBean);
	
}
