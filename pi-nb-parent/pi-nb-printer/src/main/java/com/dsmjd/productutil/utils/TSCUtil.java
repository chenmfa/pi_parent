package com.dsmjd.productutil.utils;

import java.io.UnsupportedEncodingException;

import javax.print.PrintServiceLookup;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public class TSCUtil {
  public interface TscLibDll extends StdCallLibrary {  
    TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary("TSCLIB", TscLibDll.class);  

    int about();  

    int openport(String pirnterName);  

    int closeport();  

    int sendcommand(String printerCommand);  

    int setup(String width, String height, String speed, String density, String sensor, String vertical, String offset);  

    int downloadpcx(String filename, String image_name);  

    int barcode(String x, String y, String type, String height, String readable, String rotation, String narrow, String wide, String code);  

    int printerfont(String x, String y, String fonttype, String rotation, String xmul, String ymul, String text);  

    int clearbuffer();  

    int printlabel(String set, String copy);  

    int formfeed();  

    int nobackfeed();  

    int windowsfont(int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, String content);  
  }
  
  /**
   * 绘制二维码指令
   * 功能：繪製QRCODE二維條碼
   * 語法：
   * QRCODE X, Y, ECC Level, cell width, mode, rotation, [model, mask,]"Data string”
   * 參數說明
   * X QRCODE條碼左上角X座標
   * Y QRCODE條碼左上角Y座標
   * ECC level 錯誤糾正能力等級
   * L 7%
   * M 15%
   * Q 25%
   * H 30%
   * cell width    1~10
   * mode  自動生成編碼/手動生成編碼
   * A Auto
   * M Manual
   * rotation  順時針旋轉角度
   * 0 不旋轉
   * 90    順時針旋轉90度
   * 180   順時針旋轉180度
   * 270   順時針旋轉270度
   * model 條碼生成樣式
   * 1 (預設), 原始版本
   * 2 擴大版本
   * mask  範圍：0~8，預設7
   * Data string   條碼資料內容
   * Author MaBingYang
   * @date Dec 30, 2013 12:42:48 PM
   * @param barCode
   * @return
   */
  public static String CMD_QRCODE_FROMT_GP3150TN(String barCode){
     StringBuffer sb = new StringBuffer("QRCODE");
     sb.append(" ");
     sb.append(0 +",");//X QRCODE條碼左上角X座標//45
     sb.append(0 +",");//Y QRCODE條碼左上角Y座標//55
     sb.append("H,");//ECC level 錯誤糾正能力等級 L 7% M 15% Q 25% H 30%
     sb.append(""+ConfigUtil.cellWidth+",");//cell width    1~10
     sb.append("A,");//mode  自動生成編碼/手動生成編碼 A Auto M Manual
     sb.append("0,");//rotation  順時針旋轉角度 0 不旋轉 90    順時針旋轉90度 180   順時針旋轉180度 270   順時針旋轉270度
     sb.append("1,");//model 條碼生成樣式 1 (預設), 原始版本 2 擴大版本
     sb.append("6,");//mask  範圍：0~8，預設7
     sb.append("\"");
     sb.append(barCode);//Data string   二维码內容
     sb.append("\"");
     return sb.toString();
   }
  /**
   * 调用GP-3150TN打印二维码
   * Author MaBingYang
   * @date Dec 30, 2013 11:58:29 AM
   * @param barCode 二维码内容
   */
  public static void Print_Qrcode(String barCode,String spcode){

  }
  
  public static void print(String qrcode,String spcode) throws UnsupportedEncodingException {  
    System.setProperty("jna.encoding", "GBK");// 支持中文  
    TscLibDll.INSTANCE.openport(PrintServiceLookup.lookupDefaultPrintService().getName());//打开打印机端口 
    TscLibDll.INSTANCE.setup("50", "30", "3", "10", "0", "0", "0");
    TscLibDll.INSTANCE.sendcommand("SET TEAR ON");  //是否将撕纸位置移动到撕纸处
    TscLibDll.INSTANCE.clearbuffer();  // 清除缓冲信息
  
    TscLibDll.INSTANCE.sendcommand("GAP "+ConfigUtil.gap+" mm,0");// 设置 打印的方向与纸的垂直间距
    TscLibDll.INSTANCE.sendcommand("DIRECTION "+ConfigUtil.printDirection);// 设置 打印的方向.
//    String command = "QRCODE 100,10,H,7,M,0,M1,S1, "+ qrcode;// 打印二维码  
    TscLibDll.INSTANCE.sendcommand(CMD_QRCODE_FROMT_GP3150TN(qrcode));  
    TscLibDll.INSTANCE.windowsfont(220, ConfigUtil.text_startY, 24, 90, 0, 0, "宋体", spcode);
    TscLibDll.INSTANCE.printlabel(String.valueOf(ConfigUtil.qrPrintCount), "1");  
    TscLibDll.INSTANCE.clearbuffer();  
    TscLibDll.INSTANCE.closeport();
  }  
  public static void main(String[] args) throws UnsupportedEncodingException {
    print("http://iot.pihot.com/lock?d="+"18d6d5f3c00db4".toUpperCase()+"&IMEI=865352031693426", "18d6d5f3c00db4".toUpperCase());
    byte[] readBuffer = new byte[]{0x55,0x55,0x55,0x67,0x59,0x72,(byte) 0xee,(byte) 0xe0,0x35,0x39,0x37,0x32,0x45,0x45,0x45,0x30,(byte) 0xd5,(byte) 0xee,(byte) 0xee};
    byte[] macArr = new byte[6];  
    System.arraycopy(readBuffer, 2, macArr, 0, 6);
    byte[] devCodeArr = new byte[8];
        System.arraycopy(readBuffer, 8, devCodeArr, 0, 8);
//    String macTemp = ByteUtil.byteToHexString(macArr, ":");
//    String deviceMAc = macTemp.substring(0, macTemp.length()-1);
//    String devCode = "1901"+new String(devCodeArr,"UTF-8");
//    String macStr = deviceMAc.replaceAll(":", "-");
//    String macStrWithOutSplit = ByteUtil.byteToHexNoSpace(macArr);
  }
}
