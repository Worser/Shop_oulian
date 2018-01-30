package tst.project.service.interfaces;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.bbs.BlockBean;
import tst.project.bean.member.MemberBean;
import tst.project.dao.interfaces.BlockDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class BlockService {
	@Resource
	BlockDao blockDao;
	
	/**
	 * 获得板块内容
	 */
	public List<BlockBean> getContent(BlockBean blockBean){
		return blockDao.getContent(blockBean);
	}
	/**
	 * 获得版主信息
	 */
	public List<MemberBean> getMember(MemberBean memberBean){
		return blockDao.getMember(memberBean);
	}
	public List<BlockBean> getBlockName(BlockBean blockBean)
	 {
	   return blockDao.getBlockName(blockBean);
	 }
}
