package com.dsmjd.productutil.utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;


/**
 * @description 此类实现了SWT和AWT图像的互相转换
 * @author chenmfa refer online
 * @version 创建时间：2017年7月12日 上午9:32:26 
 * @description
 */
public class ImageConvertor {
  
  public static BufferedImage toBufferedImage(Image image) {
    if (image instanceof BufferedImage) {
        return (BufferedImage)image;
     }
 
    // This code ensures that all the pixels in the image are loaded
     image = new ImageIcon(image).getImage();
 
    // Determine if the image has transparent pixels; for this method's
    // implementation, see e661 Determining If an Image Has Transparent Pixels
    //boolean hasAlpha = hasAlpha(image);
 
    // Create a buffered image with a format that's compatible with the screen
     BufferedImage bimage = null;
     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    try {
        // Determine the type of transparency of the new buffered image
        int transparency = Transparency.OPAQUE;
       /* if (hasAlpha) {
         transparency = Transparency.BITMASK;
         }*/
 
        // Create the buffered image
         GraphicsDevice gs = ge.getDefaultScreenDevice();
         GraphicsConfiguration gc = gs.getDefaultConfiguration();
         bimage = gc.createCompatibleImage(
         image.getWidth(null), image.getHeight(null), transparency);
     } catch (HeadlessException e) {
        // The system does not have a screen
     }
 
    if (bimage == null) {
        // Create a buffered image using the default color model
        int type = BufferedImage.TYPE_INT_RGB;
        //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
        /*if (hasAlpha) {
         type = BufferedImage.TYPE_INT_ARGB;
         }*/
         bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
     }
     //将image拷贝到BufferedImage
     Graphics g = bimage.createGraphics();
 
    // Paint the image onto the buffered image
     g.drawImage(image, 0, 0, null);
     g.dispose();
 
    return bimage;
  }

   /**
    * SWT转换成AWT
    * @param org
    *            .eclipse.swt.graphics.ImageData;
    * @return java.awt.image.BufferedImage;
    */
   public static BufferedImage convertToAWT(ImageData data) {
    ColorModel colorModel = null;
    PaletteData palette = data.palette;
    if (palette.isDirect) {
     colorModel = new DirectColorModel(data.depth, palette.redMask,
       palette.greenMask, palette.blueMask);
     BufferedImage bufferedImage = new BufferedImage(colorModel,
       colorModel.createCompatibleWritableRaster(data.width,
         data.height), false, null);
     WritableRaster raster = bufferedImage.getRaster();
     int[] pixelArray = new int[3];
     for (int y = 0; y < data.height; y++) {
      for (int x = 0; x < data.width; x++) {
       int pixel = data.getPixel(x, y);
       RGB rgb = palette.getRGB(pixel);
       pixelArray[0] = rgb.red;
       pixelArray[1] = rgb.green;
       pixelArray[2] = rgb.blue;
       raster.setPixels(x, y, 1, 1, pixelArray);
      }
     }
     return bufferedImage;
    } else {
     RGB[] rgbs = palette.getRGBs();
     byte[] red = new byte[rgbs.length];
     byte[] green = new byte[rgbs.length];
     byte[] blue = new byte[rgbs.length];
     for (int i = 0; i < rgbs.length; i++) {
      RGB rgb = rgbs[i];
      red[i] = (byte) rgb.red;
      green[i] = (byte) rgb.green;
      blue[i] = (byte) rgb.blue;
     }
     if (data.transparentPixel != -1) {
      colorModel = new IndexColorModel(data.depth, rgbs.length, red,
        green, blue, data.transparentPixel);
     } else {
      colorModel = new IndexColorModel(data.depth, rgbs.length, red,
        green, blue);
     }
     BufferedImage bufferedImage = new BufferedImage(colorModel,
       colorModel.createCompatibleWritableRaster(data.width,
         data.height), false, null);
     WritableRaster raster = bufferedImage.getRaster();
     int[] pixelArray = new int[1];
     for (int y = 0; y < data.height; y++) {
      for (int x = 0; x < data.width; x++) {
       int pixel = data.getPixel(x, y);
       pixelArray[0] = pixel;
       raster.setPixel(x, y, pixelArray);
      }
     }
     return bufferedImage;
    }
   }
   
   
   public static org.eclipse.swt.graphics.Image convertToSWTImage(BufferedImage bufferedImage) {
     return new org.eclipse.swt.graphics.Image(PlatformUI.getWorkbench().getDisplay(), convertToSWT(bufferedImage));
   }
   
   public static org.eclipse.swt.graphics.Image convertToSWTImage(Image image,Display display) {
     BufferedImage bufferedImage = toBufferedImage(image);
     return new org.eclipse.swt.graphics.Image(display, convertToSWT(bufferedImage));
   }

   /**
    * AWT转换成SWT
    * @param java
    *            .awt.image.BufferedImage;
    * @return org.eclipse.swt.graphics.ImageData;
    */
   public static ImageData convertToSWT(BufferedImage bufferedImage) {
    if (bufferedImage.getColorModel() instanceof DirectColorModel) {
     DirectColorModel colorModel = (DirectColorModel) bufferedImage
       .getColorModel();
     PaletteData palette = new PaletteData(colorModel.getRedMask(),
       colorModel.getGreenMask(), colorModel.getBlueMask());
     ImageData data = new ImageData(bufferedImage.getWidth(),
       bufferedImage.getHeight(), colorModel.getPixelSize(),
       palette);
     WritableRaster raster = bufferedImage.getRaster();
     int[] pixelArray = new int[3];
     for (int y = 0; y < data.height; y++) {
      for (int x = 0; x < data.width; x++) {
       raster.getPixel(x, y, pixelArray);
       int pixel = palette.getPixel(new RGB(pixelArray[0],
         pixelArray[1], pixelArray[2]));
       data.setPixel(x, y, pixel);
      }
     }
     return data;
    } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
     IndexColorModel colorModel = (IndexColorModel) bufferedImage
       .getColorModel();
     int size = colorModel.getMapSize();
     byte[] reds = new byte[size];
     byte[] greens = new byte[size];
     byte[] blues = new byte[size];
     colorModel.getReds(reds);
     colorModel.getGreens(greens);
     colorModel.getBlues(blues);
     RGB[] rgbs = new RGB[size];
     for (int i = 0; i < rgbs.length; i++) {
      rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF,
        blues[i] & 0xFF);
     }
     PaletteData palette = new PaletteData(rgbs);
     ImageData data = new ImageData(bufferedImage.getWidth(),
       bufferedImage.getHeight(), colorModel.getPixelSize(),
       palette);
     data.transparentPixel = colorModel.getTransparentPixel();
     WritableRaster raster = bufferedImage.getRaster();
     int[] pixelArray = new int[1];
     for (int y = 0; y < data.height; y++) {
      for (int x = 0; x < data.width; x++) {
       raster.getPixel(x, y, pixelArray);
       data.setPixel(x, y, pixelArray[0]);
      }
     }
     return data;
    }
    return null;
   }
   
   /**
    * 根据指定的宽高对{@link Image}图像进行绽放
    * @param src 原图对象
    * @param width 目标图像宽度
    * @param height 目标图像高度
    * @return 返回缩放后的{@link Image}对象
    */
   public static org.eclipse.swt.graphics.Image resize(org.eclipse.swt.graphics.Image src, int width, int height) {
     org.eclipse.swt.graphics.Image scaled = new org.eclipse.swt.graphics.Image(Display.getDefault(), width, height);
     GC gc = new GC(scaled);
     try{
         gc.setAdvanced(true); // 打开高级绘图模式
         gc.setAntialias(SWT.ON);// 设置消除锯齿
         gc.setInterpolation(SWT.HIGH); // 设置插值
         gc.drawImage(src, 0, 0, src.getBounds().width, src.getBounds().height,0, 0, width, height);
     }finally{
         gc.dispose();
     }
     return scaled;
   }
   /**
    * 根据缩放比例对{@link Image}对象进行缩放
    * @param src 原图对象
    * @param zoom 缩放比例
    * @return 返回缩放后的{@link Image}对象
    */
   public static org.eclipse.swt.graphics.Image resize(org.eclipse.swt.graphics.Image src, float zoom) {
     Rectangle bounds = src.getBounds();
     bounds.width*=zoom;
     bounds.height*=zoom;
     return resize(src,bounds.width,bounds.height);
   }
}
