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
import tst.project.bean.LogRecordBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.page.PageBean;
import tst.project.service.controller.LogRecordServiceC;
import tst.project.service.controller.SystemService;
import tst.project.utils.ExcelUtils;
import tst.project.utils.TimeUtils;


/**
 * 
 * @author shenjiabo
 *
 */
@Controller
@RequestMapping("/logRecordController.api")
public class LogRecordController extends BaseController{
	@Resource
	SystemService systemService;
	
	@Resource
	LogRecordServiceC daoC;
	
	/**
	 *获得记录
	 * @param merchantsBean
	 * @param bannerBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "getLogRecordBeans", method = RequestMethod.POST)
	public void getLogRecordBeans(MerchantsAccountBean merchantsAccountBean,
			LogRecordBean logRecordBean,HttpServletRequest request,
			HttpServletResponse response,PageBean pageBean) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, daoC.getLogRecordBeans(logRecordBean,pageBean),pageBean.getTotal());
	}

	/**
	 *获得记录
	 * @param merchantsBean
	 * @param bannerBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "exportLogRecordBeans", method = RequestMethod.POST)
	public void exportLogRecordBeans(MerchantsAccountBean merchantsAccountBean,
			LogRecordBean logRecordBean,HttpServletRequest request,
			HttpServletResponse response,PageBean pageBean) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
        String fileName=TimeUtils.getCurrentTime("yyyyMMddHHmmss")+".xls";
		
		List<ExcelBean> excelBeans=new ArrayList<ExcelBean>();
		excelBeans.add(new ExcelBean().setName("ID").setType("record_id"));
		excelBeans.add(new ExcelBean().setName("型号名称").setType("record_name"));
		excelBeans.add(new ExcelBean().setName("供应商名称").setType("company_name"));
		excelBeans.add(new ExcelBean().setName("类型").setType("record_type_show"));
		excelBeans.add(new ExcelBean().setName("创建时间").setType("create_time"));
		
		List<Object> memberEntrustBeans = daoC.ExportLogRecordBeans(logRecordBean);
		
		boolean is_success=ExcelUtils.exportExcel(request.getSession().getServletContext()
				.getRealPath("/")+"/excel/"+fileName, excelBeans,memberEntrustBeans,response);
		if(is_success){
			WriteMsg(response, "/excel/"+fileName);
		}else{
			WriteError(response, "导出失败");
		}
	}
	
	/**
	 * 批量删除
	 * @param merchantsBean
	 * @param bannerBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "batchDelete", method = RequestMethod.POST)
	public void batchDelete(MerchantsAccountBean merchantsAccountBean,
			LogRecordBean logRecordBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = daoC.batchDelete(logRecordBean);
		if(num > 0)
		{
			WriteObject(response, "操作成功");
		}else
		{
			WriteError(response, "操作失败");
		}
	}
	
	/**
	 * 全部删除
	 * @param merchantsBean
	 * @param bannerBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "allDelete", method = RequestMethod.POST)
	public void allDelete(MerchantsAccountBean merchantsAccountBean,
			LogRecordBean logRecordBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(!systemService.verToken(merchantsAccountBean)){
			WritePending(response, "token failed");
			return;
		}
		
		int num = daoC.allDelete(logRecordBean);
		if(num > 0)
		{
			WriteObject(response, "操作成功");
		}else
		{
			WriteError(response, "操作失败");
		}
	}
	
}
