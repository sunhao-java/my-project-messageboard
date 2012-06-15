package com.message.watermark;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-5-12 上午02:56:26
 */
public class WaterMarkDemo {
	// 为了例子简单，暂时用固定的文件名。
	private static final String FILEINPUT = "F:/workspace/workspace/messageboard/messageboard/02_Implementation/" +
			"assemblage/src/test/java/com/message/watermark/source.JPG";
	private static final String FILEMARK = "F:/workspace/workspace/messageboard/messageboard/02_Implementation/" +
			"assemblage/src/test/java/com/message/watermark/mark.png";
	private static final String FILEDEST1 = "文字水印效果.jpg";
	private static final String FILEDEST2 = "图像水印效果.jpg";

	/**
	 * 给图片添加文字水印
	 * 
	 * @param filePath 			需要添加水印的图片的路径
	 * @param markContent 		水印的文字
	 * @param markContentColor 	水印文字的颜色
	 * @param fontSize			字体大小
	 * @param location			位置(1、左上角；2、右上角；3、右下角；4、左下角；5、中间)
	 * @return 布尔类型
	 * @throws Exception 
	 */
	public boolean createStringMark(String filePath, String markContent,
			Color markContentColor, int fontSize, int location) throws Exception {
		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null);
		int height = theImg.getHeight(null);
		BufferedImage bimage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.setColor(markContentColor);
		g.setBackground(Color.white);
		g.drawImage(theImg, 0, 0, null);
		g.setFont(new Font("楷体", Font.PLAIN, fontSize)); // 字体、字型、字号
		
		int length = 0;
		char[] tmp = markContent.toCharArray();
		String en = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ`~!@#$%^&*()-_+=[]{};:'\",.<>?0123456789";
		String flag_cn = "·～！￥×（）——『』【】；：‘“，。？《》";
		for(char t : tmp){
			if (StringUtils.contains(en, t)) {
				length += fontSize / 2;
			} else if (StringUtils.contains(flag_cn, t)) {
				length += fontSize;
			} else {
				length += fontSize;
			}
		}
		length += 10;
		
		int w,h;
		switch(location){
			case 1: 
				w = 5;
				h = fontSize;
				break;
			case 2:
				w = width - length;
				h = fontSize;
				break;
			case 3:
				w = width - length;
				h = height - 10;
				break;
			case 4:
				w = 5;
				h = height - 10;
				break;
			case 5:
				w = (width - length) / 2;
				h = (height - fontSize) / 2;
				break;
			default:
				throw new Exception("没有位置！");
		}
		g.drawString(markContent, w, h); // 画文字
		g.dispose();
		try {
			FileOutputStream out = new FileOutputStream(FILEDEST1); // 先用一个特定的输出文件名
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(7, true);
			encoder.encode(bimage, param);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 给图片添加图像水印
	 * 
	 * @param filePath 		需要添加水印的图片的路径
	 * @param markPath		水印图片路径
	 * @param location		位置(1、左上角；2、右上角；3、右下角；4、左下角；5、中间)
	 * @return 布尔类型
	 * @throws Exception 
	 */
	public boolean createWaterMarkDemo(String filePath, String markPath, int location) throws Exception {
		// 要处理的原始图片
		ImageIcon icoInput = new ImageIcon(filePath);
		Image imgInput = icoInput.getImage();
		int width = imgInput.getWidth(null);
		int height = imgInput.getHeight(null);
		BufferedImage buffInput = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 要添加上来的水印
		ImageIcon icoADD = new ImageIcon(markPath);
		Image imgADD = icoADD.getImage();
		int w = imgADD.getWidth(null);
		int h = imgADD.getHeight(null);
		// 绘图
		Graphics2D g = buffInput.createGraphics();
		g.drawImage(imgInput, 0, 0, null);
		
		int x,y;
		switch(location){
			case 1: 
				x = 10;
				y = 10;
				break;
			case 2:
				x = width - w - 10;
				y = 10;
				break;
			case 3:
				x = width - w - 10;
				y = height - h - 10;
				break;
			case 4:
				x = 10;
				y = height - h - 10;
				break;
			case 5:
				x = (width - w) / 2;
				y = (height - h) / 2;
				break;
			default:
				throw new Exception("没有位置！");
		}
		// 下面代码的前面五个参数：图片，x坐标，y坐标,图片宽度,图片高度
		g.drawImage(imgADD, x, y, w, h, null);
		
		g.dispose();
		try {
			FileOutputStream out = new FileOutputStream(FILEDEST2);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder
					.getDefaultJPEGEncodeParam(buffInput);
			param.setQuality(7, true);
			encoder.encode(buffInput, param);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void main(String args[]) throws Exception {
		WaterMarkDemo wmObj = new WaterMarkDemo();
		// 文字水印
		if (wmObj.createStringMark(FILEINPUT, "孙昊的水印,【】12gaga", Color.red, 16, 3) == true)
			System.out.println("生成文字水印成功。请查看当前目录下的" + FILEDEST1);
		else
			System.out.println("生成文字水印失败！");
		// 图像水印
		if (wmObj.createWaterMarkDemo(FILEINPUT, FILEMARK, 3) == true)
			System.out.println("生成图像水印成功。请查看当前目录下的" + FILEDEST2);
		else
			System.out.println("生成图像水印失败！");
		// 结束程序
		System.exit(0);
	}
}
