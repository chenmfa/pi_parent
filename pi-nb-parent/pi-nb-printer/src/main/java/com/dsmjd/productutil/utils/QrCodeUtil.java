package com.dsmjd.productutil.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dsmjd.productutil.dto.BufferedQrCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class QrCodeUtil {
  public static final Logger logger = LoggerFactory.getLogger(QrCodeUtil.class);
	// public static void main(String[] args) {
	// // 两个地址，1.原图片，2.生成的图片。
	// QrCodeUtil.createQrCode("dsmLock|11111111|63 : 41 : ba : fc : b8 : db|lock", "E:\\aaa\\a.png",
	// 250, 250);
	// }

	/**
	 * @param code
	 * @param filePath
	 * @param width
	 * @param height
	 * @param logoFilePath
	 */
	public static void createQrCode(String code, String filePath, int width, int height, String... logoFilePath) {
		if (logoFilePath.length > 0) {
			QrCodeUtil.encode(code, width, height, logoFilePath[0], filePath);
		} else {
			QrCodeUtil.printQRCode(code, filePath, width, height);
		}
	}

	/** * * * * * * * * * * * * * * * * * * 二维码生成方案一 * * * * * * * * * * * * * * */
	/** * * * * * * * * * * * * * * * * * * 二维码生成方案一 * * * * * * * * * * * * * * */
	/** * * * * * * * * * * * * * * * * * * 二维码生成方案一 * * * * * * * * * * * * * * */

	// 图片宽度的一般
	private static final int IMAGE_WIDTH = 40;
	private static final int IMAGE_HEIGHT = 40;
	private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
	private static final int FRAME_WIDTH = 2;

	// 二维码写码器
	private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

	public static void encode(String content, int width, int height, String srcImagePath, String destImagePath) {
		try {
			ImageIO.write(genBarcode(content, width, height, srcImagePath), "jpg", new File(destImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	private static BufferedImage genBarcode(String content, int width, int height, String srcImagePath) throws WriterException, IOException {
		// 读取源图像
		BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH, IMAGE_HEIGHT, true);
		int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
		for (int i = 0; i < scaleImage.getWidth(); i++) {
			for (int j = 0; j < scaleImage.getHeight(); j++) {
				srcPixels[i][j] = scaleImage.getRGB(i, j);
			}
		}

		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 生成二维码
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);

		matrix = deleteWhite(matrix);

		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		width = matrix.getWidth();
		height = matrix.getHeight();

		int[] pixels = new int[width * height];

		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				// 读取图片
				if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH && y > halfH - IMAGE_HALF_WIDTH && y < halfH + IMAGE_HALF_WIDTH) {
					pixels[y * width + x] = srcPixels[x - halfW + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
				}
				// 在图片四周形成边框
				else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH - IMAGE_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
					pixels[y * width + x] = 0xfffffff;
				} else {
					// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
					pixels[y * width + x] = matrix.get(x, y) ? 0xff000000 : 0xfffffff;
				}
			}
		}
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);

		return image;
	}

	/**
	 * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
	 * 
	 * @param srcImageFile
	 *            源文件地址
	 * @param height
	 *            目标高度
	 * @param width
	 *            目标宽度
	 * @param hasFiller
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException
	 */
	private static BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller) throws IOException {
		double ratio = 0.0; // 缩放比例
		File file = new File(srcImageFile);
		BufferedImage srcImage = ImageIO.read(file);
		Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}
		if (hasFiller) {// 补白
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setColor(Color.white);
			graphic.fillRect(0, 0, width, height);
			if (width == destImage.getWidth(null))
				graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null), destImage.getHeight(null),
						Color.white, null);
			else
				graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0, destImage.getWidth(null), destImage.getHeight(null),
						Color.white, null);
			graphic.dispose();
			destImage = image;
		}
		return (BufferedImage) destImage;
	}

	/** * * * * * * * * * * * * * * * * * * 二维码生成方案一 * * * * * * * * * * * * * * */
	/** * * * * * * * * * * * * * * * * * * 二维码生成方案一 * * * * * * * * * * * * * * */
	/** * * * * * * * * * * * * * * * * * * 二维码生成方案一 * * * * * * * * * * * * * * */

	/** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */
	/** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */
	/** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */

	/**
	 * 打印二维码
	 * 
	 * @param file
	 * @throws WriterException
	 * @throws IOException
	 * @throws PrintException
	 */
	@SuppressWarnings("unused")
	private static void print(File file) throws WriterException, IOException, PrintException {

		HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
		PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
		DocPrintJob job = defaultService.createPrintJob(); // 创建打印作业
		Object fis = new FileInputStream(file); // 构造待打印的文件流
		DocAttributeSet das = new HashDocAttributeSet();
		Doc doc = new SimpleDoc(fis, flavor, das);
		job.print(doc, pras);

	}

	/** 二维码生成 */
	public static Boolean printQRCode(String code, String filePath, int width, int height) {
		try {
			String format = "png";
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

			BitMatrix bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.QR_CODE, width, height, hints);
			bitMatrix = deleteWhite(bitMatrix);
			File outputFile = new File(filePath);

			// MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
			MatrixToImageWriter.writeToPath(bitMatrix, format, outputFile.toPath());

			// 执行打印
			// print(outputFile);
			return true;
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			// } catch (PrintException e) {
			// e.printStackTrace();
		}

		return false;
	}

	public static BitMatrix deleteWhite(BitMatrix matrix) {
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + 1;
		int resHeight = rec[3] + 1;

		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++) {
			for (int j = 0; j < resHeight; j++) {
				if (matrix.get(i + rec[0], j + rec[1]))
					resMatrix.set(i, j);
			}
		}
		return resMatrix;
	}

	/** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */
	/** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */
	/** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */

	public static String getQrContent(String filePath){
	  BufferedImage image;  
    try {  
        image = ImageIO.read(new File(filePath));  
        return getQrContent(image);
    }catch(Exception e){
      logger.error("获取二维码内容失败",e);
      return null;
    }
	}
	
	 /** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */
  /** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */
  /** * * * * * * * * * * * * * * * * * * 二维码生成方案二 * * * * * * * * * * * * * * */

  public static String getQrContent(BufferedImage image){
    try {  
        LuminanceSource source = new BufferedImageLuminanceSource(image);  
        Binarizer binarizer = new HybridBinarizer(source);  
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
//        开启PURE_BARCODE模式
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//        hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
//        Vector<BarcodeFormat> decodeFormats = new Vector<>();
//        decodeFormats.add(BarcodeFormat.QR_CODE);
//        decodeFormats.add(BarcodeFormat.DATA_MATRIX);
//        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码 
        return result.getText();
    }catch(Exception e){
      logger.error("获取二维码内容失败",e);
      return null;
    }
  }
	
  public static String getQrContent2(BufferedImage image)
      throws DecodingFailedException, IOException{
    //调用方法 
    QRCodeDecoder codeDecoder=new QRCodeDecoder(); 
    String result=new String(codeDecoder.decode(new BufferedQrCode(image)),"UTF-8");
    System.out.println("二维码的内容为："+result);
    return result;
  }
  
  public static String getQrContent3(BufferedImage image){
    LuminanceSource source = new BufferedImageLuminanceSource(image);  
    Binarizer binarizer = new HybridBinarizer(source);  
    BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);  
    Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();  
    hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
    QRCodeReader reader = new QRCodeReader();
    Result result=null;
    try {
      result = reader.decode(binaryBitmap, hints);
    } catch (Exception e) {
      logger.info("获取二维码内容失败",e);
    }// 对图像进行解码 
    return (null != result)?result.getText():null;
  }
  public static BufferedImage zoomImage(BufferedImage image, int width, int height){
    logger.info(image.getWidth()+"---------"+image.getHeight());
    BufferedImage newImage = new BufferedImage(image.getWidth()/5, image.getHeight()/5, image.getType());
    Graphics2D graphics = newImage.createGraphics();
    graphics.drawImage(image, 0, 0, width, height, null);
    graphics.dispose();
    return newImage;
  }
  
	public static void main(String[] args) throws DecodingFailedException, IOException {
	  BufferedImage image = ImageIO.read(new File("D:\\19015972F236_55675972F236_source.png"));
	  image = zoomImage(image, 300, 300);
//	  String content = getQrContent2(image);
	  String content1 = getQrContent(image);
	  String content3 = getQrContent3(image);
	  ByteArrayOutputStream os = new ByteArrayOutputStream();
	  ImageIO.write(image, "png", os);
	  FileOutputStream fos = new FileOutputStream(new File("D:\\home\\maquan_dest.png"));
	  os.writeTo(fos);
	  os.flush();
	  os.close();
//	  System.out.println(content);
	  System.out.println(content1);
	  System.out.println(content3);
  }
}
