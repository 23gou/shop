package cn.gou23.shop.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.browser.Browser;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 
 * 
 * 描述:页面解析
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年9月9日 下午6:38:01
 */
public class UtilBrowser {
	/**
	 * 
	 * 描述:解析html的数据
	 * 
	 * @param content
	 * @param select
	 * @return
	 * @author liyixing 2015年9月9日 下午6:39:01
	 */
	public static final Elements parse(Document document, String select) {
		// Document document = Jsoup.parse(content);

		return document.select(select);
	}

	/**
	 * 
	 * 描述:解析html的数据
	 * 
	 * @param content
	 * @param select
	 * @return
	 * @author liyixing 2015年9月9日 下午6:39:01
	 */
	public static final String parseHtml(Document document, String select) {
		Elements elements = parse(document, select);

		return elements.html();
	}

	/**
	 * 
	 * 描述:解析html的数据
	 * 
	 * @param content
	 * @param select
	 * @return
	 * @author liyixing 2015年9月9日 下午6:39:01
	 */
	public static final String parseVal(Document document, String select) {
		Elements elements = parse(document, select);

		return elements.val();
	}

	/**
	 * 
	 * 描述:获取指定元素数据，已正则方式
	 * 
	 * @param text
	 * @author liyixing 2015年9月11日 上午10:02:13
	 * @return
	 */
	public static final List<String> parseTextWithPattern(String text,
			String rep) {
		Pattern pattern = Pattern.compile(rep);
		Matcher matcher = pattern.matcher(text);
		List<String> results = new ArrayList<String>();

		while (matcher.find()) {
			String txt = matcher.group(1);
			results.add(txt);
		}

		return results;
	}

	/**
	 * 
	 * 描述:获取指定元素数据，已正则方式
	 * 
	 * @param text
	 * @author liyixing 2015年9月11日 上午10:02:13
	 * @return
	 */
	public static final String parseTextWithPatternHtml(String text, String rep) {
		Pattern pattern = Pattern.compile(rep);
		Matcher matcher = pattern.matcher(text);
		String result = "";

		while (matcher.find() && matcher.groupCount() > 0) {
			String txt = matcher.group(1);

			return txt;
		}
		
		return result;
	}

	/**
	 * 
	 * 描述:添加链接跳转的元素，并跳转
	 * 
	 * @return
	 * @author liyixing 2015年9月11日 下午3:56:28
	 */
	public static final String toUrl(Browser browser, String href) {
		// browser.evaluate("return document.getElementsByTagName('body')[0].innerHTML");
		String id = "myA" + (new Date()).getTime();
		// String script =
		// "function invokeClick(element) { if(element.click){element.click();alert('click');}    else if(element.fireEvent){element.fireEvent('onclick');alert('fireEvent');}    else if(document.createEvent){alert('createEvent');    var evt = document.createEvent(\"MouseEvents\");    evt.initEvent(\"click\", true, true);    element.dispatchEvent(evt);    }}"
		// + "var a = document.getElementsByTagName('body')[0];"
		// + "var b = document.createElement('a');b.href='"
		// + href
		// + "';  b.id='"
		// + id
		// +
		// "';a.appendChild(b);var c = document.createElement('input');c.type='button';b.appendChild(c);invokeClick(c);";

		String script = "location.href='" + href + "'";

		browser.execute(script);
		return id;

	}

	/**
	 * 
	 * 描述:添加链接跳转的元素，并跳转
	 * 
	 * @return
	 * @author liyixing 2015年9月11日 下午3:56:28
	 */
	public static final void toUrl(Browser browser, String href, String from) {
		browser.setUrl(href, null, new String[] { "referer:" + from });
	}
}
