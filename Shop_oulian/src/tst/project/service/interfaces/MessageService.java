package tst.project.service.interfaces;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.member.MessageBean;
import tst.project.dao.interfaces.MessageDao;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class MessageService {
	@Resource
	MessageDao messageDao;
	
	/**
	 * 添加系统消息
	 */
	public int addMessage(MessageBean messageBean)
	{
		return messageDao.addMessage(messageBean);
	}
	
	/**
	 * 查询系统消息
	 */
	public List<MessageBean> getMessageBean(MessageBean messageBean,PageBean pageBean)
	{
		return messageDao.getMessageBean(messageBean, pageBean);
	}
	
	/**
	 * 查询有多少未读信息
	 */
	public int getNotReadMessageCount(MessageBean messageBean)
	{
		return messageDao.getNotReadMessageCount(messageBean);
	}
	
	/**
	 * 设置为已读
	 */
	public int messageIsRead(MessageBean messageBean)
	{
		return messageDao.messageIsRead(messageBean);
	}
	
	/**
	 * 查询单个消息详情 
	 */
	public MessageBean getOneMessageDateil(MessageBean messageBean)
	{
		return messageDao.getOneMessageDateil(messageBean);
	}
	
	/**
	 * 全部设为已读
	 */
	public int allMessageIsRead(MessageBean messageBean)
	{
		return messageDao.allMessageIsRead(messageBean);
	}
	
	/**
	 * 批量删除
	 */
	public int batchDelete(MessageBean messageBean)
	{
		return messageDao.batchDelete(messageBean);
	}
	
	/**
	 * 全部删除
	 */
	public int allDelete(MessageBean messageBean)
	{
		return messageDao.allDelete(messageBean);
	}
	
	/**
	 * 单个删除
	 */
	public int oneDelete(MessageBean messageBean)
	{
		return messageDao.oneDelete(messageBean);
	}
}
