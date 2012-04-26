package com.message.base.utils;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 图片处理工具类(需要整理)
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-17 上午03:06:14
 */
public class ImageUtils {
  
	/**
	 * 按宽的比例更改图片的大小
	 * 
	 * @param filePath 			图片路径
	 * @param width				需要改变图片的宽度
	 * @param destPath			目标路径
	 * @return
	 * @throws Exception
	 */
	public static File getRatioWidth(String filePath, int width, String destPath) throws Exception {
		File f = new File(filePath);
		BufferedImage bi = ImageIO.read(f);
		double wRatio = (new Integer(width)).doubleValue() / bi.getWidth(); // 宽度的比例
		int height = (int) (wRatio * bi.getHeight()); // 图片转换后的高度
		Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH); // 设置图像的缩放大小
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio, wRatio), null); // 设置图像的缩放比例
		image = op.filter(bi, null);
		File zoomFile = new File(destPath);
		File file = null;
		try {
			ImageIO.write((BufferedImage) image, "jpg", zoomFile);
			file = new File(zoomFile.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * 按高的比例更改图片大小
	 * 
	 * @param filePath		图片路径
	 * @param height		需要改变图片的高度
	 * @param destPath		目标路径
	 * @return
	 * @throws Exception
	 */
	public static File getRatioHeight(String filePath, int height, String destPath) throws Exception {
		File f = new File(filePath);
		BufferedImage bi = ImageIO.read(f);
		double hRatio = (new Integer(height)).doubleValue() / bi.getHeight(); // 高度的比例
		int width = (int) (hRatio * bi.getWidth()); // 图片转换后的高度
		Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH); // 设置图像的缩放大小
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(hRatio, hRatio), null); // 设置图像的缩放比例
		image = op.filter(bi, null);
		File zoomFile = new File(destPath);
		File file = null;
		try {
			ImageIO.write((BufferedImage) image, "jpg", zoomFile);
			file = new File(zoomFile.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}
  
	/**
	 * 按输入的任意宽高改变图片的大小
	 * 
	 * @param filePath		图片路径
	 * @param width			需要改变图片的宽度
	 * @param height		需要改变图片的高度
	 * @param destPath		目标路径
	 * @return
	 * @throws Exception
	 */
	public static File getFixedIcon(String filePath, int width, int height, String destPath) throws Exception {
		File f = new File(filePath);
		BufferedImage bi = ImageIO.read(f);
		double wRatio = (new Integer(width)).doubleValue() / bi.getWidth(); // 宽度的比例
		double hRatio = (new Integer(height)).doubleValue() / bi.getHeight(); // 高度的比例
		Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH); // 设置图像的缩放大小
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(wRatio, hRatio), null); // 设置图像的缩放比例
		image = op.filter(bi, null);
		File zoomFile = new File(destPath);
		File file = null;
		try {
			ImageIO.write((BufferedImage) image, "jpg", zoomFile);
			file = new File(zoomFile.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}
	
	/**
	 * 根据图像实际宽高和给定宽高，通过比例计算应该得到图像的宽高
	 * 
	 * @param width			实际图像宽度
	 * @param height		实际图像高度
	 * @param destWidth		给定宽度
	 * @param destHeight	给定高度
	 * @return				new int[]{应得图像宽度, 应得图像高度}
	 */
	public static int[] getSizeByPercent(int width, int height, int destWidth, int destHeight){
		/**
		 * width/height = destWidth/destHeight
		 * width	100		destWidth	720	-->270		240	-->	360(error)	240
		 * height	200		destHeight	540	-->540		720	-->	720			480
		 */
		double percent = Double.valueOf(width).doubleValue() / Double.valueOf(height).doubleValue();		//-->0.5
		double destPercent = Double.valueOf(destWidth).doubleValue() / Double.valueOf(destHeight).doubleValue();
		int w = 0;
		int h = 0;
		if(percent > destPercent){
			w = destWidth;
			h = (int) (destWidth / destPercent);
		} else {
			w = (int) (destHeight * percent);
			h = destHeight;
		}
		
		return new int[]{w, h};
	}
  
}  