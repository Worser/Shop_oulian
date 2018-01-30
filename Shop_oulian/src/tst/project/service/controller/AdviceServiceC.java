package tst.project.service.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.others.AdviceBean;
import tst.project.dao.controller.AdviceDaoC;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdviceServiceC {
	@Resource
	AdviceDaoC adviceDaoC;
	
	/**
	 * 查询意见反馈
	 * @param adviceBean
	 * @return
	 */
	public List<AdviceBean> getMemberAdvice(AdviceBean adviceBean,PageBean pageBean)
	{
		return adviceDaoC.getMemberAdvice(adviceBean,pageBean);
	}
	
}
