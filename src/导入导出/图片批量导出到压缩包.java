package 导入导出;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qmhd.telecom.common.photo.ImageByteUtil;
import com.qmhd.telecom.entity.MachineRoom;

public class 图片批量导出到压缩包 {
	private static final int  BUFFER_SIZE = 2 * 1024;
	public void exportPhoto(HttpServletResponse response, String params, MachineRoom room, HttpServletRequest req,
			HttpServletResponse res, Map<String, Object> model) throws Exception {
		System.out.println(System.currentTimeMillis());
		List<MachineRoom> findAll = machineRoomService.findPhotoAll();
		System.out.println(System.currentTimeMillis());
		
		
		String folderName= "";//文件夹名称
		String zipFileName = "E://机房照片.zip";//临时压缩包
		roomPhoto="E://1.jpg";
		String imgUrl ="";
		String photoName="";
		//压缩文件路径
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
		for (MachineRoom machineRoom : findAll) {
			//创建机房文件夹
			folderName=machineRoom.getOfficeName()+"//机房照片//";
			
			
			photoName=folderName+"//全景照片-";
			imgUrl = machineRoom.getImgUrl();
			machineRoomService.photo(roomPhoto,zos,imgUrl, photoName);
	
			imgUrl = machineRoom.getImgUrl1();
			photoName=folderName + "-门窗照片-";
			machineRoomService.photo(roomPhoto,zos,imgUrl, photoName);
			
			imgUrl = machineRoom.getImgUrl2();
			photoName=folderName+ "-管孔照片-";
			machineRoomService.photo(roomPhoto,zos,imgUrl, photoName);
		}
		if(zos!=null) {
			zos.close();
		}
//		// 获取当前时间
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		Date date = new Date();
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.HOUR_OF_DAY, 11);
//		c.set(Calendar.MINUTE, 38);
//		c.set(Calendar.SECOND, 0);
//		Date m6 = c.getTime();
//
//		System.out.println(m6);
//
//		try {
//			System.out.println("12");// m6.getTime()-date.getTime()
//			Thread.sleep(1000);
////			Thread.sleep(m6.getTime()-date.getTime());
//			System.out.println("123");
//			System.out.println(format.format(new Date()));
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		//输出到客户端
		OutputStream out = null;
		out = response.getOutputStream();
		response.reset();
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String("123.zip".getBytes("utf-8"), "ISO-8859-1"));
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		out.write(org.apache.commons.io.FileUtils.readFileToByteArray(new File(zipFileName)));
		out.flush();
		out.close();
		
		//删除压缩包
		ImageByteUtil.fileDelete(zipFileName);
	}
	
	public void photo(String photoPath,ZipOutputStream zos, String imgUrl,String photoName) throws IOException {
		if (imgUrl != null && !imgUrl.equals("")) {
			if (imgUrl.contains(";")) {
				String[] images = imgUrl.split(";");
				for (int i=0;i<images.length;i++) {
					ImageByteUtil.downloadZip( zos, photoPath ,photoName+(i+1));
				}
			} else {
				ImageByteUtil.downloadZip( zos, photoPath ,photoName+1);
			}
		}
	}
	
	public static void downloadZip(ZipOutputStream zos, String photoPath, String photoName)
			throws IOException {
//		long start=System.currentTimeMillis();
        //将照片转为byte
		byte[] image2byte = image2byte(photoPath);
        //写入的路径
		ZipEntry e = new ZipEntry(photoName+".jpg");
		zos.putNextEntry(e);
		zos.write(image2byte);
		zos.closeEntry();
//        
//		 long end=System.currentTimeMillis();
//		 System.out.println("zip ok ,use time:"+(end-start)+"hao miao ");

	}
	public static void fileDelete(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
//			new File(path).mkdir();
		}
	}
	 /**
     * 将图片转换为byte数组
     * @param path 图片路径
     * @return
     */
    public static byte[] image2byte(String path){
        //定义byte数组
        byte[] data = null;
        //输入流
        FileImageInputStream input = null;
        try {
          input = new FileImageInputStream(new File(path));
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          byte[] buf = new byte[BUFFER_SIZE];
          int numBytesRead = 0;
          while ((numBytesRead = input.read(buf)) != -1) {
          output.write(buf, 0, numBytesRead);
          }
          data = output.toByteArray();
          output.close();
          input.close();
        }
        catch (FileNotFoundException ex1) {
          ex1.printStackTrace();
        }
        catch (IOException ex1) {
          ex1.printStackTrace();
        }
        return data;
     }
}
