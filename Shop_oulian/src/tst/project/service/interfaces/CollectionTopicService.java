package tst.project.service.interfaces;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tst.project.bean.bbs.CollectionTopicBean;
import tst.project.bean.bbs.TopicBean;
import tst.project.dao.interfaces.CollectionTopicDao;
import tst.project.dao.interfaces.TopicDao;

@Service
@Transactional(rollbackFor={Exception.class})
public class CollectionTopicService
{

  @Resource
  CollectionTopicDao collectionTopicDao;

  @Resource
  TopicDao topicDao;

  public int insertCollectionTopic(CollectionTopicBean collectionTopicBean)
    throws Exception
  {
    int num = this.collectionTopicDao.insertCollectionTopic(collectionTopicBean);
    if (num > 0)
    {
      int num1 = this.topicDao.updateCollectionsCount(new TopicBean()
        .setId(Integer.valueOf(collectionTopicBean.getRelation_id()).intValue())
        .setUpdate_type("add"));
      if (num1 < 0)
      {
        throw new Exception("更新帖子点赞总数失败");
      }
    }
    return num;
  }

  public int updateCollcetionTopic(CollectionTopicBean collectionTopicBean)
    throws Exception
  {
    int num = this.collectionTopicDao.insertCollectionTopic(collectionTopicBean);
    if (num > 0)
    {
      int num1 = this.topicDao.updateCollectionsCount(new TopicBean()
        .setId(Integer.valueOf(collectionTopicBean.getRelation_id()).intValue())
        .setUpdate_type("subtract"));
      if (num1 < 0)
      {
        throw new Exception("更新帖子点赞总数失败");
      }
    }
    return num;
  }

  public CollectionTopicBean getCollectionBySearch(CollectionTopicBean collectionTopicBean)
  {
    return this.collectionTopicDao.getCollectionBySearch(collectionTopicBean);
  }

  public CollectionTopicBean getStateByCollection(CollectionTopicBean collectionTopicBean)
  {
    return this.collectionTopicDao.getStateByCollection(collectionTopicBean);
  }
}