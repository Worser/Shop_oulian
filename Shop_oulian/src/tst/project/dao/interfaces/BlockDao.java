package tst.project.dao.interfaces;

import java.util.List;

import tst.project.bean.bbs.BlockBean;
import tst.project.bean.member.MemberBean;

public interface BlockDao {
	/**
	 * 获得板块内容
	 */
	public List<BlockBean> getContent(BlockBean blockBean);
	/**
	 * 获得版主信息
	 */
	public List<MemberBean> getMember(MemberBean memberBean);
	
	public abstract List<BlockBean> getBlockName(BlockBean paramBlockBean);
}
