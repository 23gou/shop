package cn.gou23.shop.model;

import java.math.BigDecimal;
import java.util.Date;

import cn.gou23.cgodo.util.UtilDateTime;
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
			.valueOf(50);

	/**
	 * 交易记录，最高间隔
	 */
	private static final int MAX_LAST_NNOTICE_DAY = 7;
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

		if (UtilDateTime.getDifferenceDay(new Date(), getLastNoticeDay()) > MAX_LAST_NNOTICE_DAY) {
			UtilLog.debug("最后交易时间{}，超过{}，没有交易，非货源商品，自动下架，商品ID{}，URL{}",
					getLastNoticeDay(), MAX_LAST_NNOTICE_DAY, getItemId(),
					getSourceDetailUrl());
			return true;
		}
		
		return false;
	}

	/**
	 * 
	 * 描述:默认价格
	 * 
	 * @return
	 * @author liyixing 2015年10月30日 下午3:52:26
	 */
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

	/**
	 * 本店铺销量
	 *
	 * @mbggenerated do_not_delete_during_merge
	 */
	public Long getMyTotalSoldQuantity() {
		return super.getMyTotalSoldQuantity() == null ? 0l : super
				.getMyTotalSoldQuantity();
	}

	/**
	 * 货源销量
	 *
	 * @mbggenerated do_not_delete_during_merge
	 */
	public Long getSourceTotalSoldQuantity() {
		return super.getSourceTotalSoldQuantity() == null ? 0l : super
				.getSourceTotalSoldQuantity();
	}

	/**
	 * 最后交易日
	 *
	 * @mbggenerated do_not_delete_during_merge
	 */
	public Date getLastNoticeDay() {
		return super.getLastNoticeDay() == null ? UtilDateTime.addDay(
				new Date(), -100) : super.getLastNoticeDay();
	}
}
