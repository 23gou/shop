package cn.gou23.shop.view;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Display;

import cn.gou23.cgodo.util.UtilLog;

/**
 * 
 * 
 * 描述:我的事件处理监听器，支持浏览器完成事件判断机制，由每个监听器自己实现判断机制 <br>
 * 每个人都可以实现自己的判断，比如可以通过js 来判断某个元素是否创建出来 document.getElementById('id')!=null
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年11月3日 下午4:58:15
 */
public abstract class MyProgressListener implements ProgressListener {
	/**
	 * 
	 * 描述:实际加载完成事件
	 * 
	 * @param event
	 * @param browser
	 * @author liyixing 2015年11月3日 下午5:03:50
	 */
	public abstract void realCompleted(ProgressEvent event, Browser browser);

	@Override
	public void completed(final ProgressEvent event) {
		final Browser browser = (Browser) event.getSource();
		final ProgressListener myProgressListener = this;

		if (!isCompleted(event, browser)) {
			Display.getDefault().timerExec((int) 500, new Runnable() {
				public void run() {
					UtilLog.debug("当前浏览器URL{}，还未完全加载完毕，等待500秒后继续",
							browser.getUrl());
					myProgressListener.completed(event);
				}
			});
		} else {
			UtilLog.debug("当前浏览器URL{}，完全加载完毕，开始真正的事件处理", browser.getUrl());
			realCompleted(event, browser);
		}
	}

	/**
	 * 
	 * 描述:是否已经完成
	 * 
	 * @param event
	 * @param browser
	 * @return
	 * @author liyixing 2015年11月3日 下午5:01:44
	 */
	public boolean isCompleted(ProgressEvent event, Browser browser) {
		return true;
	}
}
