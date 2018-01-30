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
import tst.project.bean.goods.SearchHistoryBean;
import tst.project.bean.merchants.MerchantsAccountBean;
import tst.project.page.PageBean;
import tst.project.service.controller.SearchHistoryServiceC;
import tst.project.service.controller.SystemService;
import tst.project.utils.ExcelUtils;
import tst.project.utils.TimeUtils;

@Controller
@RequestMapping("/searchHistoryController.api")
public class SearchHistoryController extends BaseController
{

  @Resource
  SearchHistoryServiceC searchHistoryServiceC;

  @Resource
  SystemService systemService;

  @RequestMapping(params="getAllSearchHistorys", method=RequestMethod.POST)
  public void getAllSearchHistorys(SearchHistoryBean searchHistoryBean, PageBean pageBean, HttpServletRequest request, HttpServletResponse response, MerchantsAccountBean merchantsAccountBean)
    throws Exception
  {
    if (!this.systemService.verToken(merchantsAccountBean)) {
      WritePending(response, "token failed");
      return;
    }
    WriteObject(response, searchHistoryServiceC.getAllSearchHistorys(searchHistoryBean, pageBean), pageBean.getTotal());
  }

  @RequestMapping(params="exportSearchHistoryExcel")
  public void exportSearchHistoryExcel(MerchantsAccountBean merchantsAccountBean, SearchHistoryBean searchHistoryBean, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    if (!this.systemService.verToken(merchantsAccountBean)) {
      WritePending(response, "token failed");
      return;
    }

    String fileName = TimeUtils.getCurrentTime("yyyyMMddHHmmss") + ".xls";

    List<ExcelBean> excelBeans = new ArrayList<ExcelBean>();
    excelBeans.add(new ExcelBean().setName("型号名称").setType("search_name"));
    excelBeans.add(new ExcelBean().setName("公司名称").setType("company_name"));
    excelBeans.add(new ExcelBean().setName("联系人").setType("legal_person_name"));
    excelBeans.add(new ExcelBean().setName("联系方式").setType("member_account"));
    excelBeans.add(new ExcelBean().setName("搜索时间").setType("create_time"));

    List<Object> searchHistoryBeans = searchHistoryServiceC.exportSearchHistoryExcel(searchHistoryBean);

    boolean is_success = ExcelUtils.exportExcel(request.getSession().getServletContext()
      .getRealPath("/") + "/excel/" + fileName, excelBeans, searchHistoryBeans, response);
    if (is_success)
      WriteMsg(response, "/excel/" + fileName);
    else
      WriteError(response, "导出失败");
  }
}