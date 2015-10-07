package cn.gou23.shop.service;

import java.util.List;

import cn.gou23.shop.model.ItemSourceModel;

/**
 * 
 * 
 * 描述:货源管理
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月17日 下午5:18:22
 */
public interface ItemSourceService {
	public ItemSourceModel get(String id);
	/**
	 * 
	 * 描述:增加货源
	 * 
	 * @param itemSourceModel
	 * @author liyixing 2015年8月17日 下午5:20:45
	 */
	public void add(ItemSourceModel itemSourceModel);

	/**
	 * 
	 * 描述:修改货源
	 * 
	 * @param itemSourceModel
	 * @author liyixing 2015年8月17日 下午5:20:45
	 */
	public void saveSource(ItemSourceModel itemSourceModel);

	/**
	 * 
	 * 描述:删除货源
	 * 
	 * @param itemSourceModel
	 * @author liyixing 2015年8月17日 下午5:20:45
	 */
	public void delete(String id);

	/**
	 * 
	 * 描述:货源ID+店铺 获取
	 * 
	 * @param sourceId
	 * @return
	 * @author liyixing 2015年8月17日 下午5:28:36
	 */
	public ItemSourceModel getBySourceIdAndShopId(
			ItemSourceModel itemSourceModel);

	/**
	 * 
	 * 描述:查询
	 * 
	 * @return
	 * @author liyixing 2015年8月18日 下午10:31:51
	 */
	public List<ItemSourceModel> find(ItemSourceModel itemSourceModel);

	/**
	 * 
	 * 描述:获取淘宝出售中的数据
	 * 
	 * @return
	 * @author liyixing 2015年8月20日 下午4:25:21
	 */
	public List<ItemSourceModel> getByTaobaoAuction(String content);

	/**
	 * 
	 * 描述:获取为下架的
	 * 
	 * @param itemSourceModel
	 * @return
	 * @author liyixing 2015年8月26日 下午1:17:57
	 */
	public List<ItemSourceModel> getNotOff(ItemSourceModel itemSourceModel);

	/**
	 * 
	 * 描述:获取所有状态是上架的
	 * 
	 * @param itemSourceModel
	 * @return
	 * @author liyixing 2015年8月26日 下午1:17:57
	 */
	public List<ItemSourceModel> getOn(ItemSourceModel itemSourceModel);

	/**
	 * 
	 * 描述:查询黑名单
	 * 
	 * @return
	 * @author liyixing 2015年9月1日 上午10:07:26
	 */
	List<ItemSourceModel> findBlacklist();
	
	/**
	 * 
	 * 描述:查询货源黑名单
	 * 
	 * @return
	 * @author liyixing 2015年9月5日 上午10:32:01
	 */
	List<ItemSourceModel> findSourceBlacklist();
	
	/**
	 * 
	 * 描述:添加货源黑名单
	 * 
	 * @return
	 * @author liyixing 2015年9月5日 上午10:32:01
	 */
	void addSourceBlacklist(ItemSourceModel itemSourceModel);
}
