package tst.project.dao.controller;

import java.util.List;
import tst.project.bean.goods.SearchHistoryBean;
import tst.project.page.PageBean;

public  interface SearchHistoryDaoC
{
  public  List<SearchHistoryBean> getAllSearchHistorys(SearchHistoryBean searchHistoryBean, PageBean paramPageBean);

  public  List<Object> exportSearchHistoryExcel(SearchHistoryBean searchHistoryBean);
}