package tst.project.dao.interfaces;

import tst.project.bean.bbs.ThumbBean;

public abstract interface ThumbDao
{
  public abstract int insertThumb(ThumbBean paramThumbBean);

  public abstract ThumbBean getThumbBySearch(ThumbBean paramThumbBean);

  public abstract ThumbBean getStateByThumb(ThumbBean paramThumbBean);

  public abstract int updateThumb(ThumbBean paramThumbBean);
}