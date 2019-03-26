package 后端;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class TestExcelImage {
	static List<BufferedImage> images = new ArrayList<>();
	static {
		try {
			images.add(ImageIO.read(new File("C:/t/1.jpg")));
			images.add(ImageIO.read(new File("C:/t/2.jpg")));
			images.add(ImageIO.read(new File("C:/t/3.jpg")));
			images.add(ImageIO.read(new File("C:/t/4.jpg")));
			images.add(ImageIO.read(new File("C:/t/5.jpg")));
			images.add(ImageIO.read(new File("C:/t/6.jpg")));
			images.add(ImageIO.read(new File("C:/t/7.jpg")));
			images.add(ImageIO.read(new File("C:/t/8.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		FileOutputStream fileOut = null;
		try {
			// 创建一个工作薄
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet1 = wb.createSheet("new sheet");
			// HSSFRow row = sheet1.createRow(2);
			HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
			short i = 0;
			for (BufferedImage image : images) {
				ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
				ImageIO.write(image, "jpg", byteArrayOut);
				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1 + i, (short) 2, 2 + i);
				anchor.setAnchorType(0);
				// 插入图片
				patriarch.createPicture(anchor,
						wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				i++;
			}
			HSSFRow row = sheet1.createRow(10);
			short s = 10;
			HSSFCell cell = row.createCell(s);
			HSSFCellStyle style = wb.createCellStyle();
			HSSFFont font = wb.createFont();
			font.setStrikeout(true);
			style.setFont(font);
			cell.setCellStyle(style);
			cell.setCellValue("aaaaa");
			fileOut = new FileOutputStream("c:/workbook.xls");
			// 写入excel文件
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException io) {
			io.printStackTrace();
			System.out.println("io erorr : " + io.getMessage());
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
