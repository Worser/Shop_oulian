package tst.project.dao.controller;

import java.util.List;

import tst.project.bean.LogRecordBean;
import tst.project.page.PageBean;

public interface LogRecordDaoC {
	/**
	 * 获得记录
	 * @param logRecordBean
	 * @return
	 */
	public List<LogRecordBean> getLogRecordBeans(LogRecordBean logRecordBean,PageBean pageBean);
	
	/**
	 * 导出记录
	 * @param logRecordBean
	 * @return
	 */
	public List<Object> ExportLogRecordBeans(LogRecordBean logRecordBean);
	
	/**
	 * 批量删除
	 */
	public int batchDelete(LogRecordBean logRecordBean);
	
	/**
	 * 全部删除
	 */
	public int allDelete(LogRecordBean logRecordBean);
}
