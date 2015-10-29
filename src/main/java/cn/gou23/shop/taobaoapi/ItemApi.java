package cn.gou23.shop.taobaoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
			throw new RuntimeException(response.getMsg());
		}
	}
}
