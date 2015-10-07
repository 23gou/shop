package cn.gou23.shop.parse;

import java.util.List;

import cn.gou23.shop.model.ItemSourceModel;

/**
 * 
 * 
 * 描述:货源解析器
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月17日 下午5:19:36
 */
public interface ItemSourceParse {
	/**
	 * 
	 * 描述:计算货源ID
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
}
