package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.member.MessageBean;
import tst.project.page.PageBean;


public interface MessageDao {
	
	/**
	 * 添加系统消息
	 */
	public int addMessage(MessageBean messageBean);
	
	/**
	 * 查询系统消息
	 */
	public List<MessageBean> getMessageBean(MessageBean messageBean,PageBean pageBean);
	
	/**
	 * 查询有多少未读信息
	 */
	public int getNotReadMessageCount(MessageBean messageBean);
	
	/**
	 * 设置为已读
	 */
	public int messageIsRead(MessageBean messageBean);
	/**
	 * 查询单个消息详情 
	 */
	public MessageBean getOneMessageDateil(MessageBean messageBean);
	/**
	 * 全部设为已读
	 */
	public int allMessageIsRead(MessageBean messageBean);
	/**
	 * 批量删除
	 */
	public int batchDelete(MessageBean messageBean);
	
	/**
	 * 全部删除
	 */
	public int allDelete(MessageBean messageBean);
	
	/**
	 * 单个删除
	 */
	public int oneDelete(MessageBean messageBean);
}
