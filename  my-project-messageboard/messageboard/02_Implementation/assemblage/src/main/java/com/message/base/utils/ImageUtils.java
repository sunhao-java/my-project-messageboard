package com.message.base.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

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
		int w = 0;
		int h = 0;
		/**
		 * 目标图像宽与源图像宽的比例
		 * 目标图像高与源图像高的比例
		 */
		double wPercent = (double) destWidth / width;
		double hPercent = (double) destHeight / height;
		
		if (wPercent > hPercent) {
			wPercent = hPercent;
			w = (int) (wPercent * width);
			h = destHeight;
		} else {
			hPercent = wPercent;
			w = destWidth;
			h = (int) (hPercent * height);
		}
		
		return new int[]{w, h};
	}

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
    public static boolean addStringMark(String filePath, String markContent, Color markContentColor, int fontSize,
                                    int location) throws Exception {
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
            if (org.apache.commons.lang.StringUtils.contains(en, t)) {
                length += fontSize / 2;
            } else if (org.apache.commons.lang.StringUtils.contains(flag_cn, t)) {
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
            FileOutputStream out = new FileOutputStream(filePath); // 先用一个特定的输出文件名
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
    public static boolean addImageMark(String filePath, String markPath, int location) throws Exception {
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
            FileOutputStream out = new FileOutputStream(filePath);
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
  
}  