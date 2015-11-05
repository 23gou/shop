package cn.gou23.shop.handler;

import java.util.Date;
import java.util.List;

import cn.gou23.shop.model.ItemSourceModel;

/**
 * 
 * 
 * 描述:货源处理器
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月17日 下午5:19:36
 */
public interface ItemSourceHandler {
	/**
	 * 
	 * 描述:从货源url中解析出货源id
	 * 
	 * @param url
	 * @param content
	 * @return
	 * @author liyixing 2015年8月17日 下午5:20:14
	 */
	public String parseSourceId(String url);

	/**
	 * 
	 * 描述:解析信息，
	 * 
	 * 
	 * @param itemSourceModel
	 * @author liyixing 2015年8月18日 下午4:08:34
	 * @return true 表示货源依然有效，false表示货源已经无效
	 */
	public boolean parseInfo(ItemSourceModel itemSourceModel, String content);

	/**
	 * 
	 * 描述:读取最新的销售数
	 * 
	 * @param shopId
	 * @param sessionKey
	 * @param updatePrice
	 * @return
	 * @author liyixing 2015年8月26日 下午3:38:39
	 */
	public void syncTotalSoldQuantity(List<ItemSourceModel> itemSourceModels);

	/**
	 * 
	 * 描述:读取最新的SKU数据
	 * 
	 * @param shopId
	 * @param sessionKey
	 * @param updatePrice
	 * @return
	 * @author liyixing 2015年8月26日 下午3:38:39
	 */
	public String syncItemSourceSku(List<ItemSourceModel> itemSourceModels,
			String sessionKey);

	/**
	 * 
	 * 描述:更新最后一次交易时间
	 * 
	 * @param itemSourceModel
	 * @param content
	 * @return
	 * @author liyixing 2015年11月3日 下午4:41:37
	 */
	public Date syncLastTradingTime(ItemSourceModel itemSourceModel,
			String content);

	/**
	 * 
	 * 
	 * 描述:任务完成事件
	 *
	 * @author liyixing
	 * @version 1.0
	 * @since 2015年11月3日 下午5:12:44
	 */
	public static interface Result {
		/**
		 * 
		 * 描述:成功
		 * 
		 * @author liyixing 2015年11月3日 下午5:22:38
		 */
		public void doSuccess();

		/**
		 * 描述:失败
		 * 
		 * @param exception
		 * @author liyixing 2015年11月3日 下午5:22:55
		 */
		public void doError(Exception exception);
	}
}
