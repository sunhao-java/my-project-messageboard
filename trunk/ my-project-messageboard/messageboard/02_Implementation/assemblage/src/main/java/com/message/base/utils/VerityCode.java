package com.message.base.utils;

import com.message.resource.ResourceType;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 绘制图形验证码的servlet
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-21 下午8:56
 */
public class VerityCode extends HttpServlet{

    private static String key = ResourceType.VERITY_CODE_KEY; // 默认的session key

    private static Color getRandomColor(int fontColor, int backColor){
        Random random = new Random();
        if(fontColor > 255)
            fontColor = 255;
        if(backColor > 255)
            backColor = 255;

        int red = fontColor + random.nextInt(backColor - fontColor);
        int green = fontColor + random.nextInt(backColor - fontColor);
        int blue = fontColor + random.nextInt(backColor - fontColor);

        return new Color(red, green, blue);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //内存中创建图像
        int width = 60, height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //获取图像上下文
        Graphics graphics = image.getGraphics();

        //随机类
        Random random = new Random();

        //设置背景色
        graphics.setColor(getRandomColor(200, 250));
        graphics.fillRect(0, 0, width, height);

        //设置字体
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        //随机产生干扰线
        graphics.setColor(getRandomColor(160, 200));
        for(int i = 0; i < 155; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        // 取随机产生的认证码(4位数字)
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            // 将认证码显示到图象中
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成

            graphics.drawString(rand, 13 * i + 6, 16);
        }

        // 图象生效
        graphics.dispose();

        // 将认证码存入SESSION
        request.getSession().setAttribute(key, sRand);

        // 输出图象到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    public void destroy() {
    }
}
