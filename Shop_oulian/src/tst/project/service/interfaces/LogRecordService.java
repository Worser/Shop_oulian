package tst.project.service.interfaces;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.LogRecordBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.dao.interfaces.LogRecordDao;


/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogRecordService {
	
	@Resource
	private LogRecordDao dao;
	
	/**
	 * 添加记录
	 * @param logRecordBean
	 * @return
	 */
	public int addLogRecord(LogRecordBean logRecordBean)
	{
		return dao.addLogRecord(logRecordBean);
	}

	/**
	 * 添加记录
	 * @param logRecordBean
	 * @return
	 */
	public int addLogRecords(List<GoodsBean> goodsBeans)
	{
		return dao.addLogRecords(goodsBeans);
	}
	
	/**
	 * 添加记录
	 * @param logRecordBean
	 * @return
	 */
	public int updateLogRecords(GoodsBean goodsBean)
	{
		return dao.updateLogRecords(goodsBean);
	}
	
	/**
	 * 删除平台有的型号
	 */
	public int deleteHaveGoodsName()
	{
		return dao.deleteHaveGoodsName();
	}
}
