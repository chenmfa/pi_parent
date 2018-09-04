package com.dsmjd.productutil.dto;

import java.awt.image.BufferedImage;
import jp.sourceforge.qrcode.data.QRCodeImage;
public class BufferedQrCode implements QRCodeImage {
  BufferedImage bufferedImage;
  public BufferedQrCode (BufferedImage bufferedImage){
    this.bufferedImage = bufferedImage;
  } 
  @Override
  public int getHeight(){
    return bufferedImage.getHeight ();
  }
  @Override public int getPixel(int arg0, int arg1){
    return bufferedImage.getRGB (arg0, arg1);
  }
  @Override public int getWidth() {
    return bufferedImage.getWidth ();
  }
 }