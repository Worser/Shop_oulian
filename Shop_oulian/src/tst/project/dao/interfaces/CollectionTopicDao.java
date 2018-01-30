package tst.project.dao.interfaces;

import tst.project.bean.bbs.CollectionTopicBean;

public abstract interface CollectionTopicDao
{
  public abstract int insertCollectionTopic(CollectionTopicBean paramCollectionTopicBean);

  public abstract int updateCollcetionTopic(CollectionTopicBean paramCollectionTopicBean);

  public abstract CollectionTopicBean getCollectionBySearch(CollectionTopicBean paramCollectionTopicBean);

  public abstract CollectionTopicBean getStateByCollection(CollectionTopicBean paramCollectionTopicBean);
}