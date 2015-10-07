package cn.gou23.shop.service;

import cn.gou23.shop.model.SourceOwnerModel;

/**
 * 
 * 
 * 描述:货源所属人
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年9月7日 上午10:40:09
 */
public interface SourceOwnerService {
	/**
	 * 
	 * 描述:根据类型和所属人获取
	 * 
	 * @return
	 * @author liyixing 2015年9月7日 上午10:40:39
	 */
	public SourceOwnerModel getByOwnerAndType(SourceOwnerModel sourceOwnerModel);
	
	/**
	 * 保存
	 */
	public void save(SourceOwnerModel sourceOwnerModel);
}
