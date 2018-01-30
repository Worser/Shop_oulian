package tst.project.dao.controller;

import java.util.List;

import tst.project.bean.others.AdviceBean;
import tst.project.page.PageBean;

public interface AdviceDaoC {
	/**
	 * 查询意见反馈
	 * @param adviceBean
	 * @return
	 */
	public List<AdviceBean> getMemberAdvice(AdviceBean adviceBean,PageBean pageBean);
	
}
