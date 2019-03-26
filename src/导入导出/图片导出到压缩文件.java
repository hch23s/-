package 导入导出;

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
	public class 图片导出到压缩文件 {
		
		public static void main(String[] args){
			String rootPath = "E://";
			//创建文件
	        File file = new File(rootPath+"temp_download");
	        //判断文件是否存在，如果不存在，则创建此文件夹
	        if(!file.exists()){
	            file.mkdir();
	        }
	        String name = "图片压缩包下载";
	        String fileName = name+new Date().getTime();
	        String zipFileName = fileName + ".zip";
	        File zipFile = null;
	 
	 
	        String path = rootPath + "temp_download";
	        
	        //调用工具类获取图片
	        byte[] data = 图片导出.image2byte("E:\\ww.jpg");
	        //new一个文件对象用来保存图片，默认保存当前工程根目录  
	        if(data != null){
	            File imageFile = new File(path+File.separator+fileName+".jpg");  
	            //创建输出流  
	            FileOutputStream outStream;
				try {
					outStream = new FileOutputStream(imageFile);
					//写入数据  
		            outStream.write(data);  
		            //关闭输出流  
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
	             * 用JDK自带的zipentry压缩后，压缩包中的文件名中文乱码，故放弃此种方法，使用commons-compress-1.6.jar包
	             * ZipEntry zipEntry = new ZipEntry(new
	             * String(txtFile.getName().getBytes("GB2312"),"ISO-8859-1"));
	             * zos.putNextEntry(zipEntry); ZipOutputStream zos = new
	             * ZipOutputStream(zipFos); ZipEntry zipEntry = new ZipEntry(new
	             * String(txtFile.getName().getBytes("GB2312"),"ISO-8859-1")); zos.
	             * putNextEntry(zipEntry);
	             * zos.write(FileUtils.readFileToByteArray(txtFile)); zos.flush();
	             * zos.close();
	             */
	            //获取创建好的图片文件
	            File imageFile = new File(path+"/"+fileName+".jpg");
	            // 打成压缩包
	            zipFile = new File(path + "/" + zipFileName);
	            FileOutputStream zipFos = new FileOutputStream(zipFile);
	            ArchiveOutputStream archOut = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, zipFos);
	            if (archOut instanceof ZipArchiveOutputStream) {
	                ZipArchiveOutputStream zos = (ZipArchiveOutputStream) archOut;
	                ZipArchiveEntry zipEntry = new ZipArchiveEntry(imageFile, imageFile.getName());
	                zos.putArchiveEntry(zipEntry);
	                zos.write(图片导出.image2byte(path+"/"+fileName+".jpg"));
	                zos.closeArchiveEntry();
	                zos.flush();
	                zos.close();
	            }
	 
	 
	            // 压缩完删除txt文件
	            if (imageFile.exists()) {
	                imageFile.delete();
	            }
	 
	 
	            // 输出到客户端
//	            OutputStream out = null;
//	            out = response.getOutputStream();
//	            response.reset();
//	            response.setHeader("Content-Disposition", "attachment;filename=" + new String(zipFileName.getBytes("GB2312"), "ISO-8859-1"));
//	            response.setContentType("application/octet-stream; charset=utf-8");
//	            response.setCharacterEncoding("UTF-8");
//	            out.write(FileUtils.readFileToByteArray(zipFile));
//	            out.flush();
//	            out.close();
	 
	 
	            // 输出客户端结束后，删除压缩包
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
