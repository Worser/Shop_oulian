package tst.project.service.interfaces;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tst.project.bean.bbs.ThumbBean;
import tst.project.bean.bbs.TopicBean;
import tst.project.dao.interfaces.ThumbDao;
import tst.project.dao.interfaces.TopicDao;

@Service
@Transactional(rollbackFor={Exception.class})
public class ThumbService
{

  @Resource
  ThumbDao thumbDao;

  @Resource
  TopicDao topicDao;

  public int insertThumb(ThumbBean thumbBean)
    throws Exception
  {
    int num = this.thumbDao.insertThumb(thumbBean);
    if (num > 0)
    {
      int num1 = this.topicDao.updateThumbCount(new TopicBean()
        .setId(thumbBean.getRelation_id())
        .setUpdate_type("add"));
      if (num1 < 0)
      {
        throw new Exception("更新帖子点赞总数失败");
      }
    }
    return num;
  }

  public ThumbBean getThumbBySearch(ThumbBean thumbBean)
  {
    return this.thumbDao.getThumbBySearch(thumbBean);
  }

  public ThumbBean getStateByThumb(ThumbBean thumbBean)
  {
    return this.thumbDao.getStateByThumb(thumbBean);
  }

  public int updateThumb(ThumbBean thumbBean)
    throws Exception
  {
    int num = this.thumbDao.updateThumb(thumbBean);
    if (num > 0)
    {
      int num1 = this.topicDao.updateThumbCount(new TopicBean()
        .setId(thumbBean.getRelation_id())
        .setUpdate_type("subtract"));
      if (num1 < 0)
      {
        throw new Exception("更新帖子点赞总数失败");
      }
    }
    return num;
  }
}