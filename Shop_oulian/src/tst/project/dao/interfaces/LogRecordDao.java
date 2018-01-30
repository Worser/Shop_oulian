package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.LogRecordBean;
import tst.project.bean.goods.GoodsBean;

public interface LogRecordDao {
	/**
	 * 修改记录
	 * @param logRecordBean
	 * @return
	 */
	public int updateLogRecords(GoodsBean goodsBean);
	/**
	 * 添加记录
	 * @param logRecordBean
	 * @return
	 */
	public int addLogRecord(LogRecordBean logRecordBean);
	
	/**
	 * 添加记录
	 * @param logRecordBean
	 * @return
	 */
	public int addLogRecords(List<GoodsBean> goodsBeans);
	
	/**
	 * 删除平台有的型号
	 */
	public int deleteHaveGoodsName();
}
