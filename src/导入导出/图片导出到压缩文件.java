package ���뵼��;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
//	<dependency>
//    <groupId>org.apache.commons</groupId>
//    <artifactId>commons-compress</artifactId>
//    <version>1.6</version>
//</dependency>
	public class ͼƬ������ѹ���ļ� {
		
		public static void main(String[] args){
			String rootPath = "E://";
			//�����ļ�
	        File file = new File(rootPath+"temp_download");
	        //�ж��ļ��Ƿ���ڣ���������ڣ��򴴽����ļ���
	        if(!file.exists()){
	            file.mkdir();
	        }
	        String name = "ͼƬѹ��������";
	        String fileName = name+new Date().getTime();
	        String zipFileName = fileName + ".zip";
	        File zipFile = null;
	 
	 
	        String path = rootPath + "temp_download";
	        
	        //���ù������ȡͼƬ
	        byte[] data = ͼƬ����.image2byte("E:\\ww.jpg");
	        //newһ���ļ�������������ͼƬ��Ĭ�ϱ��浱ǰ���̸�Ŀ¼  
	        if(data != null){
	            File imageFile = new File(path+File.separator+fileName+".jpg");  
	            //���������  
	            FileOutputStream outStream;
				try {
					outStream = new FileOutputStream(imageFile);
					//д������  
		            outStream.write(data);  
		            //�ر������  
		            outStream.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
	        }
	        
	        try {
	            /*
	             * ��JDK�Դ���zipentryѹ����ѹ�����е��ļ����������룬�ʷ������ַ�����ʹ��commons-compress-1.6.jar��
	             * ZipEntry zipEntry = new ZipEntry(new
	             * String(txtFile.getName().getBytes("GB2312"),"ISO-8859-1"));
	             * zos.putNextEntry(zipEntry); ZipOutputStream zos = new
	             * ZipOutputStream(zipFos); ZipEntry zipEntry = new ZipEntry(new
	             * String(txtFile.getName().getBytes("GB2312"),"ISO-8859-1")); zos.
	             * putNextEntry(zipEntry);
	             * zos.write(FileUtils.readFileToByteArray(txtFile)); zos.flush();
	             * zos.close();
	             */
	            //��ȡ�����õ�ͼƬ�ļ�
	            File imageFile = new File(path+"/"+fileName+".jpg");
	            // ���ѹ����
	            zipFile = new File(path + "/" + zipFileName);
	            FileOutputStream zipFos = new FileOutputStream(zipFile);
	            ArchiveOutputStream archOut = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, zipFos);
	            if (archOut instanceof ZipArchiveOutputStream) {
	                ZipArchiveOutputStream zos = (ZipArchiveOutputStream) archOut;
	                ZipArchiveEntry zipEntry = new ZipArchiveEntry(imageFile, imageFile.getName());
	                zos.putArchiveEntry(zipEntry);
	                zos.write(ͼƬ����.image2byte(path+"/"+fileName+".jpg"));
	                zos.closeArchiveEntry();
	                zos.flush();
	                zos.close();
	            }
	 
	 
	            // ѹ����ɾ��txt�ļ�
	            if (imageFile.exists()) {
	                imageFile.delete();
	            }
	 
	 
	            // ������ͻ���
//	            OutputStream out = null;
//	            out = response.getOutputStream();
//	            response.reset();
//	            response.setHeader("Content-Disposition", "attachment;filename=" + new String(zipFileName.getBytes("GB2312"), "ISO-8859-1"));
//	            response.setContentType("application/octet-stream; charset=utf-8");
//	            response.setCharacterEncoding("UTF-8");
//	            out.write(FileUtils.readFileToByteArray(zipFile));
//	            out.flush();
//	            out.close();
	 
	 
	            // ����ͻ��˽�����ɾ��ѹ����
	            if (zipFile.exists()) {
	                zipFile.delete();
	            }
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (ArchiveException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		}
	 
	 
	}

 
 
}
