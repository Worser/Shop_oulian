package tst.project.webservice.interfaces;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import tst.project.bean.entrust.EntrustBean;
import tst.project.bean.entrust.MemberEntrustBean;
import tst.project.bean.member.MemberBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.EntrustService;
import tst.project.service.interfaces.MemberEntrustService;
import tst.project.service.interfaces.MemberService;
import tst.project.utils.CreateRandom;
import tst.project.utils.ExcelUtils;
import tst.project.utils.OulianUtils;
import tst.project.utils.TimeUtils;
import tst.project.webservice.controller.BaseController;


@Controller
@RequestMapping("/memberEntrustInterfaces.api")
public class MemberEntrustInterfaces extends BaseController{
	
	@Resource
	MemberEntrustService meService;
	
	@Resource
	MemberService memberService;
	
	@Resource
	EntrustService entrustService;
	
	/**
	 * 添加客户委托信息  （json）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "addMemberEntrust", method = RequestMethod.POST)
	public void addMemberEntrust(HttpServletRequest request,HttpServletResponse response,
			MemberBean memberBean,
			MemberEntrustBean memberEntrustBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String json = request.getParameter("json");
		
		List<EntrustBean> entrustBeans = new Gson().fromJson(json, new TypeToken<List<EntrustBean>>(){}.getType());
		if(entrustBeans!=null)
		{
			String entrust_number = TimeUtils.getCurrentTime("yyyyMMddHHmmss")+CreateRandom.createRandom(true,7);
			
			//添加客户委托
			int num = meService.addMemberEntrust(memberEntrustBean.setEntrust_number(entrust_number)
					  .setEntrust_state("wait_confirm"));
			if(num > 0) {
				for (int i = 0; i < entrustBeans.size(); i++) {
					EntrustBean entrustBean = entrustBeans.get(i);
					int num1 = entrustService.addEntrustDateil(entrustBean.setEntrust_number(entrust_number));
					if(num1 < 0) {
						throw new Exception("添加客户委托详情失败");
					}
				}
				WriteObject(response, "委托成功");
			}else {
				WriteError(response, "添加客户委托失败");
			}
		}else {
			WriteError(response, "数据为空，添加失败");
		}
	}
	
	/**
	 * 查询委托信息
	 */
	@RequestMapping(params = "getMemberEntrustList", method = RequestMethod.POST)
	public void getMemberEntrustList(HttpServletRequest request,HttpServletResponse response,
			MemberBean memberBean,PageBean pageBean,
			MemberEntrustBean memberEntrustBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, meService.getMemberEntrustList(memberEntrustBean, pageBean),pageBean.getTotal());
	}
	
	/**
	 * 根据委托订单号查询委托详情信息
	 */
	@RequestMapping(params = "getEntrustDateilByNumber", method = RequestMethod.POST)
	public void getEntrustDateilByNumber(HttpServletRequest request,HttpServletResponse response,
			MemberBean memberBean,PageBean pageBean,
			EntrustBean entrustBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, entrustService.getEntrustDateilByNumber(entrustBean, pageBean),pageBean.getTotal());
	}
	
	/**
	 * 添加客户委托信息  （快速粘贴）
	 */
	@RequestMapping(params = "addMemberEntrustByPaste", method = RequestMethod.POST)
	public void addMemberEntrustByPaste(HttpServletRequest request,HttpServletResponse response,
			MemberBean memberBean,
			MemberEntrustBean memberEntrustBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String json = request.getParameter("json");
		json = json.substring(0, json.length()-1);
		
		if(json!=null && !json.equals(""))
		{
            String entrust_number = TimeUtils.getCurrentTime("yyyyMMddHHmmss")+CreateRandom.createRandom(true,7);
			
			//添加客户委托
			int num = meService.addMemberEntrust(memberEntrustBean.setEntrust_number(entrust_number)
					  .setEntrust_state("wait_confirm"));
			if(num > 0)
			{	
				String[] strArray = json.split(";");
				for (int i = 0; i < strArray.length; i++) {
					
					EntrustBean entrustBean = new EntrustBean();
					String[] strArray1 = strArray[i].toString().split(",");
					
					//写入参数
					entrustBean.setGoods_name(strArray1[0]); //型号
					entrustBean.setGoods_num(strArray1[1]); //数量
					entrustBean.setTarget_unit_price(strArray1[2]); //单价
					entrustBean.setData_categories(strArray1[3]); //类型
					entrustBean.setEnd_time(strArray1[4]);  //截止日期
					
					//添加委托详情信息
					int num1 = entrustService.addEntrustDateil(entrustBean.setEntrust_number(entrust_number));
					if(num1 < 0)
					{
						throw new Exception("添加客户委托详情失败");
					}
				}
				WriteObject(response, "委托成功");
			}else
			{
				WriteError(response, "添加客户委托失败");
			}
			
		}else
		{
			WriteError(response, "无任何发布委托信息");
		}
	}
	
	/**
	 * 上传发布委托excel文件
	 */
	@RequestMapping(params = "uploadEntrustExcel", method = RequestMethod.POST)
	public void uploadSupplyExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String json=uploadFile(request, "/excel/entrust/");		
		if(json.equals("-1")){
			WriteError(response, "文件不可为空");
			return;
		}else if(json.equals("-2")){
			WriteError(response, "上传失败");	
			return;
		}else
		{
			WriteObject(response, json);
		}
	}
	
	/**
	 * 发布委托（excle）
	 */
	 @RequestMapping(params = "releaseEntrustExcel", method = RequestMethod.POST)
	 public void releaseFindBuyJson(MemberBean memberBean,HttpServletRequest request,
				HttpServletResponse response,
				MemberEntrustBean memberEntrustBean) throws Exception
	 {
		 if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
		 }
		 
		 int number = 0; //错误条数
		 
//		 String json = request.getParameter("json");
//		 String result=ExcelUtils.readExcel(request.getSession().getServletContext()
//					.getRealPath("/")+json);
		 
	     String result=ExcelUtils.readExcel("C:\\Users\\JOSY.Lenovo-PC\\Desktop\\entrust_template.xlsx");
		
		 //替换委托excle中的字段
		 result = OulianUtils.replaceEntrustField(result);
			
		 List<EntrustBean> entrustBeans = new Gson().fromJson(result, new TypeToken<List<EntrustBean>>() {}.getType());
		 if(entrustBeans!=null)
		 {
			 String entrust_number = TimeUtils.getCurrentTime("yyyyMMddHHmmss")+CreateRandom.createRandom(true,7);
				
				//添加客户委托
				int num = meService.addMemberEntrust(memberEntrustBean.setEntrust_number(entrust_number)
						  .setEntrust_state("wait_confirm"));
			    if(num > 0)
			    {
			    	for (int i = 0; i < entrustBeans.size(); i++) {
						
						EntrustBean entrustBean = entrustBeans.get(i);
						int num1 = entrustService.addEntrustDateil(entrustBean.setEntrust_number(entrust_number));
						if(num1 < 0)
						{
//							throw new Exception("添加客户委托详情失败");
							number+=1;
						}
					}
					WriteObject(response, "委托成功,失败条数为："+number);
			    }else
			    {
			    	WriteError(response, "添加客户委托失败");
			    }
		 }else
		 {
			 WriteError(response, "无任何发布委托数据");
		 }
		 
	 }
	 
}
