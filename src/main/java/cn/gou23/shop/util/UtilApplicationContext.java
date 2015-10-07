package cn.gou23.shop.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 
 * 描述:上下文Util
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月17日 下午5:10:52
 */
public class UtilApplicationContext {
	private static ApplicationContext applicationContext;

	public static void load() {
		applicationContext = new ClassPathXmlApplicationContext(
				"/cn/gou23/shop/resource/spring/application/applicationContext.xml");
	}

	public static final <T> T get(Class<T> requiredType) {
		return (T) applicationContext.getBean(requiredType);
	}
	
	public static final <T> T get(String name) {
		return (T) applicationContext.getBean(name);
	}
}
