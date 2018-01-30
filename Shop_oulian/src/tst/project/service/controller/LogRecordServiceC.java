package tst.project.service.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.LogRecordBean;
import tst.project.dao.controller.LogRecordDaoC;
import tst.project.page.PageBean;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogRecordServiceC {
	
	@Resource
	private LogRecordDaoC dao;
	
	/**
	 * 获得记录
	 * @param logRecordBean
	 * @return
	 */
	public List<LogRecordBean> getLogRecordBeans(LogRecordBean logRecordBean,PageBean pageBean)
	{
		return dao.getLogRecordBeans(logRecordBean,pageBean);
	}
	
	/**
	 * 导出记录
	 * @param logRecordBean
	 * @return
	 */
	public List<Object> ExportLogRecordBeans(LogRecordBean logRecordBean)
	{
		return dao.ExportLogRecordBeans(logRecordBean);
	}
	
	/**
	 * 批量删除
	 */
	@Transactional
	public int batchDelete(LogRecordBean logRecordBean)
	{
		return dao.batchDelete(logRecordBean);
	}
	
	/**
	 * 全部删除
	 */
	@Transactional
	public int allDelete(LogRecordBean logRecordBean)
	{
		return dao.allDelete(logRecordBean);
	}
}
