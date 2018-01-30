package tst.project.webservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tst.project.bean.ExcelBean;
import tst.project.bean.entrust.EntrustBean;
import tst.project.bean.entrust.MemberEntrustBean;
import tst.project.bean.member.MemberBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.page.PageBean;
import tst.project.service.controller.EntrustServiceC;
import tst.project.service.controller.MemberEntrustServiceC;
import tst.project.service.controller.SystemService;
import tst.project.utils.ExcelUtils;
import tst.project.utils.TimeUtils;

import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/memberEntrustController.api")
public class MemberEntrustController extends BaseController{
	
	@Resource
	MemberEntrustServiceC meService;
	
	@Resource
	SystemService systemService;
	
	@Resource
	EntrustServiceC entrustServiceC;
	
	/**
	 * 查询委托信息
	 */
	@RequestMapping(params = "getMemberEntrustList", method = RequestMethod.POST)
	public void getMemberEntrustList(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,PageBean pageBean,
			MemberEntrustBean memberEntrustBean,
			MemberBean memberBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, meService.getMemberEntrustList(memberBean, pageBean),pageBean.getTotal());
	}
	
	/**
	 * 修改委托状态 ，接受/拒绝
	 */
	@RequestMapping(params = "updateEntrustState", method = RequestMethod.POST)
	public void updateEntrustState(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			MemberEntrustBean memberEntrustBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = meService.updateEntrustState(memberEntrustBean);
		if(num > 0)
		{
		    WriteObject(response, "操作成功");	
		}else
		{
			WriteError(response, "操作失败");
		}
	}
	
	
	/**
	 * 查询委托详情
	 */
	@RequestMapping(params = "getEntrustDateilByNumber", method = RequestMethod.POST)
	public void getEntrustDateilByNumber(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,PageBean pageBean,
			EntrustBean entrustBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, entrustServiceC.getEntrustDateilByNumber(entrustBean, pageBean),pageBean.getTotal());
	}
	
	/**
	 * 导出客户委托列表
	 */
	@RequestMapping(params = "exportMemberEntrustExcel", method = RequestMethod.POST)
	public void exportMemberEntrustExcel(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			MemberEntrustBean memberEntrustBean,
			MemberBean memberBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
        String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+".xls";
		
		List<ExcelBean> excelBeans=new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("委托公司").setType("company_name"));
		excelBeans.add(new ExcelBean().setName("公司等级").setType("member_level_show"));
		excelBeans.add(new ExcelBean().setName("委托单号").setType("entrust_number"));
		excelBeans.add(new ExcelBean().setName("委托时间").setType("create_time"));
		excelBeans.add(new ExcelBean().setName("状态").setType("entrust_state_show"));
		excelBeans.add(new ExcelBean().setName("接受时间").setType("accept_time"));
		
		List<Object> memberEntrustBeans = meService.exportMemberEntrustExcel(memberBean);
		
		boolean is_success=ExcelUtils.exportExcel(request.getSession().getServletContext()
				.getRealPath("/")+"/excel/"+fileName, excelBeans,memberEntrustBeans,response);
		if(is_success){
			WriteMsg(response, "/excel/"+fileName);
		}else{
			WriteError(response, "导出失败");
		}
	}
	
	/**
	 * 导出委托详情列表（买）
	 */
	@RequestMapping(params = "exportMemberEntrustBuyExcel", method = RequestMethod.POST)
	public void exportMemberEntrustBuyExcel(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			EntrustBean entrustBean,
			MemberBean memberBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
        String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+".xls";
		
		List<ExcelBean> excelBeans=new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("型号").setType("goods_name"));
		excelBeans.add(new ExcelBean().setName("数量").setType("goods_num"));
		excelBeans.add(new ExcelBean().setName("目标单价").setType("target_unit_price"));
		excelBeans.add(new ExcelBean().setName("类型").setType("data_categories_show"));
		excelBeans.add(new ExcelBean().setName("截止时间").setType("end_time"));
		
		List<Object> EntrustBeans = entrustServiceC.getEntrustDateilByNumberNoPage(entrustBean);
		
		boolean is_success=ExcelUtils.exportExcel(request.getSession().getServletContext()
				.getRealPath("/")+"/excel/"+fileName, excelBeans,EntrustBeans,response);
		if(is_success){
			WriteMsg(response, "/excel/"+fileName);
		}else{
			WriteError(response, "导出失败");
		}
	}
	
	/**
	 * 导出委托详情列表（卖）
	 */
	@RequestMapping(params = "exportMemberEntrustSellExcel", method = RequestMethod.POST)
	public void exportMemberEntrustSellExcel(HttpServletRequest request,HttpServletResponse response,
			MerchantsAccountBean merchantsAccountBean,
			EntrustBean entrustBean,
			MemberBean memberBean) throws Exception
	{
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
        String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+".xls";
		
		List<ExcelBean> excelBeans=new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("型号").setType("goods_name"));
		excelBeans.add(new ExcelBean().setName("数量").setType("goods_num"));
		excelBeans.add(new ExcelBean().setName("目标单价").setType("target_unit_price"));
		excelBeans.add(new ExcelBean().setName("最小起订量").setType("min_buy_num"));
		excelBeans.add(new ExcelBean().setName("最小包装量").setType("min_pack_num"));
		excelBeans.add(new ExcelBean().setName("类型").setType("data_categories_show"));
		excelBeans.add(new ExcelBean().setName("交期").setType("give_time"));
		
		List<Object> EntrustBeans = entrustServiceC.getEntrustDateilByNumberNoPage(entrustBean);
		
		boolean is_success=ExcelUtils.exportExcel(request.getSession().getServletContext()
				.getRealPath("/")+"/excel/"+fileName, excelBeans,EntrustBeans,response);
		if(is_success){
			WriteMsg(response, "/excel/"+fileName);
		}else{
			WriteError(response, "导出失败");
		}
	}
}
