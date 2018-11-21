package com.ideassoft.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;

import javax.imageio.ImageIO;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

public class BarUtil {
	private final static String imagePath = getWorkDir() + File.separator + "images";

	public static final String getWorkDir() {
		ClassLoader loader = BarUtil.class.getClassLoader();
		if (loader == null) {
			loader = Thread.currentThread().getContextClassLoader();
		}
		URL souce = loader.getResource("");
		if (souce != null) {
			File file = new File(souce.getPath());
			return file.getParentFile().getParentFile().getPath();
		}
		return null;
	}

	public static void generateBarCode(String[] source, Integer scale) {
		try {
			delOldFile(source[0].substring(0, 4));
			JBarcode barcode = new JBarcode(Code128Encoder.getInstance(),
					WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
			BufferedImage image;
			for (String str : source) {
				image = barcode.createBarcode(str);
				saveImage(image, str + ".png", scale);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static void saveImage(BufferedImage bufferedImage, String paramString, Integer scale) {
		saveToFile(bufferedImage, paramString, "png", scale);
	}

	static void saveToFile(BufferedImage paramBufferedImage, String paramString1, 
			String paramString2, Integer scale) {
		FileOutputStream fos = null;
		try {
			String imgUrl = URLDecoder.decode(imagePath + File.separator + paramString1, "utf-8");
			File image = new File(imgUrl);
			if (!image.exists()) {
				fos = new FileOutputStream(image);
				ImageUtil.encodeAndWrite(paramBufferedImage, paramString2, fos, 96, 96);
				scale(imgUrl, imgUrl, scale);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static void delOldFile(final String filterName) {
		File images = new File(imagePath);
		if (images.isDirectory()) {
			File[] olds = images.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					File old = new File(dir + File.separator + name);
					return name.startsWith(filterName) 
						&& System.currentTimeMillis() - old.lastModified() > 86400000;
				}
			});
			if (olds != null && olds.length > 0) {
				for (File old : olds) {
					old.delete();
				}
			}
		}
	}

	static void scale(String imgUrl, String resultImgUrl, int scale)
			throws IOException {
		BufferedImage src = ImageIO.read(new File(imgUrl));
		int width = src.getWidth();
		int height = src.getHeight();
		width = width * scale;
		height = height * scale;
		Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		ImageIO.write(tag, "PNG", new File(resultImgUrl));
	}

	public static void generateBarCode(String source, OutputStream sos)
			throws Exception {
		JBarcode barcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), 
				BaseLineTextPainter.getInstance());
		barcode.setShowText(false);
		barcode.setXDimension(50);
		BufferedImage image = barcode.createBarcode(source);
		ImageIO.write(image, "png", sos);
		sos.close();
	}

	public static void main(String[] args) {
		generateBarCode(new String[] { "33011000000308", "33011000000309" }, 2);
	}

}