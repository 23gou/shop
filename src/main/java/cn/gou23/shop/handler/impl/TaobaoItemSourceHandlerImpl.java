package cn.gou23.shop.handler.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gou23.cgodo.util.UtilLog;
import cn.gou23.cgodo.util.UtilUrl;
import cn.gou23.shop.constant.SourceType;
import cn.gou23.shop.handler.ItemSourceHandler;
import cn.gou23.shop.model.ItemSourceModel;
import cn.gou23.shop.service.ItemSourceService;
import cn.gou23.shop.taobaoapi.ItemApi;
import cn.gou23.shop.taobaoapi.SkuApi;
import cn.gou23.shop.taobaoapi.SkuApi.Sku;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 
 * 描述:爱淘宝货源处理器
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月17日 下午5:38:12
 */
@Component
public class TaobaoItemSourceHandlerImpl implements ItemSourceHandler {
	@Autowired
	private SkuApi skuApi;
	@Autowired
	private ItemApi itemApi;
	@Autowired
	private ItemSourceService ItemSourceService;

	@Override
	public String parseSourceId(String url) {
		try {
			return UtilUrl.urlToMap(url, "utf-8").get("id");
		} catch (UnsupportedEncodingException e) {
			UtilLog.debug(url + "无法获取ID", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 淘宝客的信息解析，价格，售价，利润等等
	 *
	 * @see cn.gou23.shop.parse.ItemSourceParse#parseInfo(cn.gou23.shop.model.ItemSourceModel)
	 */
	@Override
	public boolean parseInfo(ItemSourceModel itemSourceModel, String content) {
		ObjectMapper objectMapper = new ObjectMapper();

		// 去阿里妈妈读取商品详情
		try {
			Map<String, Object> result = objectMapper
					.readValue(
							content.replace("<HTML>", "")
									.replace("</HTML>", "")
									.replace("<HEAD>", "")
									.replace("<BODY>", "")
									.replace("</HEAD>", "")
									.replace("</BODY>", "")
									.replace("<html>", "")
									.replace("</html>", "")
									.replace("<head>", "")
									.replace("<body>", "")
									.replace("</head>", "")
									.replace("</body>", "")
									.replace(
											"<html xmlns=\"http://www.w3.org/1999/xhtml\">",
											"").replace("<pre>", "")
									.replace("</pre>", "")
									.replace("</html>", ""), Map.class);
			// 返回的结果
			Integer length = Integer.valueOf(PropertyUtils.getProperty(result,
					"data.paginator.length").toString());

			if (length == 0) {
				itemSourceModel.setProfit(BigDecimal.ZERO);
				itemSourceModel.setPurchasePrice(BigDecimal.ZERO);
				itemSourceModel.setPurchaseDiscountPrice(BigDecimal.ZERO);
				return false;
			} else {
				// 返回到额数据集
				List<Map<String, Object>> pagelist = (List<Map<String, Object>>) PropertyUtils
						.getProperty(result, "data.pagelist");
				// 当前ID的那个
				Map<String, Object> sourceInfo = pagelist.get(0);
				// 进货价
				BigDecimal zkPrice = new BigDecimal(sourceInfo.get("zkPrice")
						.toString());
				// 佣金
				BigDecimal calCommission = new BigDecimal(sourceInfo.get(
						"calCommission").toString());
				itemSourceModel.setPurchasePrice(zkPrice);
				itemSourceModel.setPurchaseDiscountPrice(calCommission);
				itemSourceModel.setImg(sourceInfo.get("pictUrl").toString());
				itemSourceModel.setType(SourceType.淘宝联盟);
				itemSourceModel.setTitle(sourceInfo.get("title").toString());
				itemSourceModel.setSourceOwner(sourceInfo.get("userNumberId")
						.toString());

				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 描述:同步爱淘宝最新的SKU数据
	 * 
	 * @param shopId
	 * @param sessionKey
	 * @param updatePrice
	 * @return
	 * @author liyixing 2015年8月26日 下午3:38:39
	 */
	@Override
	public String syncItemSourceSku(List<ItemSourceModel> itemSourceModels,
			String sessionKey) {
		// 成功
		int success = 0;
		// 失败
		int failure = 0;

		for (ItemSourceModel itemSourceModel : itemSourceModels) {
			try {
				// 读取店铺本身的sku
				List<Sku> skuOfMy = skuApi.getSkuProperties(Long
						.valueOf(itemSourceModel.getItemId()));
				// SKU组合，按照属性排列，如aaa:bbb;ccc:ddd是一个SKU;
				Map<String, Sku> skuByProperties = new HashMap<String, Sku>();
				// 标题
				// SKU组合，按照属性排列，如aaa:bbb;ccc:ddd是一个SKU;
				Map<String, Sku> sourceSkuByProperties = new HashMap<String, Sku>();
				// 读取淘宝客的sku
				List<Sku> skuOfSource = skuApi.getSkuProperties(Long
						.valueOf(itemSourceModel.getSourceId()));

				for (Sku sku : skuOfMy) {
					skuByProperties.put(sku.getProperties(), sku);
				}

				// 由于淘宝客可能存在别名情况，因此需要把本地的SKU ID和淘宝客的SKU ID 做一次矫正
				if (skuOfMy.size() > 0) {
					Sku mySku = skuOfMy.get(0);

					for (Sku skuOfAtb : skuOfSource) {
						String psString = "";
						// 20509:587044975;1627207:28338
						String[] ps = skuOfAtb.getProperties().split(";");
						boolean first = true;

						for (String p : ps) {
							// 女装厚薄 常规款，淘宝助手对该属性无法导入
							if (p.equals("-1:-1") || p.equals("-1:-2")
									|| p.equals("-1:-3")) {
								continue;
							}
							String[] z = p.split(":");

							// pid
							Map<String, String> mySkuId = mySku
									.getSkuPropsByTitle().get(z[0]);
							// 8个汉字，16个字符
							int titleLength = 0;
							String title = skuOfAtb.getPropertiesTitleByMap()
									.get(p);

							String newTitle = "";
							// 超过的丢弃
							for (char c : title.toCharArray()) {
								int ci = (int) c;
								if (ci <= 127) {
									titleLength += 1;
								} else {
									titleLength += 2;
								}

								if (titleLength <= 16) {
									newTitle += c;
								} else {
									break;
								}
							}

							title = newTitle;
							// "咖啡色~最后一小".equals(title)
							String newId = mySkuId.get(title);

							if (newId == null) {
								newId = z[1];
							}

							if (!first) {
								psString = psString + ";";
							}

							psString = psString + z[0] + ":" + newId;

							first = false;
						}

						skuOfAtb.setProperties(psString);
					}
				}

				// 重新生成
				for (Sku sku : skuOfSource) {
					sourceSkuByProperties.put(sku.getProperties(), sku);
					sku.setNumId(Long.valueOf(itemSourceModel.getItemId()));

					if (skuByProperties.get(sku.getProperties()) == null) {
						// 无效的属性
						boolean isInvalidPro = false;
						// 有些商家会设置一些无效的，用来做营销的SKU，比如尺码叫做 亏本冲量 数量不多的
						// String psString = "";
						// 20509:587044975;1627207:28338
						String[] ps = sku.getProperties().split(";");
						// boolean first = true;
						//
						for (String p : ps) {
							// String[] z = p.split(":");
							String title = sku.getPropertiesTitleByMap().get(p);

							if ("亏本冲量 数量不多".equals(title)) {
								isInvalidPro = true;
								break;
							}
						}
						// 属性有效
						if (!isInvalidPro) {
							// 增加原始的，价格从第一个复制而来
							if (skuOfMy.size() > 0) {
								sku.setPrice(skuOfMy.get(0).getPrice());
							}
							skuApi.add(sku, sessionKey);
						}
					} else {
						// 修改
						skuApi.update(sku, sessionKey, false);
					}
				}

				// 修改标题
				// 获取对方的SKU属性描述
				if (skuOfSource.size() > 0) {
					skuApi.updateSkuAlias(skuOfSource.get(0), sessionKey);
				}

				for (Sku sku : skuOfMy) {
					if (sourceSkuByProperties.get(sku.getProperties()) == null) {
						// 删除原始的
						skuApi.deleteSku(sku, sessionKey);
					}
				}

				success++;
			} catch (Exception e) {
				UtilLog.error("同步SKU失败", e);
				failure++;
			}
		}

		return "成功" + success + "失败" + failure;
	}

	@Override
	public void syncTotalSoldQuantity(List<ItemSourceModel> itemSourceModels) {
		// 成功
		// int success = 0;
		// 失败
		// int failure = 0;

		for (ItemSourceModel itemSourceModel : itemSourceModels) {
			if (StringUtils.isNotBlank(itemSourceModel.getItemId())) {
				itemSourceModel.setMyTotalSoldQuantity(itemApi
						.getTotalSoldQuantity(itemSourceModel.getItemId()));
			}

			if (StringUtils.isNotBlank(itemSourceModel.getSourceId())) {
				itemSourceModel.setSourceTotalSoldQuantity(itemApi
						.getTotalSoldQuantity(itemSourceModel.getSourceId()));
			}
			ItemSourceService.saveSource(itemSourceModel);
		}

		// return "成功" + success + "失败" + failure;
	}

	@Override
	public Date syncLastTradingTime(ItemSourceModel itemSourceModel,
			String content) {
		return null;
	}
}
