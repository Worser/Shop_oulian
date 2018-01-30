package tst.project.service.interfaces;




import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tst.project.bean.goods.GoodsBean;
import tst.project.dao.interfaces.GoodsDaoI;
import tst.project.dao.interfaces.LogRecordDao;

/**
 * @author xingqianji
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsUtilService {
	
	@Resource
	private GoodsDaoI goodsDaoI;
	
	public GoodsBean GoodsBasicInfo(GoodsBean goodsBean)
	{
		GoodsBean goodsBean2 = goodsDaoI.getGoodsBasicDetail(new GoodsBean()
		        .setGoods_name(goodsBean.getGoods_name()));
		goodsBean.setPrice_interval(goodsBean2.getPrice_interval());
		goodsBean.setBuy_num_interval(goodsBean2.getBuy_num_interval());
		goodsBean.setMin_buy_num(goodsBean2.getMin_buy_num());
		goodsBean.setTotalStock(goodsBean2.getTotalStock());
		return goodsBean;
	}
}
