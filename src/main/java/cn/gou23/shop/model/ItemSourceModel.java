package cn.gou23.shop.model;

import java.math.BigDecimal;

import cn.gou23.cgodo.util.UtilLog;
import cn.gou23.shop.entity.ItemSourceEntity;

/**
 * 
 * 
 * 描述:商品来源信息
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月17日 下午4:30:58
 */
public class ItemSourceModel extends ItemSourceEntity {
	/**
	 * 最低折扣
	 */
	private static final BigDecimal MIN_PROFIT = BigDecimal.valueOf(30);

	/**
	 * 最低进货折扣价
	 */
	private static final BigDecimal MIN_PURCHASE_DISCOUNT_PRICE = BigDecimal
			.valueOf(40);
	/**
	 * 描述：
	 */

	private static final long serialVersionUID = 1L;

	public BigDecimal getPrice() {
		return super.getPrice() == null ? BigDecimal.ZERO : super.getPrice();
	}

	/**
	 * 利润
	 *
	 * @mbggenerated do_not_delete_during_merge
	 */
	public BigDecimal getProfit() {
		// 进货价减去进货折扣，减去销售价
		return getPrice().subtract(
				getPurchasePrice().subtract(getPurchaseDiscountPrice()));
	}

	/**
	 * 
	 * 描述:是否需要下架
	 * 
	 * 
	 * @return true需要下架
	 * @author liyixing 2015年8月26日 下午4:37:59
	 */
	public boolean needOff() {
		BigDecimal profit = getProfit();

		// 利润最低25
		if (profit.compareTo(MIN_PROFIT) < 0) {
			UtilLog.debug("利润过低，需要下架，利润{}，商品ID{}，URL{}", profit, getItemId(),
					getSourceDetailUrl());
			return true;
		}

		if (getPurchaseDiscountPrice().compareTo(MIN_PURCHASE_DISCOUNT_PRICE) < 0) {
			UtilLog.debug("进货折扣过低，需要下架，折扣（返利）{}，商品ID{}，URL{}",
					getPurchaseDiscountPrice(), getItemId(),
					getSourceDetailUrl());
			return true;
		}
		return false;
	}

	public BigDecimal countDefaultPrice() {
		// 计算默认销售价
		// 店铺销售价相对进货价的折扣价未进货折扣价的一半
		BigDecimal myDiscountPrice = getPurchaseDiscountPrice().divide(
				BigDecimal.valueOf(2)).setScale(2, BigDecimal.ROUND_DOWN);

		// 不够最低利润
		// 进货价-进货折扣+最低利润
		if (myDiscountPrice.compareTo(MIN_PROFIT) < 0) {
			return getPurchasePrice().subtract(getPurchaseDiscountPrice()).add(
					MIN_PROFIT);
		} else {
			return getPurchasePrice().subtract(myDiscountPrice);
		}
	}
}
