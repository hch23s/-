package ���뵼��;

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

public class ͼƬ����������ѹ���� {
	private static final int  BUFFER_SIZE = 2 * 1024;
	public void exportPhoto(HttpServletResponse response, String params, MachineRoom room, HttpServletRequest req,
			HttpServletResponse res, Map<String, Object> model) throws Exception {
		System.out.println(System.currentTimeMillis());
		List<MachineRoom> findAll = machineRoomService.findPhotoAll();
		System.out.println(System.currentTimeMillis());
		
		
		String folderName= "";//�ļ�������
		String zipFileName = "E://������Ƭ.zip";//��ʱѹ����
		roomPhoto="E://1.jpg";
		String imgUrl ="";
		String photoName="";
		//ѹ���ļ�·��
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
		for (MachineRoom machineRoom : findAll) {
			//���������ļ���
			folderName=machineRoom.getOfficeName()+"//������Ƭ//";
			
			
			photoName=folderName+"//ȫ����Ƭ-";
			imgUrl = machineRoom.getImgUrl();
			machineRoomService.photo(roomPhoto,zos,imgUrl, photoName);
	
			imgUrl = machineRoom.getImgUrl1();
			photoName=folderName + "-�Ŵ���Ƭ-";
			machineRoomService.photo(roomPhoto,zos,imgUrl, photoName);
			
			imgUrl = machineRoom.getImgUrl2();
			photoName=folderName+ "-�ܿ���Ƭ-";
			machineRoomService.photo(roomPhoto,zos,imgUrl, photoName);
		}
		if(zos!=null) {
			zos.close();
		}
//		// ��ȡ��ǰʱ��
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
		
		//������ͻ���
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
		
		//ɾ��ѹ����
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
        //����ƬתΪbyte
		byte[] image2byte = image2byte(photoPath);
        //д���·��
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
     * ��ͼƬת��Ϊbyte����
     * @param path ͼƬ·��
     * @return
     */
    public static byte[] image2byte(String path){
        //����byte����
        byte[] data = null;
        //������
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
