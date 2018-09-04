package com.dsmjd.productutil.gui;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dsmjd.productutil.dto.PuSysUser;
import com.dsmjd.productutil.utils.ConfigUtil;
import com.dsmjd.productutil.utils.FileUtil;
import com.dsmjd.productutil.utils.FileVersionUtil;
import com.dsmjd.productutil.utils.ImageConvertor;
import com.dsmjd.productutil.utils.QrCodeUtil;
import com.dsmjd.productutil.utils.TSCUtil;
import com.dsmjd.productutil.utils.WeChatQrUtil;
import com.dsmzg.base.util.bytes.ByteUtil;
import com.dsmzg.base.util.http.HttpPostUtil;
import com.dsmzg.base.util.imageio.qrutil.ZXingQrcode;
import com.dsmzg.base.util.lang.ArrayUtil;
import com.dsmzg.base.util.lang.IntegerUtil;
import com.dsmzg.base.util.lang.StringUtil;

public class MainWindow extends ApplicationWindow {
  
  private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
  byte[] readBuffer = new byte[24];
	public static int paramsHight = 50;
	public static int otherHight = 40;
	public static int paramsWidht = 100;
	public static int length = 6;
	String lastMac =""; //上次打印的mac地址

	public static boolean comPortIsOpened = false;

	private Text macText;

	public static String macStr = "";
	public static String macStrWithOutSplit = "";
	public static String devCode="";
	Combo combo;
	Combo cb;
	Button btnmac;
	Button openComBtn;
	Label qrCodeLabel;
	Label msgLabel;
	Label comListLabel;//串口的label页面
	Combo comListCombo;//串口选项框
	Combo comListLock;  //锁类型
	Button createQrCodeBtn;
	Button printQrCodeBtn;
	Button manPrintQrcode;//手工打印的按钮
	private SerialPort serialPort;
	private OutputStream outputStream;
	private InputStream inputStream;

	PuSysUser puSysUser = new PuSysUser();
	private StatusLineManager statusLineManager;
	private Text qrCodeText;
	private Text manmadeMac;
	private Text manmadeDevId;
	/**
	 * Create the application window.
	 * 
	 * @wbp.parser.constructor
	 */
	public MainWindow() {
		super(null);
    try {
      FileVersionUtil.initializeVersionMap();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "读取年度生产二维码数目错误", "启动出错", JOptionPane.WARNING_MESSAGE);
      System.exit(0);
      return;
    }
		setShellStyle(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create the application window.
	 */
	public MainWindow(PuSysUser puSysUser) {
		super(null);
		try {
      FileVersionUtil.initializeVersionMap();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "读取年度生产二维码数目错误", "启动出错", JOptionPane.WARNING_MESSAGE);
      System.exit(0);
      return;
    }
		if(StringUtils.isBlank(puSysUser.getName())){
			puSysUser.setName("1");
		}
		this.puSysUser = puSysUser;
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Control createContents(Composite parent) {
		setStatus("欢迎你，" + this.puSysUser.getName());
		Composite container = new Composite(parent, SWT.NONE);

		int j = 0;

		Label lblMac = new Label(container, SWT.NONE);
		lblMac.setAlignment(SWT.RIGHT);
		lblMac.setBounds(15, (j + 1) * paramsHight + 30 + 5, paramsWidht, 17);
		lblMac.setText("Mac地址：");

		macText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		macText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		macText.setBounds(115, (j + 1) * paramsHight + 30, (length - 4) * paramsWidht + 88, 35);

		btnmac = new Button(container, SWT.NONE);
		btnmac.setBounds((length - 1) * paramsWidht + 15, (j + 1) * paramsHight + 30, 88, 35);
		btnmac.setText("获取mac");
		btnmac.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (outputStream == null) {
						outputStream = serialPort.getOutputStream();
					}
//					outputStream.write(new byte[] { (byte) 0xfe, (byte) 0x2, (byte) 0xf1, (byte) 0x0, (byte) 0x6, (byte) 0x63, (byte) 0x41,
//							(byte) 0x0a, (byte) 0xfc, (byte) 0xb8, (byte) 0xdb, (byte) 0x4, (byte) 0xe6 });
//					outputStream.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnmac.setEnabled(false);
		btnmac.setVisible(false);
		
		openComBtn = new Button(container, SWT.NONE);
		openComBtn.setBounds((length - 2) * paramsWidht + 15, (j + 1) * paramsHight + 30, 88, 35);
		openComBtn.setText("打开串口");
		openComBtn.forceFocus();
		openComBtn.addSelectionListener(new SelectionAdapter() {
			private Enumeration portList;
			private CommPortIdentifier portId;

			@Override
			public void widgetSelected(SelectionEvent e) {
				lastMac="";
//				byte[] bbq = ByteUtil.hexToByte("55 55 02 01 06 10 FF 18 EE AC 52 0B 34 AD 00 03 13 08 78 67 C4 49 03 09 50 49 37 42 33 35 43 46 32 FD EE EE".replaceAll(" ", ""));
//				handleBuffer(bbq);
				if (comPortIsOpened) {
					openComBtn.setText("打开串口");
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							macText.setText("ComOUT");
							qrCodeText.setText("ComOUT");
							openComBtn.setEnabled(true);
							btnmac.setEnabled(false);
							printQrCodeBtn.setEnabled(false);

							showMsgOnLabel("串口已关闭，使用请重新打开。");
							macStr = "";
							macStrWithOutSplit = "";
							qrCodeLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/" + ConfigUtil.dsmLogoImage));
						};
					});
					serialPort.close();
					outputStream = null;
					inputStream = null;
					comPortIsOpened = false;
				} else {
					System.out.println("in.");
					portList = CommPortIdentifier.getPortIdentifiers();
					if (portList.hasMoreElements()) {
						// 如果找到了串口
					  showMsgOnLabel("串口接口调用成功");
					} else {
					  showMsgOnLabel("没有找到可用的串口");
					}
					while (portList.hasMoreElements()) {
						portId = (CommPortIdentifier) portList.nextElement();
						if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
							String selectedCom = comListCombo.getText();
							if (portId.getName().equals(selectedCom)) { // 端口名 
								try {
									serialPort = (SerialPort) portId.open("ReadComm", 10000);
									serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
									showMsgOnLabel("已打开串口 , 正在接收数据.....");
									openComBtn.setText("关闭串口");
									btnmac.setEnabled(true);

									btnmac.forceFocus();
									comPortIsOpened = true;
									serialPort.addEventListener(new SerialPortEventListener() {

										@Override
										public void serialEvent(SerialPortEvent event) {
											try {
												inputStream = serialPort.getInputStream();
											} catch (Exception e) {
												e.printStackTrace();
											}
											try {
												while (inputStream.available() > 0) {
												  byte[] period = new byte[inputStream.available()];
												  inputStream.read(period,0,period.length);
												  readBuffer = getAvailableByte(readBuffer, period);
												}
												handleBuffer(readBuffer);
											} catch (Exception e) {
											  logger.error("出错",e);
												Display.getDefault().syncExec(new Runnable() {
													public void run() {
														macText.setText("ComOUT");
														qrCodeText.setText("ComOUT");
														openComBtn.setEnabled(true);
														btnmac.setEnabled(false);
														printQrCodeBtn.setEnabled(false);

														msgLabel.setText("串口已经拔出，请重新插入串口后打开");

														openComBtn.setText("打开串口");
														qrCodeLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/"
																+ ConfigUtil.dsmLogoImage));
													};
												});
												serialPort.close();
												outputStream = null;
												inputStream = null;
												comPortIsOpened = false;
												macStr = "";
												macStrWithOutSplit = "";
											}
										}
									});

									serialPort.notifyOnDataAvailable(true);

								} catch (Exception e5) {
									e5.printStackTrace();
								}
							}
						}
					}
				}
				// readThread = new Thread(this);
				// readThread.start(); // 线程负责每接收一次数据休眠20秒钟
			}
		});
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.RIGHT);
		lblNewLabel_1.setBounds(15, (j + 2) * paramsHight + 30 + 5, paramsWidht, 17);
		lblNewLabel_1.setText("二维码字符串：");
		
    Label lblNewLabel_2 = new Label(container, SWT.NONE);
    lblNewLabel_2.setAlignment(SWT.RIGHT);
    lblNewLabel_2.setBounds(15, (j + 3) * paramsHight + 30 + 10, paramsWidht, 17);
    lblNewLabel_2.setText("人工IMEI：");

    Label lblManMadeDevId = new Label(container, SWT.NONE);
    lblManMadeDevId.setAlignment(SWT.RIGHT);
    lblManMadeDevId.setBounds(265, (j + 3) * paramsHight + 30 + 10, paramsWidht, 17);
    lblManMadeDevId.setText("人工设备序列号：");
    
		qrCodeText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
		qrCodeText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		// qrCodeText.setText("http://dsmjd.com/?x=lock520|11111111|E5:3F:33:43:88:EC|lock");
		qrCodeText.setBounds(115, (j + 2) * paramsHight + 30, (length - 4) * paramsWidht + 220, 35);
		
		manmadeMac = new Text(container, SWT.BORDER);
		manmadeMac.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		manmadeMac.setBounds(115, (j + 3) * paramsHight + 30, paramsWidht + 50, 35);
		
		manmadeDevId = new Text(container, SWT.BORDER);
		manmadeDevId.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		manmadeDevId.setBounds(364, (j + 3) * paramsHight + 30, paramsWidht + 50, 35);

		printQrCodeBtn = new Button(container, SWT.NONE);
		printQrCodeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			  print(devCode, macText.getText());
        qrCodeText.setText("");
        macText.setText("");
			}
		});
		printQrCodeBtn.setBounds((length - 1) * paramsWidht + 15, (j + 1) * paramsHight + 30, 88, 35);
		printQrCodeBtn.setText("打印二维码");
		printQrCodeBtn.setEnabled(false);
		//手工打印的二维码
		manPrintQrcode = new Button(container, SWT.NONE);
		manPrintQrcode.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        String writtenMAC = manmadeMac.getText().toUpperCase();
        String writtenDevCode = manmadeDevId.getText().toUpperCase();
        macText.setText(writtenMAC);
        qrCodeText.setText(String.format("http://iot.pihot.com/lock?d=%s&IMEI=%s", writtenDevCode, writtenMAC));
        print(writtenDevCode, writtenMAC);
      }
    });
    manPrintQrcode.setBounds((length - 1) * paramsWidht + 20, (j + 3) * paramsHight + 30, paramsWidht+3, 35);
    manPrintQrcode.setText("手工打印二维码");
    manPrintQrcode.setEnabled(true);

		msgLabel = new Label(container, SWT.NONE);
		msgLabel.setBounds(1 * paramsWidht + 15, (j + 4) * paramsHight + 30, (length - 4) * paramsWidht + 88, 35);

		qrCodeLabel = new Label(container, SWT.NONE);
		qrCodeLabel.setImage(SWTResourceManager.getImage(MainWindow.class, "/" + ConfigUtil.dsmLogoImage));
		qrCodeLabel.setBounds((length * paramsWidht + 30 - 210) / 2, (j + 5) * paramsHight, 210, 210);

		//串口Label 文字的初始化
		comListLabel = new Label(container, SWT.NONE);
		comListLabel.setAlignment(SWT.RIGHT);
		comListLabel.setBounds((length - 2) * paramsWidht + 15, (j) * paramsHight + 30, paramsWidht, 17);
		comListLabel.setText("可用串口：");
    
		comListCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		comListCombo.setBounds((length - 1) * paramsWidht + 15, (j) * paramsHight + 30, 88, 35);
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		if (portList.hasMoreElements()) {
			// 如果找到了串口
			msgLabel.setText("串口接口调用成功");
		} else {		  
			msgLabel.setText("未连接需要打印的设备,请手工打印");
		}
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				comListCombo.add(portId.getName());
				comListCombo.setText(portId.getName());
			}
		}
		//添加普通锁与假锁的下拉框的标签
		Label lbListLock = new Label(container, SWT.NONE);
		lbListLock.setAlignment(SWT.RIGHT);
		lbListLock.setBounds(15, (j) * paramsHight + 30 + 5, paramsWidht, 17);
		lbListLock.setText("二维码类型：");
					
		//添加普通锁与假锁的选项
		comListLock = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		comListLock.setBounds((length - 5) * paramsWidht + 15, (j) * paramsHight + 30, paramsWidht, 35);
		comListLock.add("小程序二维码", 0);
		comListLock.select(0);
		
		comListLock.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent event) {
				String text = comListLock.getText();
        printQrCodeBtn.setEnabled(false);
        openComBtn.setEnabled(true);
        qrCodeText.setText("");
			}

			@Override
      public void widgetDefaultSelected(SelectionEvent event) {
      }
		});
		
		getShell().setSize(length * paramsWidht + 30, (j + 11) * paramsHight);
		Rectangle area = Display.getDefault().getClientArea();
		getShell().setLocation((area.width - (length * paramsWidht + 30)) / 2, (area.height - ((j + 8) * paramsHight + 30)) / 2);
//    byte[] by1 = ByteUtil.hexToByte("55 55 02 01 06 10 FF 18 EE AC 52 0B 34 AD 00 03 13 08", true);
//    byte[] by2 = ByteUtil.hexToByte("78 67 C4 49 03 09 50 49 37 42 33 35 43 46 32 FD EE EE 55 55 02 01 06 10 FF 18 EE AC 52 0B",true);
//    byte[] availableByte = getAvailableByte(by1, by2);
//    handleBuffer(availableByte);
		return container;
	}
	//设备id和设备imei
	public void print(String deviceCode,String imei){
    String lockType = comListLock.getText();
    if(null != imei){
      imei =  imei.trim();
    }
    if(null == imei || imei.trim().isEmpty()){
      showMsgOnLabel("设备imei为空, 不能打印");
      return;
    }else if(null == deviceCode || deviceCode.trim().isEmpty()){
      showMsgOnLabel("设备编号为空, 不能打印");
      return;
    }else if(lastMac.equals(deviceCode)){
      showMsgOnLabel(String.format("该设备编号的锁已经打印(%s)，勿需重复", deviceCode));
      return;
    }

      // QrCodeUtil.createQrCode(qrCodeText.getText(), "D:/xxxx.png", 200, 200, "D:/1.png");
      // qrCodeLabel.setImage(SWTResourceManager.getImage("D:/xxxx.png"));
      try {            
          
        ZXingQrcode qroceZxing = new ZXingQrcode();
        logger.info("二维码内容：{}, 设备编号： {}, 文件路径：{},设备mac： {}",
            qrCodeText.getText(), deviceCode.toUpperCase(), "", imei);
        File qrfile = new File("qrcode\\" + deviceCode + ".png");
        if(!qrfile.getParentFile().exists()){
          qrfile.getParentFile().mkdirs();
        }
        qroceZxing.writeToFile(qrCodeText.getText(), deviceCode.toUpperCase(), qrfile, "png");
        showImageOnLabel(qrCodeLabel, qrfile.getAbsolutePath());
        TSCUtil.print(qrCodeText.getText(), deviceCode);
        logger.info("打印内容已提交" + qrCodeText.getText());
//          int x = WinpplbUtil.print(qrcode, deviceMac.replaceAll(":", ""), deviceCode,comListLock.getText());
        lastMac = deviceCode;
        macStrWithOutSplit = "";
        devCode ="";
      } catch (Exception ex ) {
        logger.error("打印二维码失败",ex);
        showMsgOnLabel("打印二维码失败");
      }
  }
	
	public void print2(String deviceCode,String deviceMac){
	  String lockType = comListLock.getText();
	  if(null != deviceMac){
	    deviceMac =  deviceMac.trim();
	  }
    if(null == deviceMac || deviceMac.trim().isEmpty()){
      showMsgOnLabel("设备MAC为空, 不能打印");
      return;
    }else if(null == deviceCode || deviceCode.trim().isEmpty()){
      showMsgOnLabel("设备编号为空, 不能打印");
      return;
    }else if((deviceMac.replaceAll(":", "").length() !=12 
        || !deviceMac.matches("([a-zA-Z0-9]{2}(:|$)){6}")) && !"假模块".equals(lockType)){
      showMsgOnLabel(String.format("MAC地址不对(%s)，请重新连接以获取数据",macStr));
      return ;
    }else if(lastMac.equals(deviceMac.replaceAll(":", ""))){
      showMsgOnLabel(String.format("该MAC地址的锁已经打印(%s)，勿需重复",macStr));
      return;
    }

    if (deviceMac != null && !deviceMac.isEmpty() 
        && (deviceMac.matches("([a-zA-Z0-9]{2}(:|$)){6}") || qrCodeText.getText().indexOf("假模块")>0)) {
      // QrCodeUtil.createQrCode(qrCodeText.getText(), "D:/xxxx.png", 200, 200, "D:/1.png");
      // qrCodeLabel.setImage(SWTResourceManager.getImage("D:/xxxx.png"));
      String qrcode;
      //获取微信二维码的ACCESS_TOKEN
      Map<String, Object> tokenParam = new HashMap<String, Object>();
      tokenParam.put("pid", 2);
      String ACCESS_TOKEN;
      try {
        String ACCESS_TOKEN_JSON = HttpPostUtil.postByHttpClient(ConfigUtil.tokenUrl, tokenParam);
        JSONObject json = JSONObject.parseObject(ACCESS_TOKEN_JSON);
        if(null == json || json.getString("value") == null){
          showMsgOnLabel("未获取到微信的访问权限,请联系管理员");
        }else{              
          ACCESS_TOKEN =  json.getString("value");
          Map<String,Object> urlparamMap = new HashMap<String, Object>();
          urlparamMap.put("path", WeChatQrUtil.generatePath(deviceMac, deviceCode));
          urlparamMap.put("width", 430);
          InputStream in = HttpPostUtil.postViaHttpClient(ConfigUtil.urlsBeforeMac+ACCESS_TOKEN, urlparamMap,null,"JSON");
          String wechatQrContent = HttpPostUtil.postByHttpClient(ConfigUtil.urlsBeforeMac+ACCESS_TOKEN, urlparamMap,"JSON");
          byte[] buf = wechatQrContent.getBytes("UTF-8");
          String parentFolder = getProjectPath() + File.separator + "qrcode" + File.separator;
          String fileName = deviceCode + "_" + deviceMac.replaceAll(":", "");
          String sorceFile = parentFolder+ fileName + "_source.png";
          String destFile =  parentFolder+ fileName + ".png";
          FileUtil.saveStreamToQrcode(in, sorceFile);
          BufferedImage bufferedImage = ImageIO.read(new File(sorceFile));
          //由于需要加额外文字: 改用新方法
          //FileUtil.saveStreamToQrcode(in, "D:\\qrcode.png");
          //qrcode = QrCodeUtil.getQrContent("D:\\qrcode.png");
          qrcode = QrCodeUtil.getQrContent2(bufferedImage);
          File file = new File(destFile);
          if(file.exists()){
            file.delete();
          }
          ZXingQrcode qroceZxing = new ZXingQrcode();
          logger.info("二维码内容：{}, 设备编号： {}, 文件路径：{},设备mac： {}",
              qrcode,deviceCode.toUpperCase(),file.getAbsolutePath(),deviceMac);
          qroceZxing.writeToFile(qrcode, deviceCode.toUpperCase(), file, "png");
          qrCodeText.setText(urlparamMap.get("path").toString());
          
          showImageOnLabel(qrCodeLabel, destFile);
          TSCUtil.print(qrcode,deviceCode.toUpperCase());
          logger.info("打印内容已提交"+qrcode);
//          int x = WinpplbUtil.print(qrcode, deviceMac.replaceAll(":", ""), deviceCode,comListLock.getText());
          lastMac = deviceMac;
          macStrWithOutSplit = "";
          devCode ="";
//          switch (x) {
//          case 1:
//            msgLabel.setText("二维码打印已经加入队列.");
//            break;
//          case 2:
//            msgLabel.setText("打印机未打开、未连接或者缺纸，请检查后重试。");
//            break;
//          default:
//            break;
//          }
        }
        
      } catch (IllegalAccessException | IOException ex ) {
        logger.error("从微信注册二维码内容失败",ex);
        showMsgOnLabel("从微信注册二维码内容失败");
      }
    }
	}
	
	public void showImageOnLabel(Label label,String path){
	  label.setImage(ImageConvertor.resize(new Image(Display.getDefault(), path),
	      label.getBounds().width, label.getBounds().height));
//	  ImageIcon imageIcon = new ImageIcon(path); 
//	  java.awt.Image image = imageIcon.getImage().getScaledInstance(label.getBounds().width,label.getBounds().height,java.awt.Image.SCALE_AREA_AVERAGING);
//	  label.setBackgroundImage(ImageConvertor.convertToSWTImage(imageIcon.getImage(),Display.getDefault()));
	}
	/**
	 * @description 在标签位置显示提示内容
	 * @param msg 消息内容
	 * @param param 消息变量
	 */
	public void showMsgOnLabel(String msg, Object... param){
	  if(null == msg){
	    msg = "";
	  }
	  msg = String.format(msg,param);
	  if(null != msgLabel){	    
	    msgLabel.setText(msg);
	  }else{
	    JOptionPane.showMessageDialog(null, "提示", msg, JOptionPane.ERROR_MESSAGE); 
	  }
	}
	
	 /**
   * Create the actions.
   */
  private void createActions() {
    // Create the actions
  }

	public void closePort() {
		serialPort.close();
		outputStream = null;
		inputStream = null;
	}

	/**
	 * Create the menu manager.
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * 
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {

		statusLineManager = new StatusLineManager();
		return statusLineManager;
	}
	
  /**
   * Configure the shell.
   * 
   * @param newShell
   */
  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setImage(SWTResourceManager.getImage(MainWindow.class, "/" + ConfigUtil.utilLogoImage));
    newShell.setText("生产串口二维码工具 " + ConfigUtil.versionStr);
    newShell.addListener(SWT.Close, new Listener() {
      public void handleEvent(Event event) {
        System.exit(0);
      }

    });
  }
	/**
	 * Launch the application.
	 * 
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
	  PuSysUser puSysUser = new PuSysUser();
    MainWindow window = new MainWindow(puSysUser);

	    byte[] bu = new byte[]{0x35, 0x41, 0x41, 0x42};
	    System.out.println(new String(bu,"UTF-8"));
//	  byte[] a = "59520bb3".getBytes("UTF-8");
//	  System.out.println(ByteUtil.byteToHex(a));
//	  byte[] readBuffer= new byte[]{0X55,0X55,(byte)0xEC,(byte)0x83, (byte)0xF2,0x67,0x76,(byte)0x7F,2,0,0,1,0x35,0x39,0x35,0x32,0x30,0x62,0x62,0x33,(byte)0XEE,(byte)0XEE};
//	  byte[] macArr = new byte[6];  
//    System.arraycopy(readBuffer, 2, macArr, 0, 6);
//byte[] dfArr = new byte[4];
//    System.arraycopy(readBuffer, 8, dfArr, 0, 4);
//byte[] devCodeArr = new byte[8];
//    System.arraycopy(readBuffer, 12, devCodeArr, 0, 8);
//String macTemp = ByteUtil.byteToHexString(macArr, ":");
//String deviceMAc = macTemp.substring(0, macTemp.length()-1).toUpperCase();
//String devCode = String.valueOf(dfArr[0])+String.valueOf(dfArr[1])+String.valueOf(dfArr[2])+String.valueOf(dfArr[3])+new String(devCodeArr,"UTF-8").toUpperCase();
		try {
		  if(null != args && args.length >0){
		    ConfigUtil.setConfigFile(args[0]);
		  }

			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
  /**
   * 获取项目所在路径(包括jar)
   * 
   * @return
   */
  public static String getProjectPath() {
      java.net.URL url = MainWindow.class.getProtectionDomain().getCodeSource()
              .getLocation();
      String filePath = null;
      try {
          filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
      } catch (Exception e) {
          e.printStackTrace();
      }
      if (filePath.endsWith(".jar"))
          filePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
      java.io.File file = new java.io.File(filePath);
      filePath = file.getAbsolutePath();
      return filePath;
  }
  public static byte[] getAvailableByte(byte[] src, byte[] temp){
    if(null == src || src.length>20){
      src = new byte[0];
    }
    if(null == temp){
      temp = new byte[0];
    }
    if(src.length == 0){
      if(null != temp && temp.length >0){
        byte[] srcTemp = ArrayUtil.byteConcat(src, temp);
        if((srcTemp[0] & 0xff) != 0x55){
          return getAvailableByte(src, temp.length>=1?Arrays.copyOfRange(temp, 1, temp.length):new byte[0]);
        }else if(srcTemp.length >=2 && (srcTemp[1] & 0xff) != 0x55){
          return getAvailableByte(src, temp.length>=2?Arrays.copyOfRange(temp, 1, temp.length):new byte[0]);
        }else{
          return srcTemp;
        }
      }
    }
    return ArrayUtil.byteConcat(src, temp);
  }
  public static long bytesToLong(byte[] input, int offset, boolean littleEndian) { 
    ByteBuffer buffer = ByteBuffer.wrap(input,offset,8);
    if(littleEndian){
//      ByteBuffer.order(ByteOrder) 方法指定字节序,即大小端模式(BIG_ENDIAN/LITTLE_ENDIAN)
//      ByteBuffer 默认为大端(BIG_ENDIAN)模式 
        buffer.order(littleEndian ?ByteOrder.LITTLE_ENDIAN:ByteOrder.BIG_ENDIAN);
    }
    return buffer.getLong();  
  }

  public static long bytes2Long(byte[] bytes) {
    long num = 0;
    for (int ix = 0; ix < 8; ++ix) {
      num <<= 8;
      num |= (bytes[ix] & 0xff);
    }
    return num;
  }
  
  public void handleBuffer(byte[] readBuffer){
    logger.info("接收到字节数据"+ByteUtil.byteToHex(readBuffer));
    if (null != readBuffer 
        && readBuffer.length > 20 
        && (readBuffer[0] & 0xff) == 0x55 
        && (readBuffer[1] & 0xff) == 0x55) {
      logger.info("获取完整数据：{}", ByteUtil.byteToHex(readBuffer));
      int dataLength = readBuffer[2] & 0xFF;
      byte[] data = new byte[dataLength];
      if(dataLength + 6 > readBuffer.length){
        logger.error("数据长度与实际不一致：{}", ByteUtil.byteToHex(new byte[]{readBuffer[5]}));
        return;
      }
      System.arraycopy(readBuffer, 3, data, 0, dataLength);
      byte[] deviceArr = new byte[dataLength - 8 - 1];//-1为FF位
      System.arraycopy(data, 1, deviceArr, 0, deviceArr.length);
      byte[] imeiArr = new byte[8];
      System.arraycopy(data, dataLength - 8, imeiArr, 0, 8);
      logger.info("获取IMEI: {}",ByteUtil.byteToHex(imeiArr));
//      byte[] deviceNameArr = new byte[(readBuffer[dataLength + 6] & 0xFF) -1];
//          System.arraycopy(readBuffer, dataLength + 7, deviceNameArr, 0, deviceNameArr.length);
      
      devCode = ByteUtil.byteToHexNoSpace(deviceArr).toUpperCase();
      Long imei = bytes2Long(imeiArr);
      final String ercode = String.format("http://iot.pihot.com/lock?d=%s&IMEI=%s", devCode, imei);
      Display.getDefault().syncExec(new Runnable() {
        public void run() {
          printQrCodeBtn.setEnabled(true);
          macText.setText(devCode);
          qrCodeText.setText(ercode);
        };
      });
    }
  }
}
