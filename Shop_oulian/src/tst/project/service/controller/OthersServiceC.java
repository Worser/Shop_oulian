package tst.project.service.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.HostBean;
import tst.project.bean.order.PingSettingBean;
import tst.project.bean.others.AdviceBean;
import tst.project.bean.others.AdviceImgBean;
import tst.project.bean.others.HtmlBean;
import tst.project.bean.others.HtmlStyleBean;
import tst.project.bean.others.PercentBean;
import tst.project.bean.others.QrcodeBean;
import tst.project.bean.wx.WXSetingBean;
import tst.project.dao.controller.OthersDaoC;
import tst.project.page.PageBean;


@Service
@Transactional(rollbackFor = Exception.class)
public class OthersServiceC {
	@Resource
	OthersDaoC othersDaoC;
	
	/**
	 * 二维码内容配置
	 * @param htmlStyleBean
	 * @return
	 */
	public QrcodeBean getQrcodeSetting(QrcodeBean qrcodeBean){
		return othersDaoC.getQrcodeSetting(qrcodeBean);
	}
	
	/**
	 * ping++设置
	 * @param pingSettingBean
	 * @return
	 */
	public PingSettingBean getPingSetting(PingSettingBean pingSettingBean){
		return othersDaoC.getPingSetting(pingSettingBean);
	}
	
	/**
	 * html基础样式
	 * @param htmlStyleBean
	 * @return
	 */
	public HtmlStyleBean getHtmlStyle(HtmlStyleBean htmlStyleBean){
		return othersDaoC.getHtmlStyle(htmlStyleBean);
	}
	/**
	 * 微信设置
	 * @param wxSetingBean
	 * @return
	 */
	public WXSetingBean getWXSeting(WXSetingBean wxSetingBean){
		return othersDaoC.getWXSeting(wxSetingBean);
	}
	
	/**
	 * 域名获得
	 * @param hostBean
	 * @return
	 */
	public HostBean getHost(HostBean hostBean){
		return othersDaoC.getHost(hostBean);
	}
	
	/**
	 * 修改平台基本信息
	 * @param hostBean
	 * @return
	 */
	public int updateHost(HostBean hostBean){
		return othersDaoC.updateHost(hostBean);
	}
	/**
	 * 删除意见
	 * @return
	 */
	public int deleteAdvice(AdviceBean adviceBean){
		return othersDaoC.deleteAdvice(adviceBean);
	}
	/**
	 * 意见的图片列表
	 * @param asAdviceImgBean
	 * @return
	 */
	public List<AdviceImgBean> getAdviceImgs(AdviceImgBean asAdviceImgBean){
		return othersDaoC.getAdviceImgs(asAdviceImgBean);
	}
	/**
	 * 反馈详情
	 * @param adviceBean
	 * @return
	 */
	public AdviceBean getAdviceDetail(AdviceBean adviceBean){
		AdviceBean adviceBean2=othersDaoC.getAdviceDetail(adviceBean);
		if(adviceBean2!=null){
			List<AdviceImgBean> adviceImgBeans=getAdviceImgs(new AdviceImgBean().setAdvice_id(adviceBean2.getAdvice_id()+""));
			adviceBean2.setAdviceImgBeans(adviceImgBeans);
		}
		return adviceBean2;
	}
	/**
	 * 意见反馈列表
	 * @return
	 */
	public List<AdviceBean> getAdvices(AdviceBean adviceBean,PageBean pageBean){
		return othersDaoC.getAdvices(adviceBean,pageBean);
	}
	/**
	 * 
	 * @param percentBean
	 * @return
	 */
	public List<PercentBean> getPercents(PercentBean percentBean){
		return othersDaoC.getPercents(percentBean);
	}
	
	/**
	 * 
	 * @param percentBean
	 * @return
	 */
	public PercentBean getPercent(PercentBean percentBean){
		return othersDaoC.getPercent(percentBean);
	}
	
	public int updatePercents(List<PercentBean> percentBeans) throws Exception{
		int num=0;
		for (int i = 0; i < percentBeans.size(); i++) {
			num=updatePercentByType(percentBeans.get(i));
			if(num<=0){
				throw new Exception("更新失败");
			}
		}
		return num;
	}
	
	public int updatePercent(PercentBean percentBean){
		return othersDaoC.updatePercent(percentBean);
	}
	
	public int updatePercentByType(PercentBean percentBean){
		return othersDaoC.updatePercentByType(percentBean);
	}
	
	/**
	 * 添加帮助中心
	 * @param htmlBean
	 * @return
	 */
	public int insertHelpDetail(HtmlBean htmlBean){
		return othersDaoC.insertHelpDetail(htmlBean);
	}
	
	/**
	 * 修改帮助中心
	 * @param htmlBean
	 * @return
	 */
	public int updateHelpDetail(HtmlBean htmlBean){
		return othersDaoC.updateHelpDetail(htmlBean);
	}
	
	/**
	 * 删除帮助中心
	 * @param htmlBean
	 * @return
	 */
	public int deleteHelpDetail(HtmlBean htmlBean){
		return othersDaoC.deleteHelpDetail(htmlBean);
	}
	/**
	 * 帮助中心分类
	 * @param htmlBean
	 * @return
	 */
	public List<HtmlBean> getHelpClasss(HtmlBean htmlBean){
		return othersDaoC.getHelpClasss(htmlBean);
	}
}
