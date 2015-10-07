package cn.gou23.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gou23.shop.dao.ShopEntityMapper;
import cn.gou23.shop.model.ShopModel;
import cn.gou23.shop.service.ShopService;

/**
 * 
 * 
 * 描述:店铺
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月18日 下午2:44:39
 */
@Service
@Transactional
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopEntityMapper shopEntityMapper;

	@Override
	public List<ShopModel> getAll() {
		return shopEntityMapper.selectByExample(null);
	}

}
