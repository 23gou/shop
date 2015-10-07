package cn.gou23.shop.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

/**
 * 
 * 
 * 描述:图片工具类
 *
 * @author liyixing
 * @version 1.0
 * @since 2015年8月18日 下午9:52:44
 */
public class UtilImage {
	private static final String PATH = System.getProperty("user.home")
			+ "/image/cache/";
	
	static {
		File path = new File(PATH);
		
		if(!path.exists()) {
			path.mkdirs();
		}
	}

	/**
	 * 
	 * 描述:图片大小转化
	 * 
	 * @param srcImgPath
	 * @param distImgPath
	 * @param width
	 * @param height
	 * @throws IOException
	 * @author liyixing 2015年8月18日 下午9:53:05
	 */
	public static ImageData resizeImage(String srcImgPath, int width, int height)
			throws IOException {
		// 图片是否缓存过
		String cacheName = PATH + srcImgPath.hashCode()+".jpg";
		File cacheFile = new File(cacheName);

		if (cacheFile.exists()) {
			ImageData imageData = new ImageData(cacheName);

			imageData = imageData.scaledTo(width, height);
			return imageData;
		} else {
			ImageLoader loader = new ImageLoader();
			URL url = new URL(srcImgPath);
			ImageData imageData = loader.load(url.openStream())[0];
			imageData = imageData.scaledTo(width, height);
			cacheFile.createNewFile();
			loader.save(cacheName, SWT.IMAGE_JPEG);
			return imageData;
		}
	}
}
