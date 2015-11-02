package cn.gou23.shop.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gou23.cgodo.page.PageContext;
import cn.gou23.cgodo.util.UtilUrl;
import cn.gou23.shop.constant.SaleStatus;
import cn.gou23.shop.dao.ItemSourceEntityMapper;
import cn.gou23.shop.dao.SourceBlacklistEntityMapper;
import cn.gou23.shop.entity.ItemSourceEntityCondition;
import cn.gou23.shop.entity.SourceBlacklistEntityCondition;
import cn.gou23.shop.model.ItemSourceModel;
import cn.gou23.shop.model.SourceBlacklistModel;
import cn.gou23.shop.service.ItemSourceService;

/**
 * 
 * 
 * 描述:货源处理
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月17日 下午5:21:14
 */
@Transactional
@Service
public class ItemSourceServiceImpl implements ItemSourceService {
	@Autowired
	private ItemSourceEntityMapper itemSourceEntityMapper;
	@Autowired
	private SourceBlacklistEntityMapper sourceBlacklistEntityMapper;

	@Override
	public void add(ItemSourceModel itemSourceModel) {
		// 添加货源
		itemSourceModel.setSaleStatus(SaleStatus.创建);
		itemSourceEntityMapper.insert(itemSourceModel);
	}

	@Override
	public ItemSourceModel getBySourceIdAndShopId(
			ItemSourceModel itemSourceModel) {
		ItemSourceEntityCondition itemSourceEntityCondition = new ItemSourceEntityCondition();

		itemSourceEntityCondition.createCriteria()
				.andSourceIdEqualTo(itemSourceModel.getSourceId())
				.andShopIdEqualTo(itemSourceModel.getShopId())
				.andTypeEqualTo(itemSourceModel.getType());

		List<ItemSourceModel> itemSourceModels = itemSourceEntityMapper
				.selectByExample(itemSourceEntityCondition);
		return itemSourceModels.size() > 0 ? itemSourceModels.get(0) : null;
	}

	@Override
	public void saveSource(ItemSourceModel itemSourceModel) {
		itemSourceEntityMapper.updateByPrimaryKey(itemSourceModel);
	}

	@Override
	public List<ItemSourceModel> find(ItemSourceModel itemSourceModel) {
		ItemSourceEntityCondition itemSourceEntityCondition = new ItemSourceEntityCondition();
		ItemSourceEntityCondition.Criteria criteria = itemSourceEntityCondition
				.createCriteria();

		if (StringUtils.isNotBlank(itemSourceModel.getTitle())) {
			criteria.andTitleLike("%" + itemSourceModel.getTitle() + "%");
		}

		
		if (itemSourceModel.getSaleStatus() != null) {
			criteria.andSaleStatusEqualTo(itemSourceModel.getSaleStatus());
		}
		
		if (StringUtils.isNotBlank(itemSourceModel.getItemId())) {
			criteria = itemSourceEntityCondition.or();
			criteria.andItemIdEqualTo(itemSourceModel.getItemId());
			if (itemSourceModel.getSaleStatus() != null) {
				criteria.andSaleStatusEqualTo(itemSourceModel.getSaleStatus());
			}
		}

//		criteria.andSaleStatusNotEqualTo(SaleStatus.下架);

		return itemSourceEntityMapper
				.selectByExample(itemSourceEntityCondition);
	}

	@Override
	public List<ItemSourceModel> getNotOff(ItemSourceModel itemSourceModel) {
		ItemSourceEntityCondition itemSourceEntityCondition = new ItemSourceEntityCondition();
		// 指定店铺，状态未下架，指定货源类型
		itemSourceEntityCondition.createCriteria()
				.andShopIdEqualTo(itemSourceModel.getShopId())
				.andSaleStatusNotEqualTo(SaleStatus.下架)
				.andTypeEqualTo(itemSourceModel.getType());

		if (itemSourceModel.getSaleStatus() != null) {
			itemSourceEntityCondition.getOredCriteria().get(0)
					.andSaleStatusEqualTo(itemSourceModel.getSaleStatus());
		}

		return itemSourceEntityMapper
				.selectByExample(itemSourceEntityCondition);

	}

	@Override
	public List<ItemSourceModel> getOn(ItemSourceModel itemSourceModel) {
		ItemSourceEntityCondition itemSourceEntityCondition = new ItemSourceEntityCondition();
		// 指定店铺，状态未下架，指定货源类型
		itemSourceEntityCondition.createCriteria()
				.andShopIdEqualTo(itemSourceModel.getShopId())
				.andSaleStatusEqualTo(SaleStatus.上架)
				.andTypeEqualTo(itemSourceModel.getType());

		return itemSourceEntityMapper
				.selectByExample(itemSourceEntityCondition);
	}

	@Override
	public void delete(String id) {
		itemSourceEntityMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<ItemSourceModel> getByTaobaoAuction(String content) {
		List<ItemSourceModel> itemSourceModels = new ArrayList<ItemSourceModel>();
		Document document = Jsoup.parse(content);
		// 数据量
		PageContext.get().setTotalCount(
				Integer.valueOf(document.getElementById("J_onSaleCount_1")
						.html().replace("共有出售中宝贝", "").replace("条记录", "")));

		// 处理数据
		Elements elements = document.getElementById("J_DataTable")
				.select("tbody.data").get(0).select("tr.with-sid");

		for (Element element : elements) {
			// 排除代销的
			if (element
					.select("img[src=\"//img.alicdn.com/tps/i2/TB1.ZVyGXXXXXbMXpXX91dEGXXX-32-16.png\"]")
					.size() > 0) {
				continue;
			}

			// 商品属性，在td中
			Element a = element.select("a.item-title").get(0);
			// 标题和ID
			ItemSourceModel itemSourceModel = new ItemSourceModel();
			Map<String, String> queryParam;
			try {
				queryParam = UtilUrl.urlToMap(a.attr("href"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}

			// 销售ID
			itemSourceModel.setItemId(queryParam.get("id"));
			// 店铺标题
			itemSourceModel.setTitle(a.html().trim());
			// 店铺销售价格
			itemSourceModel.setPrice(new BigDecimal(element
					.select("span.price-now").html().trim()).setScale(2));
			itemSourceModels.add(itemSourceModel);
		}
		return itemSourceModels;
	}

	@Override
	public List<ItemSourceModel> findBlacklist() {
		return itemSourceEntityMapper.selectBlacklist();
	}

	@Override
	public ItemSourceModel get(String id) {
		return itemSourceEntityMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ItemSourceModel> findSourceBlacklist() {
		return itemSourceEntityMapper.selectSourceBlacklist();
	}

	@Override
	public void addSourceBlacklist(ItemSourceModel itemSourceModel) {
		SourceBlacklistEntityCondition sourceBlacklistEntityCondition = new SourceBlacklistEntityCondition();

		SourceBlacklistEntityCondition.Criteria criteria = sourceBlacklistEntityCondition
				.createCriteria();

		criteria.andTypeEqualTo(itemSourceModel.getType())
				.andSourceOwnerEqualTo(itemSourceModel.getSourceOwner());

		List<SourceBlacklistModel> sourceBlacklistModels = sourceBlacklistEntityMapper
				.selectByExample(sourceBlacklistEntityCondition);

		if (sourceBlacklistModels.size() > 0) {
			return;
		} else {
			SourceBlacklistModel sourceBlacklistModel = new SourceBlacklistModel();

			sourceBlacklistModel.setSourceOwner(itemSourceModel
					.getSourceOwner());
			sourceBlacklistModel.setType(itemSourceModel.getType());
			sourceBlacklistEntityMapper.insert(sourceBlacklistModel);
		}
	}
}
