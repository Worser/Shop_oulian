package tst.project.service.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tst.project.bean.goods.SearchHistoryBean;
import tst.project.bean.member.CustomerServiceBean;
import tst.project.dao.controller.SearchHistoryDaoC;
import tst.project.dao.interfaces.MemberDao;
import tst.project.page.PageBean;

@Service
@Transactional(rollbackFor={Exception.class})
public class SearchHistoryServiceC
{

  @Resource
  SearchHistoryDaoC searchHistoryDaoC;

  @Resource
  MemberDao memberDao;

  @Resource
  MemberServiceC memberServiceC;

  public List<SearchHistoryBean> getAllSearchHistorys(SearchHistoryBean searchHistoryBean, PageBean pageBean)
  {
    List<SearchHistoryBean> searchHistoryBeans = searchHistoryDaoC.getAllSearchHistorys(searchHistoryBean, pageBean);
    if (searchHistoryBeans.size() > 0) {
      for (int i = 0; i <= searchHistoryBeans.size() - 1; i++) {
        List<CustomerServiceBean> getMemberCustomerServiceQQ = this.memberDao.getMemberCustomerServiceQQ(new CustomerServiceBean()
          .setMember_id(String.valueOf((searchHistoryBeans.get(i)).getMember_id())));
        (searchHistoryBeans.get(i)).setCustomerServiceBeans(getMemberCustomerServiceQQ);
      }
    }
    return searchHistoryBeans;
  }

  public List<Object> exportSearchHistoryExcel(SearchHistoryBean searchHistoryBean)
  {
    return this.searchHistoryDaoC.exportSearchHistoryExcel(searchHistoryBean);
  }
}