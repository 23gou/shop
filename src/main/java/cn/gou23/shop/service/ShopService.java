package cn.gou23.shop.service;

import java.util.List;

import cn.gou23.shop.model.ShopModel;

/**
 * 
 * 
 * 描述:店铺
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月18日 下午2:43:24
 */
public interface ShopService {
	/**
	 * 
	 * 描述:查询所有
	 * 
	 * @return
	 * @author liyixing 2015年8月18日 下午2:44:06
	 */
	public List<ShopModel> getAll();
}
