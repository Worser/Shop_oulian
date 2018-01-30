package tst.project.service.interfaces;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tst.project.bean.bbs.SearchTopicHistoryBean;
import tst.project.dao.interfaces.SearchTopicHistoryDao;

@Service
@Transactional(rollbackFor={Exception.class})
public class SearchTopicHistoryService
{

  @Resource
  private SearchTopicHistoryDao dao;

  public List<SearchTopicHistoryBean> getSearchHistory(SearchTopicHistoryBean searchTopicHistoryBean)
    throws Exception
  {
    return this.dao.getSearchTopicHistory(searchTopicHistoryBean);
  }

  @Transactional
  public int addSearchHistory(SearchTopicHistoryBean searchTopicHistoryBean)
    throws Exception
  {
    return this.dao.addSearchTopicHistory(searchTopicHistoryBean);
  }

  public int removeSearchHistory(SearchTopicHistoryBean searchTopicHistoryBean)
    throws Exception
  {
    return this.dao.removeSearchTopicHistory(searchTopicHistoryBean);
  }
}