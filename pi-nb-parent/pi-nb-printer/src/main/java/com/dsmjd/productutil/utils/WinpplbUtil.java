package com.dsmjd.productutil.utils;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class WinpplbUtil {
  
	public static void main(String[] args) {
		print("http://dsmzg.com/app?i=T700|888888|E5:3F:33:43:88:EC|safe|1", "E53F334388EC", "1","T700");
	}

	public static int print(String qrCode, String macStrWithSplit, String devCode,String lockType) {
	  String servicePrefix ="电话";
	  String ServicePhone = ":"+(StringUtils.isNotBlank((ConfigUtil.ServicePhone))?ConfigUtil.ServicePhone:"4008801889");
	  String guide = "产品注册使用说明二维码";
	  try {
      FileVersionUtil.increaseLockSerialNo(lockType);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
	  String versionFormat = FileVersionUtil.getCurrentReleaseNo()+String.format("%07d", FileVersionUtil.versionMap.get(lockType));
	  String lockTypePrefix ="型号";
	  if(StringUtils.isNotBlank(ConfigUtil.oem)){
	    lockType = ":"+versionFormat;
    }else{
      if("假锁".equals(lockType)){
    	ConfigUtil.xyMacY = ConfigUtil.xyMacY -10;
        lockTypePrefix = lockTypePrefix + ":"+lockType;
        lockType = "/"+versionFormat;
      }else{        
        lockType = ":"+lockType+"/"+versionFormat;
      }
    }
	  try {
			int nLen = Winpplb.API.B_GetUSBBufferLen() + 1;
			byte[] pbuf = null;
			if (nLen > 1) {
				pbuf = new byte[nLen];
				Winpplb.API.B_EnumUSB(pbuf);
				// return Convert.ToString(pbuf);
				Winpplb.API.B_CreateUSBPort(1);
			} else {
				System.out.println("USB端口开启失败");
				return 2;
				// MessageBox.Show("USB端口开启失败");
				// return 2; //2:USB端口开启失败
			}// sample setting.

			Winpplb.API.B_Set_DebugDialog(1);
			// 打印起步坐标。210,0
			Winpplb.API.B_Set_Originpoint(ConfigUtil.xyPrintX, ConfigUtil.xyPrintY);
			Winpplb.API.B_Select_Option(6);
			// 颜色深度
			Winpplb.API.B_Set_Darkness(8);
			// Winpplb.API.B_Set_DebugDialog(1);
			// Winpplb.API.B_Set_Originpoint(0, 0);
			// Winpplb.API.B_Select_Option(6);
			// Winpplb.API.B_Set_Darkness(8);
			Winpplb.API.B_Del_Pcx("*");// delete all picture.

			// draw box.
			// Winpplb.API.B_Draw_Box(20, 20, 4, 760, 560);
			// Winpplb.API.B_Draw_Line('O', 400, 20, 4, 540);

			// // 添加文字
			// Winpplb.API.B_Prn_Text(40, 0, 0, 2, 1, 1, 'N', "DESSMANN LOCK");
			// Winpplb.API.B_Prn_Text_TrueType_Uni(440, 330, 40, "Times New Roman", 1, 400, 0, 0, 0, "A9", "注册用二维码", 3);

			// // 二维码
			// Winpplb.API.B_Bar2d_QR(60, 10, 2, 10, 'M', 'A', 0, 0, 0, qrCode);
			//
			// // 添加mac
			// Winpplb.API.B_Prn_Text_TrueType_Uni(ConfigUtil.xyMacX, ConfigUtil.xyMacY, 30, "Times New Roman", 1, 400,
			// 0, 0, 0, "B1", macStrWithSplit,
			// 3);
			//
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 0, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "C1", "产", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 1, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "C2", "品", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 2, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "C3", "注", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 3, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "C4", "册", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 4, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "C5", "&", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 5, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "C6",
			// checkUserCode == null ? "" : checkUserCode.trim(), 3);
			//
			// textX += 40;
			//
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 0, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "D1", "使", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 1, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "D2", "用", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 2, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "D3", "说", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 3, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "D4", "明", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 4, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "D5", "二", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 5, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "D6", "维", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 6, 40, "Times New Roman", 1, 400, 0, 0,
			// 0, "D7", "码", 3);


			// 二维码
			int nextX = 450;
			int fontSize = 30;
			Winpplb.API.B_Bar2d_QR(25,          18, 2, 9, 'M', 'A', 0, 0, 0, qrCode);
			Winpplb.API.B_Bar2d_QR(25 + nextX, 18, 2, 9, 'M', 'A', 0, 0, 0, qrCode);
			// 添加mac
			if(null !=macStrWithSplit){			  
				for(int i=0;i<macStrWithSplit.length();i++){
					// 添加mac
				  Winpplb.API.B_Prn_Text_TrueType_Uni(360 + nextX, 48+i*25, 20, "Times New Roman", 1, 800, 0, 0, 0, "G"+i, macStrWithSplit, 3);
					Winpplb.API.B_Prn_Text_TrueType_Uni(360,         48+i*25, 20, "Times New Roman", 1, 800, 0, 0, 0, "G"+i, String.valueOf(macStrWithSplit.charAt(i)), 3);
				}
			}			
			//打印使用说明二维码
			if(null != guide){
			  for(int i=0;i<guide.length();i++){
          Winpplb.API.B_Prn_Text_TrueType_Uni(ConfigUtil.xyMacX + fontSize*i,         ConfigUtil.xyMacY + 31, fontSize+3,
              "Times New Roman", 1, 800, 0, 0, 0, "J"+(i+1), guide.charAt(i)+"", 3);
          Winpplb.API.B_Prn_Text_TrueType_Uni(ConfigUtil.xyMacX + fontSize*i + nextX, ConfigUtil.xyMacY + 31, fontSize+3,
              "Times New Roman", 1, 800, 0, 0, 0, "J"+(i+1), guide.charAt(i)+"", 3);
			  }
			}
			//打印锁具型号
      if(null !=lockType){
//        Winpplb.API.B_Prn_Text(ConfigUtil.xyMacX+(fontSize*(lockTypePrefix.length())),         ConfigUtil.xyMacY + 33, 0, 2, 1, 1, 'N', lockType);
//        Winpplb.API.B_Prn_Text(ConfigUtil.xyMacX+(fontSize*(lockTypePrefix.length())) + nextX, ConfigUtil.xyMacY + 33, 0, 2, 1, 1, 'N', lockType);
        for(int i=0;i<lockTypePrefix.length();i++){
          Winpplb.API.B_Prn_Text_TrueType_Uni(ConfigUtil.xyMacX + fontSize*i,         ConfigUtil.xyMacY-2, fontSize+3,
              "Times New Roman", 1, 800, 0, 0, 0, "O"+(i+1), lockTypePrefix.charAt(i)+"", 3);
          Winpplb.API.B_Prn_Text_TrueType_Uni(ConfigUtil.xyMacX + fontSize*i + nextX, ConfigUtil.xyMacY-2, fontSize+3,
              "Times New Roman", 1, 800, 0, 0, 0, "O"+(i+1), lockTypePrefix.charAt(i)+"", 3);
        }
      }
      //打印服务号码
/*      if(null !=ServicePhone){
        Winpplb.API.B_Prn_Text(ConfigUtil.xyMacX+(fontSize*2),        ConfigUtil.xyMacY + 71, 0, 2, 1, 1, 'N', ServicePhone);
        Winpplb.API.B_Prn_Text(ConfigUtil.xyMacX+(fontSize*2) + nextX, ConfigUtil.xyMacY + 71, 0, 2, 1, 1, 'N', ServicePhone);
        for(int i=0;i<servicePrefix.length();i++){
          Winpplb.API.B_Prn_Text_TrueType_Uni(ConfigUtil.xyMacX + fontSize * i,          ConfigUtil.xyMacY + 69, 
              fontSize, "Times New Roman", 1, 800, 0, 0, 0, "K"+(i+1), servicePrefix.charAt(i)+"", 3);
          Winpplb.API.B_Prn_Text_TrueType_Uni(ConfigUtil.xyMacX + fontSize * i + nextX,  ConfigUtil.xyMacY + 69, 
              fontSize, "Times New Roman", 1, 800, 0, 0, 0, "K"+(i+1), servicePrefix.charAt(i)+"", 3);
        }
      }*/
      
      // 打印
      Winpplb.API.B_Print_Out(ConfigUtil.qrPrintCount);// copy 2.

      // close port.
      Winpplb.API.B_ClosePrn();
      return 1;
		
			// 添加文字
			// Winpplb.API.B_Prn_Text(40, 0, 0, 2, 1, 1, 'N', "DESSMANN LOCK");
			// Winpplb.API.B_Prn_Text_TrueType_Uni(50, 330, 30, "Times New Roman", 2, 400, 0, 0, 0, "A0", "注册用二维码", 3);
			//

			// Winpplb.API.B_Bar2d_QR(400, 20, 2, 10, 'M', 'A', 0, 0, 0, qrCode);

			// 条形码
			// Winpplb.API.B_Prn_Barcode(420, 100, 0, "3", 2, 3, 100, 'B', "lock520|11111111|E5:3F:33:43:88:EC|lock");//

			// Winpplb.API.B_Prn_Barcode(420, 100, 0, "3", 2, 3, 40, 'B', "1234<+1>");// have a counter
			// Winpplb.API.B_Bar2d_QR(420, 200, 1, 3, 'M', 'A', 0, 0, 0, "QR CODE");

			// // picture.
			// Winpplb.API.B_Get_Graphic_ColorBMP(420, 280, "bb.bmp");// Color bmp file.
			// Winpplb.API.B_Get_Graphic_ColorBMPEx(420, 320, 200, 150, 2, "bb1", "bb.bmp");// 180 angle.

			// output.
			// Winpplb.API.B_Print_Out(2);// copy 2.
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
	}
	
	public static boolean isAlpha(Character c){
	  String text = c+"";
	  return text.matches("[a-zA-Z0-9\\-\\/]");
	}

	public interface Winpplb extends Library {
		Winpplb API = (Winpplb) Native.loadLibrary("Winpplb", Winpplb.class);

		int B_Bar2d_Maxi(int x, int y, int cl, int cc, int pc, String data);

		int B_Bar2d_PDF417(int x, int y, int w, int v, int s, int c, int px, int py, int r, int l, int t, int o, String data);

		int B_Bar2d_PDF417_N(int x, int y, int w, String para, String data);

		void B_ClosePrn();

		int B_CreatePrn(int selection, String filename);

		int B_Del_Form(String formname);

		int B_Del_Pcx(String pcxname);

		int B_Draw_Box(int x, int y, int thickness, int hor_dots, int ver_dots);

		int B_Draw_Line(char mode, int x, int y, int hor_dots, int ver_dots);

		int B_Error_Reporting(char option);

		int B_Get_DLL_VersionA(int nShowMessage);

		int B_Get_Graphic_ColorBMP(int x, int y, String filename);

		int B_Get_Graphic_ColorBMPEx(int x, int y, int nWidth, int nHeight, int rotate, String id_name, String filename);

		int B_Get_Pcx(int x, int y, String filename);

		int B_Initial_Setting(int Type, String Source);

		int B_WriteData(int IsImmediate, byte[] pbuf, int length);

		int B_ReadData(byte[] pbuf, int length, int dwTimeoutms);

		int B_Load_Pcx(int x, int y, String pcxname);

		int B_Open_ChineseFont(String path);

		int B_Print_Form(int labset, int copies, String form_out, String var);

		int B_Print_MCopy(int labset, int copies);

		int B_Print_Out(int labset);

		int B_Prn_Barcode(int x, int y, int ori, String type, int narrow, int width, int height, char human, String data);

		void B_Prn_Configuration();

		int B_Prn_Text(int x, int y, int ori, int font, int hor_factor, int ver_factor, char mode, String data);

		int B_Prn_Text_Chinese(int x, int y, int fonttype, String id_name, String data);

		int B_Prn_Text_TrueType_Uni(int x, int y, int FSize, String FType, int Fspin, int FWeight, int FItalic, int FUnline, int FStrikeOut,
				String id_name, String data, int format);

		int B_Prn_Text_TrueType_UniB(int x, int y, int FSize, String FType, int Fspin, int FWeight, int FItalic, int FUnline, int FStrikeOut,
				String id_name, String data, int format);

		int B_Prn_Text_TrueType(int x, int y, int FSize, String FType, int Fspin, int FWeight, int FItalic, int FUnline, int FStrikeOut,
				String id_name, String data);

		int B_Prn_Text_TrueType_W(int x, int y, int FHeight, int FWidth, String FType, int Fspin, int FWeight, int FItalic, int FUnline,
				int FStrikeOut, String id_name, String data);

		int B_Select_Option(int option);

		int B_Select_Option2(int option, int p);

		int B_Select_Symbol(int num_bit, int symbol, int country);

		int B_Select_Symbol2(int num_bit, String csymbol, int country);

		int B_Set_Backfeed(char option);

		int B_Set_BMPSave(int nSave, String strBMPFName);

		int B_Set_Darkness(int darkness);

		int B_Set_DebugDialog(int nEnable);

		int B_Set_Direction(char direction);

		int B_Set_Form(String formfile);

		int B_Set_Labgap(int lablength, int gaplength);

		int B_Set_Labwidth(int labwidth);

		int B_Set_Originpoint(int hor, int ver);

		int B_Set_Prncomport(int baud, char parity, int data, int stop);

		int B_Set_Prncomport_PC(int nBaudRate, int nByteSize, int nParity, int nStopBits, int nDsr, int nCts, int nXonXoff);

		int B_Set_Speed(int speed);

		int B_Set_ProcessDlg(int nShow);

		int B_Set_ErrorDlg(int nShow);

		int B_GetUSBBufferLen();

		int B_EnumUSB(byte[] buf);

		int B_CreateUSBPort(int nPort);

		int B_ResetPrinter();

		int B_GetPrinterResponse(byte[] buf, int nMax);

		int B_TFeedMode(int nMode);

		int B_TFeedTest();

		int B_CreatePort(int nPortType, int nPort, String filename);

		int B_Execute_Form(String form_out, String var);

		int B_Bar2d_QR(int x, int y, int model, int scl, char error, char dinput, int c, int d, int p, String data);

		int B_GetNetPrinterBufferLen();

		int B_EnumNetPrinter(byte[] buf);

		int B_CreateNetPort(int nPort);

	}

	public static int printV1(String qrCode, String macStrWithSplit, String checkUserCode) {
		try {
			int nLen = Winpplb.API.B_GetUSBBufferLen() + 1;
			byte[] pbuf = null;
			if (nLen > 1) {
				pbuf = new byte[nLen];
				Winpplb.API.B_EnumUSB(pbuf);
				// return Convert.ToString(pbuf);
				Winpplb.API.B_CreateUSBPort(1);
			} else {
				System.out.println("2");
				return 2;
				// MessageBox.Show("USB端口开启失败");
				// return 2; //2:USB端口开启失败
			}// sample setting.

			Winpplb.API.B_Set_DebugDialog(1);
			// 打印起步坐标。210,0
			Winpplb.API.B_Set_Originpoint(ConfigUtil.xyPrintX, ConfigUtil.xyPrintY);
			Winpplb.API.B_Select_Option(6);
			// 颜色深度
			Winpplb.API.B_Set_Darkness(8);
			// Winpplb.API.B_Set_DebugDialog(1);
			// Winpplb.API.B_Set_Originpoint(0, 0);
			// Winpplb.API.B_Select_Option(6);
			// Winpplb.API.B_Set_Darkness(8);
			Winpplb.API.B_Del_Pcx("*");// delete all picture.

			// draw box.
			// Winpplb.API.B_Draw_Box(20, 20, 4, 760, 560);
			// Winpplb.API.B_Draw_Line('O', 400, 20, 4, 540);

			// // 添加文字
			// Winpplb.API.B_Prn_Text(40, 0, 0, 2, 1, 1, 'N', "DESSMANN LOCK");
			// Winpplb.API.B_Prn_Text_TrueType_Uni(440, 330, 40, "Times New Roman", 1, 400, 0, 0, 0, "A9", "注册用二维码", 3);

			// 添加文字
			// Winpplb.API.B_Prn_Text(40, 0, 0, 2, 1, 1, 'N', "DESSMANN LOCK");
			Winpplb.API.B_Prn_Text_TrueType_Uni(ConfigUtil.xyMacX, ConfigUtil.xyMacY, 30, "Times New Roman", 1, 400, 0, 0, 0, "A0", macStrWithSplit,
					3);

			int fontHeight = 50;
			int textX = ConfigUtil.xyText1X;
			int textY = ConfigUtil.xyText1Y;
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 0, 40, "Times New Roman", 1, 400, 0, 0, 0, "CA", "产", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 1, 40, "Times New Roman", 1, 400, 0, 0, 0, "CB", "品", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 2, 40, "Times New Roman", 1, 400, 0, 0, 0, "CC", "注", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 3, 40, "Times New Roman", 1, 400, 0, 0, 0, "CD", "册", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 4, 40, "Times New Roman", 1, 400, 0, 0, 0, "CE", "&", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 5, 40, "Times New Roman", 1, 400, 0, 0, 0, "CF",
					checkUserCode == null ? "" : checkUserCode.trim(), 3);

			textX += 40;

			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 0, 40, "Times New Roman", 1, 400, 0, 0, 0, "DA", "使", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 1, 40, "Times New Roman", 1, 400, 0, 0, 0, "DB", "用", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 2, 40, "Times New Roman", 1, 400, 0, 0, 0, "DC", "说", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 3, 40, "Times New Roman", 1, 400, 0, 0, 0, "DD", "明", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 4, 40, "Times New Roman", 1, 400, 0, 0, 0, "DE", "二", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 5, 40, "Times New Roman", 1, 400, 0, 0, 0, "DF", "维", 3);
			Winpplb.API.B_Prn_Text_TrueType_Uni(textX, textY + fontHeight * 6, 40, "Times New Roman", 1, 400, 0, 0, 0, "D0", "码", 3);

			// Winpplb.API.B_Prn_Text_TrueType_Uni(420, 60, 50, "Times New Roman", 1, 400, 0, 0, 0, "BA", "质", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(420, 100, 50, "Times New Roman", 1, 400, 0, 0, 0, "BB", "检", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(420, 140, 50, "Times New Roman", 1, 400, 0, 0, 0, "BC", "员", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(420, 180, 50, "Times New Roman", 1, 400, 0, 0, 0, "BD", "编", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(420, 220, 50, "Times New Roman", 1, 400, 0, 0, 0, "BE", "号", 3);
			// Winpplb.API.B_Prn_Text_TrueType_Uni(420, 260, 50, "Times New Roman", 1, 400, 0, 0, 0, "BF", "13", 3);

			// 二维码
			Winpplb.API.B_Bar2d_QR(0, 20, 2, 10, 'M', 'A', 0, 0, 0, qrCode);

			// 添加文字
			// Winpplb.API.B_Prn_Text(40, 0, 0, 2, 1, 1, 'N', "DESSMANN LOCK");
			// Winpplb.API.B_Prn_Text_TrueType_Uni(50, 330, 30, "Times New Roman", 2, 400, 0, 0, 0, "A0", "注册用二维码", 3);
			//

			// Winpplb.API.B_Bar2d_QR(400, 20, 2, 10, 'M', 'A', 0, 0, 0, qrCode);

			// 条形码
			// Winpplb.API.B_Prn_Barcode(420, 100, 0, "3", 2, 3, 100, 'B', "lock520|11111111|E5:3F:33:43:88:EC|lock");//

			// Winpplb.API.B_Prn_Barcode(420, 100, 0, "3", 2, 3, 40, 'B', "1234<+1>");// have a counter
			// Winpplb.API.B_Bar2d_QR(420, 200, 1, 3, 'M', 'A', 0, 0, 0, "QR CODE");

			// // picture.
			// Winpplb.API.B_Get_Graphic_ColorBMP(420, 280, "bb.bmp");// Color bmp file.
			// Winpplb.API.B_Get_Graphic_ColorBMPEx(420, 320, 200, 150, 2, "bb1", "bb.bmp");// 180 angle.

			// output.
			// Winpplb.API.B_Print_Out(2);// copy 2.

			// 打印
			Winpplb.API.B_Print_Out(ConfigUtil.qrPrintCount);// copy 2.

			// close port.
			Winpplb.API.B_ClosePrn();
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
	}
}
