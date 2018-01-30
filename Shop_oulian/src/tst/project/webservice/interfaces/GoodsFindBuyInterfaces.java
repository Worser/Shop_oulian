package tst.project.webservice.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import tst.project.bean.LogRecordBean;
import tst.project.bean.goods.GoodsBean;
import tst.project.bean.goods.GoodsFindBuyBean;
import tst.project.bean.goods.OfflineQuotationBean;
import tst.project.bean.member.MemberBean;
import tst.project.page.PageBean;
import tst.project.service.interfaces.GoodsFindBuyService;
import tst.project.service.interfaces.GoodsServiceI;
import tst.project.service.interfaces.LogRecordService;
import tst.project.service.interfaces.MemberService;
import tst.project.utils.ExcelUtils;
import tst.project.utils.NumberUtils;
import tst.project.utils.OulianUtils;
import tst.project.utils.TimeUtils;
import tst.project.webservice.controller.BaseController;

@Controller
@RequestMapping("/goodsFindBuyInterfaces.api")
public class GoodsFindBuyInterfaces extends BaseController{
	
	@Resource
	GoodsFindBuyService findBuyService;
	
	@Resource
	MemberService memberService;
	
	@Resource
	GoodsServiceI goodsServiceI;
	
	@Resource
	LogRecordService recordService;
	
	/**
	 * 发布求购（json）
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "addGoodsFindBuy", method = RequestMethod.POST)
	public void addGoodsFindBuy(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String json = request.getParameter("json");
		List<GoodsFindBuyBean> findBuyBeans = new Gson().fromJson(json, new TypeToken<List<GoodsFindBuyBean>>(){}.getType());
		if(findBuyBeans.size()>0)
		{
			for (int i = 0; i < findBuyBeans.size(); i++) {
				GoodsFindBuyBean findBuyBean = findBuyBeans.get(i);
				
				//查询型号是否存在
				GoodsBean goodsBean = goodsServiceI.getGoodsIsSupply(new GoodsBean()
				              .setGoods_name(findBuyBean.getGoods_name()));
				if(goodsBean!=null)
				{
					//查询我是否求购过此商品
					GoodsFindBuyBean findBuyBean1 = findBuyService.getGoodsMemberIsFindBuy(findBuyBean
					                 .setMember_id(memberBean.getMember_id()+""));
					if(findBuyBean1==null)
					{
						int num = findBuyService.addGoodsFindBuy(findBuyBean
								  .setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								  .setIs_delete("0"));
						if(num < 0)
						{
							WriteError(response, "发布求购失败"+findBuyBean.getGoods_name());
						}
					}else
					{
						int num = findBuyService.updateGoodsFindBuy(findBuyBean.setFind_id(findBuyBean1.getFind_id()));
						if(num<0)
						{
							throw new Exception("发布求购失败");
						}
					}
					
				}else
				{
					LogRecordBean recordBean = new LogRecordBean();
					recordBean.setMember_id(memberBean.getMember_id()+"");
					recordBean.setRecord_name(findBuyBean.getGoods_name());
					recordBean.setRecord_type("no_find_buy_goods");
					
					int num = recordService.addLogRecord(recordBean);
					if(num>0)
					{
						//查询我是否求购过此商品
						GoodsFindBuyBean findBuyBean1 = findBuyService.getGoodsMemberIsFindBuy(findBuyBean
						                 .setMember_id(memberBean.getMember_id()+""));
						if(findBuyBean1==null)
						{
							int num1 = findBuyService.addGoodsFindBuy(findBuyBean
									.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
									.setIs_delete("0"));
							if(num1 < 0)
							{
								WriteError(response, "发布求购失败"+findBuyBean.getGoods_name());
							}
						}else
						{
							int num1 = findBuyService.updateGoodsFindBuy(findBuyBean.setFind_id(findBuyBean1.getFind_id()));
							if(num1<0)
							{
								throw new Exception("发布求购失败");
							}
						}
					}else
					{
						WriteError(response, "记录平台未有型号失败"+findBuyBean.getGoods_name());
					}
				}
			}
			WriteObject(response, "发布求购成功");
		}else
		{
			WriteError(response, "无任何发布求购数据");
		}
	}
	
	/**
	 * 发布求购（快速粘贴）
	 */
	@RequestMapping(params = "releaseFindBuyPaste", method = RequestMethod.POST)
	public void releaseFindBuyPaste(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		String json = request.getParameter("json");
		json = json.substring(0, json.length()-1);
		
		if(json!=null && !json.equals(""))
		{
			String[] strArray = json.split(";");
			for (int i = 0; i < strArray.length; i++) {
				GoodsFindBuyBean findBuyBean = new GoodsFindBuyBean();
				String[] strArray1 = strArray[i].toString().split(",");
				
				findBuyBean.setGoods_name(strArray1[0]); //型号
				findBuyBean.setGoods_name(strArray1[1]); //数量
				findBuyBean.setTarget_unit_price(strArray1[2]); //目标单价
				findBuyBean.setData_categories(strArray1[3]); //类型
				findBuyBean.setEnd_time(strArray1[4]); //截止日期
				
				//查询型号是否存在
				GoodsBean goodsBean = goodsServiceI.getGoodsIsSupply(new GoodsBean()
				              .setGoods_name(findBuyBean.getGoods_name()));
				if(goodsBean!=null)
				{
					//查询我是否求购过此商品
					GoodsFindBuyBean findBuyBean1 = findBuyService.getGoodsMemberIsFindBuy(findBuyBean
					                 .setMember_id(memberBean.getMember_id()+""));
					if(findBuyBean1==null)
					{
						int num = findBuyService.addGoodsFindBuy(findBuyBean
								  .setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
								  .setIs_delete("0"));
						if(num < 0)
						{
							WriteError(response, "发布求购失败"+findBuyBean.getGoods_name());
						}
					}else
					{
						int num = findBuyService.updateGoodsFindBuy(findBuyBean.setFind_id(findBuyBean1.getFind_id()));
						if(num<0)
						{
							throw new Exception("发布求购失败");
						}
					}
					
				}else
				{
					LogRecordBean recordBean = new LogRecordBean();
					recordBean.setMember_id(memberBean.getMember_id()+"");
					recordBean.setRecord_name(findBuyBean.getGoods_name());
					recordBean.setRecord_type("no_find_buy_goods");
					
					int num = recordService.addLogRecord(recordBean);
					if(num>0)
					{
						//查询我是否求购过此商品
						GoodsFindBuyBean findBuyBean1 = findBuyService.getGoodsMemberIsFindBuy(findBuyBean
						                 .setMember_id(memberBean.getMember_id()+""));
						if(findBuyBean1==null)
						{
							int num1 = findBuyService.addGoodsFindBuy(findBuyBean
									.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
									.setIs_delete("0"));
							if(num1 < 0)
							{
								WriteError(response, "发布求购失败"+findBuyBean.getGoods_name());
							}
						}else
						{
							int num1 = findBuyService.updateGoodsFindBuy(findBuyBean.setFind_id(findBuyBean1.getFind_id()));
							if(num1<0)
							{
								throw new Exception("发布求购失败");
							}
						}
					}else
					{
						WriteError(response, "记录平台未有型号失败"+findBuyBean.getGoods_name());
					}
				}
			}
			WriteObject(response, "发布求购成功");
		}else
		{
			WriteError(response, "无任何发布求购信息");
		}
	}
	
	/**
	 * 查询用户求购
	 */
	@RequestMapping(params = "getMemberFindBuy", method = RequestMethod.POST)
	public void getMemberFindBuy(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response,GoodsFindBuyBean goodsFindBuyBean,
			PageBean pageBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, findBuyService.getMemberFindBuy(goodsFindBuyBean, pageBean),pageBean.getTotal());
	}
	
	/**
	 * 查询单个求购信息 （--商家端）
	 */
	@RequestMapping(params = "getFindBuyDatilsById", method = RequestMethod.POST)
	public void getFindBuyDatilsById(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response,
			GoodsFindBuyBean goodsFindBuyBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		WriteObject(response, findBuyService.getFindBuyDatilsById(goodsFindBuyBean));
	}
	
	/**
	 * 修改求购信息
	 */
	@RequestMapping(params = "updateGoodsFindBuy", method = RequestMethod.POST)
	public void updateGoodsFindBuy(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response,
			GoodsFindBuyBean goodsFindBuyBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num = findBuyService.updateGoodsFindBuy(goodsFindBuyBean);
		if(num > 0)
		{
			WriteObject(response, "修改成功");
		}else
		{
			WriteError(response, "修改失败");
		}
	}
	
	/**
	 * 删除求购
	 */
	@RequestMapping(params = "deleteGoodsFindBuy", method = RequestMethod.POST)
	public void deleteGoodsFindBuy(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response,
			GoodsFindBuyBean goodsFindBuyBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num = findBuyService.deleteGoodsFindBuy(goodsFindBuyBean);
		if(num > 0)
		{
			WriteObject(response, "删除成功");
		}else
		{
			WriteError(response, "删除失败");
		}
	}
	
	/**
	 * 批量删除求购
	 */
	@RequestMapping(params = "deleteMoreGoodsFindBuy", method = RequestMethod.POST)
	public void deleteMoreGoodsFindBuy(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response,
			GoodsFindBuyBean goodsFindBuyBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num = findBuyService.deleteMoreGoodsFindBuy(goodsFindBuyBean);
		if(num > 0)
		{
			WriteObject(response, "删除成功");
		}else
		{
			WriteError(response, "删除失败");
		}
	}
	
	/**
	 * 一键延期
	 */
	@RequestMapping(params = "batchAddTime", method = RequestMethod.POST)
	public void batchAddTime(MemberBean memberBean,HttpServletRequest request,
			HttpServletResponse response,
			GoodsFindBuyBean goodsFindBuyBean) throws Exception
	{
		if (memberService.verificationToken(memberBean) == null) {
			WritePending(response, "token failed");
			return;
		}
		
		int num = findBuyService.batchAddTime(goodsFindBuyBean);
		if(num > 0)
		{
		   WriteObject(response, "延期成功");	
		}else
		{
		   WriteError(response, "延期失败");
		}
	}
	
	/**
	 * 最新求购信息（供应页面）
	 */
	@RequestMapping(params = "GetNewestFindBuyInfo", method = RequestMethod.POST)
	public void GetNewestFindBuyInfo(HttpServletRequest request,
			HttpServletResponse response,
			GoodsFindBuyBean goodsFindBuyBean) throws Exception
	{
		WriteObject(response, findBuyService.GetNewestFindBuyInfo(goodsFindBuyBean));
	}
	
	/**
	 * 快速搜索（匹配满足条件的供应数据）（粘贴）
	 */
	@RequestMapping(params = "moreSearch", method = RequestMethod.POST)
	public void moreSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{ 
		List<GoodsBean> goodsBeans  = new ArrayList<GoodsBean>();
		
		//获得文本，进行分割
		String json = request.getParameter("json");
		json = json.substring(0, json.length()-1);
		
		if(json!=null && !json.equals(""))
		{
			String[] strArray = json.split(";");
			for (int i = 0; i < strArray.length; i++) {
				
				String[] strArray1 = strArray[i].toString().split(",");
				
				GoodsBean goodsBean = new GoodsBean();
				goodsBean.setGoods_name(strArray[0]);
				goodsBean.setGoods_stock(Integer.valueOf(strArray1[1])+"");
				
				GoodsBean goodsBean1 = findBuyService.getMoreSearch(goodsBean);
				if(goodsBean1!=null)
				{
					goodsBeans.add(goodsBean1);	
				}
			}
			WriteObject(response, goodsBeans);
		}else
		{
			WriteError(response, "无任何数据");
		}
		
	}
	
	
	/**
	 * 快速搜索（匹配满足条件的供应数据）（JSON）
	 */
	@RequestMapping(params = "moreSearchJson", method = RequestMethod.POST)
	public void moreSearchJson(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{ 
		List<GoodsBean> goodsBeans  = new ArrayList<GoodsBean>();
		
		//获得json,进行解析
		String json = request.getParameter("json");
		List<GoodsBean> goodsBeans1=new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {}.getType());
		
		if(goodsBeans1.size()>0)
		{
			for (int i = 0; i < goodsBeans1.size(); i++) {
				
				GoodsBean goodsBean = goodsBeans1.get(i);
				GoodsBean goodsBean1 = findBuyService.getMoreSearch(goodsBean);
				if(goodsBean1!=null)
				{
					goodsBeans.add(goodsBean1);	
				}
			}
			WriteObject(response, goodsBeans);
		}else
		{
			WriteError(response, "无任何数据");
		}
		
	}
	
	
	/**
	 * 上传发布供应excel文件
	 */
	@RequestMapping(params = "uploadFindBuyExcel", method = RequestMethod.POST)
	public void uploadSupplyExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		String json=uploadFile(request, "/excel/find_buy/");		
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
	 * 发布求购（excle）
	 */
	 @RequestMapping(params = "releaseFindBuyJson", method = RequestMethod.POST)
	 public void releaseFindBuyJson(MemberBean memberBean,HttpServletRequest request,
				HttpServletResponse response) throws Exception
	 {
		 if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
		 }
		 
		 int number = 0; //错误条数
		 
		 String json = request.getParameter("json");
		 String result=ExcelUtils.readExcel(request.getSession().getServletContext()
					.getRealPath("/")+json);
		
		 //替换求购excle中的字段，
		 result = OulianUtils.replaceFindBuyField(result);
			
//			String result=ExcelUtils.readExcel("C:\\Users\\JOSY.Lenovo-PC\\Desktop\\goods_find_buy.xlsx");
		 List<GoodsFindBuyBean> findBuyBeans = new Gson().fromJson(result, new TypeToken<List<GoodsFindBuyBean>>() {}.getType());
		 if(findBuyBeans!=null)
		 {
			 for (int i = 0; i < findBuyBeans.size(); i++) {
					GoodsFindBuyBean findBuyBean = findBuyBeans.get(i);
					
					//查询型号是否存在
					GoodsBean goodsBean = goodsServiceI.getGoodsIsSupply(new GoodsBean()
					              .setGoods_name(findBuyBean.getGoods_name()));
					if(goodsBean!=null)
					{
						//查询我是否求购过此商品
						GoodsFindBuyBean findBuyBean1 = findBuyService.getGoodsMemberIsFindBuy(findBuyBean
						                 .setMember_id(memberBean.getMember_id()+""));
						if(findBuyBean1==null)
						{
							int num = findBuyService.addGoodsFindBuy(findBuyBean
									  .setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
									  .setIs_delete("0"));
							if(num < 0)
							{
//								WriteError(response, "发布求购失败"+findBuyBean.getGoods_name());
								number+=1;
							}
						}else
						{
							int num = findBuyService.updateGoodsFindBuy(findBuyBean.setFind_id(findBuyBean1.getFind_id()));
							if(num<0)
							{
//								throw new Exception("发布求购失败");
								number+=1;
							}
						}
						
					}else
					{
						LogRecordBean recordBean = new LogRecordBean();
						recordBean.setMember_id(memberBean.getMember_id()+"");
						recordBean.setRecord_name(findBuyBean.getGoods_name());
						recordBean.setRecord_type("no_find_buy_goods");
						
						int num = recordService.addLogRecord(recordBean);
						if(num>0)
						{
							//查询我是否求购过此商品
							GoodsFindBuyBean findBuyBean1 = findBuyService.getGoodsMemberIsFindBuy(findBuyBean
							                 .setMember_id(memberBean.getMember_id()+""));
							if(findBuyBean1==null)
							{
								int num1 = findBuyService.addGoodsFindBuy(findBuyBean
										.setCreate_time(TimeUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"))
										.setIs_delete("0"));
								if(num1 < 0)
								{
//									WriteError(response, "发布求购失败"+findBuyBean.getGoods_name());
									number+=1;
								}
							}else
							{
								int num1 = findBuyService.updateGoodsFindBuy(findBuyBean.setFind_id(findBuyBean1.getFind_id()));
								if(num1<0)
								{
//									throw new Exception("发布求购失败");
									number+=1;
								}
							}
						}else
						{
//							WriteError(response, "记录平台未有型号失败"+findBuyBean.getGoods_name());
							number+=1;
						}
					}
				}
				WriteObject(response, "发布求购成功,其中失败条数为："+number);
		 }else
		 {
			 WriteError(response, "无任何数据");
		 }
	 }
	 
	 
	 /**
	  * 品牌求购列表（求购）
	  */
	 @RequestMapping(params = "getBrandFindBuyList", method = RequestMethod.POST)
	 public void getBrandFindBuyList(HttpServletRequest request,
				HttpServletResponse response,PageBean pageBean,
				GoodsBean goodsBean) throws Exception
	 {
		 WriteObject(response, findBuyService.getBrandFindBuyList(goodsBean, pageBean),pageBean.getTotal());
	 }
	 
	 /**
	  * 根据型号查询该详情（从平台商品点进去）
	  */
	@RequestMapping(params = "getGoodsDataByName", method = RequestMethod.POST)
	public void getGoodsDataByName(HttpServletRequest request,
			HttpServletResponse response,
			GoodsBean goodsBean) throws Exception
	{
		WriteObject(response, findBuyService.getGoodsDataByName(goodsBean));
	}
	 
	/**
	 * 根据型号查询有多少求购商
	 */
	@RequestMapping(params = "getFindBuyMemberByGoodsName", method = RequestMethod.POST)
	public void getFindBuyMemberByGoodsName(HttpServletRequest request,
			HttpServletResponse response,
			GoodsFindBuyBean goodsFindBuyBean,
			PageBean pageBean) throws Exception
	{
		WriteObject(response, findBuyService.getFindBuyMemberByGoodsName(goodsFindBuyBean, pageBean),pageBean.getTotal());
	}
	
	/**
	 * 根据品牌查询求购列表（分页）
	 */
	 @RequestMapping(params = "getBrandFindBuyListPage", method = RequestMethod.POST)
	 public void getBrandFindBuyListPage(HttpServletRequest request,
				HttpServletResponse response,PageBean pageBean,
				GoodsFindBuyBean goodsFindBuyBean) throws Exception
	 {
		 WriteObject(response, findBuyService.getBrandFindBuyListPage(goodsFindBuyBean, pageBean),pageBean.getTotal());
	 }
	 
	 /**
	  * 根据型号查询该详情（从商家点进去）
	  */
	 @RequestMapping(params = "getOneFindBuyById", method = RequestMethod.POST)
	 public void getFindBuyDateilById(HttpServletRequest request,
				HttpServletResponse response,
				GoodsFindBuyBean goodsFindBuyBean) throws Exception
	 {
		 WriteObject(response, findBuyService.getOneFindBuyById(goodsFindBuyBean));
	 }
	 

	 /**
	  * 离线报价
	  */
	 @RequestMapping(params = "addOfflineQuotation", method = RequestMethod.POST)
	 public void addOfflineQuotation(HttpServletRequest request,
				HttpServletResponse response,
				MemberBean memberBean,
				OfflineQuotationBean offlineQuotationBean,
				GoodsFindBuyBean findBuyBean) throws Exception
	 {
		 if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
		 }
		 
		 if(findBuyService.getfindBuyWhetherOverdue(findBuyBean)==null)
		 {
			 WriteError(response, "该求购信息已过期");
			 return;
		 }
		 
		 //价格保留两位小数
		 offlineQuotationBean.setQuotation_price(
				 String.valueOf(NumberUtils.KeepDecimal(Double.valueOf(offlineQuotationBean.getQuotation_price()), 2)));
		 
		 int num = findBuyService.addOfflineQuotation(offlineQuotationBean
				                  .setIs_delete("0"));
		 if(num > 0)
		 {
			 WriteObject(response, "离线报价成功");
		 }else
		 {
			 WriteError(response, "离线报价失败");
		 }
	 }
	 
	 /**
	  * 求购信息
	  */
	 @RequestMapping(params = "getFindBuyInfo", method = RequestMethod.POST)
	 public void getFindBuyInfo(MemberBean memberBean,HttpServletRequest request,
				HttpServletResponse response,
				GoodsFindBuyBean goodsFindBuyBean,
				PageBean pageBean) throws Exception
	 {
		 if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
		 }
		 
		 WriteObject(response, findBuyService.getFindBuyInfo(goodsFindBuyBean, pageBean),pageBean.getTotal());
	 }
	 
	 /**
	  * 删除报价（单个）
	  */
	 @RequestMapping(params = "deleteQuotationById", method = RequestMethod.POST)
	 public void deleteQuotationById(MemberBean memberBean,HttpServletRequest request,
				HttpServletResponse response,
				OfflineQuotationBean offlineQuotationBean) throws Exception
	 {
		 if (memberService.verificationToken(memberBean) == null) {
				WritePending(response, "token failed");
				return;
		 }
		 
		 int num = findBuyService.deleteQuotationById(offlineQuotationBean);
		 if(num > 0)
		 {
			 WriteObject(response, "删除成功");
		 }else
		 {
			 WriteError(response, "删除失败");
		 }
	 }
	 
	 /**
	  * 相关求购信息
	  */
	 @RequestMapping(params = "relatedFindBuyMessage", method = RequestMethod.POST)
	 public void relatedFindBuyMessage(HttpServletRequest request,
				HttpServletResponse response,
				GoodsFindBuyBean goodsFindBuyBean,
				PageBean pageBean) throws Exception
	 {
		 WriteObject(response, findBuyService.relatedFindBuyMessage(goodsFindBuyBean, pageBean));
	 }
	 
	 /**
	  * 根据品牌查询求购信息
	  */
	 @RequestMapping(params = "getFindBuyBeansByBrand", method = RequestMethod.POST)
	 public void getFindBuyBeansByBrand(MemberBean memberBean,HttpServletRequest request,
				HttpServletResponse response,
				GoodsFindBuyBean goodsFindBuyBean,
				PageBean pageBean) throws Exception
	 {		 
		 WriteObject(response, findBuyService.getFindBuyBeansByBrand(goodsFindBuyBean, pageBean),pageBean.getTotal());
	 }
}
