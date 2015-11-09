package cn.gou23.shop.taobaoapi;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gou23.cgodo.util.UtilHtml;
import cn.gou23.cgodo.util.UtilLog;

import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemUpdateDelistingRequest;
import com.taobao.api.request.ItemUpdateRequest;
import com.taobao.api.response.ItemUpdateDelistingResponse;
import com.taobao.api.response.ItemUpdateResponse;

/**
 * 
 * 
 * 描述:商品API
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月17日 下午12:32:44
 */
@Component
public class ItemApi {
	@Autowired
	private TaobaoClient taobaoClient;

	/**
	 * 
	 * 描述:下架
	 * 
	 * @param numIid
	 * @author liyixing 2015年8月17日 下午12:33:23
	 * @throws Exception
	 */
	public void delisting(Long numIid, String sessionKey) throws Exception {
		ItemUpdateDelistingRequest req = new ItemUpdateDelistingRequest();
		req.setNumIid(numIid);
		ItemUpdateDelistingResponse response = taobaoClient.execute(req,
				sessionKey);

		// 确保在对方设置过

		if (response.getErrorCode() != null) {
			throw new RuntimeException(response.getMsg());
		}
	}

	/**
	 * 
	 * 描述:修改标题
	 * 
	 * @param numIid
	 * @author liyixing 2015年8月17日 下午12:33:23
	 * @throws Exception
	 */
	public void updateTitle(Long numIid, String title, String sessionKey)
			throws Exception {
		ItemUpdateRequest req = new ItemUpdateRequest();
		req.setNumIid(numIid);
		req.setTitle(title);
		ItemUpdateResponse response = taobaoClient.execute(req, sessionKey);

		// 确保在对方设置过

		if (response.getErrorCode() != null) {
			if ("该商品已被删除".equals(response.getSubMsg())) {
				// 下架

			} else {
				throw new RuntimeException(response.getMsg());
			}
		}
	}

	/**
	 * 
	 * 描述:获取总销量
	 * 
	 * @param id
	 * @return
	 * @author liyixing 2015年10月30日 下午3:26:11
	 */
	public Long getTotalSoldQuantity(String id) {
		String content = null;
		try {
			content = UtilHtml
					.requestHttp("http://hws.m.taobao.com/cache/wdetail/5.0/?id="
							+ id);
			ObjectMapper objectMapper = new ObjectMapper();

			/**
			 * 解析结果
			 */
			Object ret = objectMapper.readValue(content, Map.class);
			// api内部
			List<Object> apiStack = (List<Object>) PropertyUtils.getProperty(
					ret, "data.apiStack");
			// 内部堆栈第一次解析出来是字符串，需要再次解析一次
			Map<String, Object> apiStackVal = objectMapper.readValue(
					((Map<String, Object>) apiStack.get(0)).get("value")
							.toString(), Map.class);

			return Long.valueOf(PropertyUtils.getProperty(apiStackVal,
					"data.itemInfoModel.totalSoldQuantity").toString());
		} catch (Exception e) {
			UtilLog.error("处理itemid{}时失败，", e, id);
			throw new RuntimeException(e);
		}
	}
}
