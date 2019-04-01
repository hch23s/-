package 导入导出;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qmhd.telecom.common.GetRequest;
import com.qmhd.telecom.common.JsonUtil;
import com.qmhd.telecom.common.ReflectField;
import com.qmhd.telecom.common.StringUtils;
import com.qmhd.telecom.common.photo.ImageByteUtil;
import com.qmhd.telecom.entity.DownloadManagement;
import com.qmhd.telecom.entity.MachineRoom;
import com.qmhd.telecom.entity.MessageReminding;
import com.qmhd.telecom.entity.OperationLog;
import com.qmhd.telecom.entity.Users;

public class 我写的图片导出 {
	@RequestMapping("/affirmExport")
	@ResponseBody
	public String affirmExport(HttpServletRequest req, HttpServletResponse res, Long id)
			throws IOException {
		DownloadManagement dm = downloadManagementService.findOne(id);
		Users users = (Users) GetRequest.getSession().getAttribute("users");
		Map<String, String> map = new HashMap<>();

		// 压缩文件名称
		roomPhoto="D://";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hhmm");
		String zipFileName = roomPhoto + users.getUserName() + "-" + sdf.format(new Date()) + ".zip";
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipFileName));
		} catch (Exception e) {
			map.put("msg", zipFileName+"正在被使用，无法进行操作");
			map.put("status", "2");
			return JsonUtil.toJson(map);
		}
		// 机房数据
		List<String> exportDeviceTypeList = new ArrayList<>();
		
		//导出那些设备
		if (dm.getEquipmentType() != null && dm.getEquipmentType().contains(",")) {
			String[] split = dm.getEquipmentType().split(",");
			exportDeviceTypeList = Arrays.asList(split);
		} else {
			exportDeviceTypeList.add(dm.getEquipmentType());
		}
		
	
		
		// 图片路径
		ArrayList<String> pathList = new ArrayList<>();
		//图片名称
		ArrayList<String> nameList = new ArrayList<>();
		// 机房照片数量
		int roomNum = 0;
		if (dm.getIds() != null) {
			List<Long> idList = StringUtils.getIds(dm.getIds());
			machineRoomService.roomPhotoPath(exportDeviceTypeList,pathList,nameList,roomNum,null,idList);
		}else {
			MachineRoom room = new MachineRoom();
			room.setNetType("");
			String roomCondition = dm.getRoomCondition().substring(1, dm.getRoomCondition().length()-1);
			String[] split2 = roomCondition.split(",");
			for (String string : split2) {
				ReflectField.setRoom(room,string.split("=")[0].trim(),string.split("=")[1].trim());
			}
			// 导出全部的
			machineRoomService.roomPhotoPath(exportDeviceTypeList,pathList,nameList,roomNum,room,null);
		}

		roomPhoto = "E://2.jpg";
		equipmentPhoto = "E://2.jpg";
		for (int i = 0; i < pathList.size(); i++) {
			if (i < roomNum) {
				ImageByteUtil.downloadZip(zos, roomPhoto, nameList.get(i));
			} else {
				ImageByteUtil.downloadZip(zos, equipmentPhoto, nameList.get(i));
			}
		}
		zos.close();        
		MessageReminding messageReminding = new MessageReminding();

        
        //文件大小
        long length = new File(zipFileName).length();
        String driver = ImageByteUtil.driver();
		
        //用户最大盘符大小
		long freeSpace = new File(driver).getFreeSpace();
		if (driver.equals("内存不够")|| length>freeSpace) {
			messageReminding.setTitle("照片导出");
			messageReminding.setContent("导出失败");
			messageReminding.setUserId(users.getId());
			messageReminding.setCreateTime(new Date());
			messageReminding.setFlag(1);
			messageRemindingService.save(messageReminding);
			map.put("msg", "内存不够!");
			map.put("status", "1");
			return JsonUtil.toJson(map);
		}
		
		
		
        ZipFile war = new ZipFile(zipFileName);
        String name=driver + users.getUserName() + "-" + sdf.format(new Date()) + ".zip";
        ZipOutputStream append = new ZipOutputStream(new FileOutputStream(name));
 
        Enumeration<? extends ZipEntry> entries = war.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
//            System.out.println("copy: " + e.getName());
            append.putNextEntry(e);
            if (!e.isDirectory()) {
                ImageByteUtil.copy(war.getInputStream(e), append);
            }
            append.closeEntry();
        }
        war.close();
        append.close();
        
        File zipFile = new File(zipFileName);
        if (zipFile.exists()) {
            zipFile.delete();
        }
        
        messageReminding.setTitle("照片导出");
		messageReminding.setContent("导出成功");
		messageReminding.setUserId(users.getId());
		messageReminding.setCreateTime(new Date());
		messageReminding.setFlag(1);
		messageRemindingService.save(messageReminding);
		logService.addOperationLog(MenuName, null,pathList.size(),7);
		
		dm.setExportContent(pathList.size()+"");
		dm.setFinishDate(new Date());
		dm.setExportStop("成功");
		dm.setZipFileName(name);
		downloadManagementService.save(dm);
 
		map.put("msg", "下载成功!");
		map.put("status", "1");
		return JsonUtil.toJson(map);
	}
 
	@RequestMapping("/delExport")
	@ResponseBody
	public String delExport( HttpServletRequest req, HttpServletResponse res, Long id)
			throws IOException {
		DownloadManagement findOne = downloadManagementService.findOne(id);
		findOne.setExportStop("取消");
		downloadManagementService.save(findOne);
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "取消成功!");
		map.put("status", "1");
		return JsonUtil.toJson(map);
	}
	
	
	
	
	
	
	/**
	 * 导出图片一张一张导，不用了
	 * @throws IOException 
	 */
//	@RequestMapping("/affirmExport")
//	@ResponseBody
	public String affirmExports(OperationLog operationLog, HttpServletRequest req, HttpServletResponse res, Long id)
			throws IOException {
		DownloadManagement dm = downloadManagementService.findOne(id);

		Users users = (Users) GetRequest.getSession().getAttribute("users");
		Map<String, String> map = new HashMap<>();


		// 获取用户最大的盘,低于1G提示内存不够
		String driver = ImageByteUtil.driver();
		if (driver.equals("内存不够")) {
			map.put("msg", "内存不够!");
			map.put("status", "1");
			return JsonUtil.toJson(map);
		}
		// 压缩文件名称
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日hhmm");
		String zipFileName = driver + users.getUserName() + "-" + sdf.format(new Date()) + ".zip";
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(new FileOutputStream(zipFileName));
		} catch (Exception e) {
			map.put("msg", zipFileName+"正在被使用，无法进行操作");
			map.put("status", "2");
			return JsonUtil.toJson(map);
		}

		dm.setZipFileName(zipFileName);
		// 机房数据
		List<String> exportDeviceTypeList = new ArrayList<>();
		
		//导出那些设备
		if (dm.getEquipmentType() != null && dm.getEquipmentType().contains(",")) {
			String[] split = dm.getEquipmentType().split(",");
			exportDeviceTypeList = Arrays.asList(split);
		} else {
			exportDeviceTypeList.add(dm.getEquipmentType());
		}
		
	
		
		// 图片路径
		ArrayList<String> pathList = new ArrayList<>();
		//图片名称
		ArrayList<String> nameList = new ArrayList<>();
		// 机房照片数量
		int roomNum = 0;
		if (dm.getIds() != null) {
			List<Long> idList = StringUtils.getIds(dm.getIds());
			machineRoomService.roomPhotoPath(exportDeviceTypeList,pathList,nameList,roomNum,null,idList);
		}else {
			MachineRoom room = new MachineRoom();
			room.setNetType("");
			String roomCondition = dm.getRoomCondition().substring(1, dm.getRoomCondition().length()-1);
			String[] split2 = roomCondition.split(",");
			for (String string : split2) {
				ReflectField.setRoom(room,string.split("=")[0].trim(),string.split("=")[1].trim());
			}
			// 导出全部的
			machineRoomService.roomPhotoPath(exportDeviceTypeList,pathList,nameList,roomNum,room,null);
		}
		dm.setExportContent(pathList.size()+"");
		
		MessageReminding messageReminding = new MessageReminding();
		roomPhoto = "E://1.jpg";
		equipmentPhoto = "E://1.jpg";
		int count =0;//失败时的条数
		int exportStopNum = Integer.parseInt(dm.getExportStop()) ;
		
//		if(sdf1.format(new Date()).equals("08")){
//		System.out.println("现在8点");
//		}
		SimpleDateFormat sdf1=new SimpleDateFormat("HH");
		String stop="";
		
		try {
			if(exportStopNum==0) {
				exportStopNum=1;
			}
			for (int i = exportStopNum--; i < pathList.size(); i++) {
				if(sdf1.format(new Date()).equals("08")) {
					stop="暂停";
					break;
				}
				
				if (i < roomNum) {
					ImageByteUtil.downloadZip(zos, roomPhoto, nameList.get(i));
				} else {
					ImageByteUtil.downloadZip(zos, equipmentPhoto, nameList.get(i));
				}
				count=i;
				if(count%30==0 &&count !=exportStopNum) {
					count++;
					dm.setExportStop(count+"");
 					downloadManagementService.save(dm);
				}
			}
			messageReminding.setTitle("照片导出");
			if(stop.equals("暂停")) {
				messageReminding.setContent("导出暂停");
				map.put("msg", "导出暂停!");
				map.put("status", "1");
				return JsonUtil.toJson(map);
			}else {
				messageReminding.setContent("导出成功");
			}
			messageReminding.setUserId(users.getId());
			messageReminding.setCreateTime(new Date());
			messageReminding.setFlag(1);
			messageRemindingService.save(messageReminding);
			
		} catch (Exception e) {
			dm.setExportStop(count+"");
			downloadManagementService.save(dm);
			System.out.println("2");
			messageReminding.setTitle("照片导出");
			messageReminding.setContent("导出失败");
			messageReminding.setUserId(users.getId());
			messageReminding.setCreateTime(new Date());
			messageReminding.setFlag(1);
			messageRemindingService.save(messageReminding);
			map.put("msg", "导出失败!");
			map.put("status", "1");
			return JsonUtil.toJson(map);
		}finally {
			if (zos != null) {
				zos.close();
			}
		}
		
		count++;
		dm.setFinishDate(new Date());
		dm.setExportStop(count+"");
		downloadManagementService.save(dm);
		logService.addOperationLog(MenuName, null,count,7);

		
		
		map.put("msg", "导出成功!");
		map.put("status", "1");
		return JsonUtil.toJson(map);
	}

	package com.qmhd.telecom.common.photo;

	import java.io.ByteArrayOutputStream;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.text.DecimalFormat;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.HashMap;
	import java.util.List;
	import java.util.zip.ZipEntry;
	import java.util.zip.ZipOutputStream;

	import javax.imageio.stream.FileImageInputStream;
	import javax.persistence.criteria.CriteriaBuilder.In;
	import javax.swing.filechooser.FileSystemView;

	import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

	import javassist.expr.NewArray;

	/**
	 * 图片压缩
	 * 
	 * @author Administrator
	 *
	 */
	public class ImageByteUtil {
		private static final int BUFFER_SIZE = 2 * 1024;
		private static int i = 0;
	    private static final byte[] BUFFER = new byte[4096 * 1024];
	    
	    
	    public static void copy(InputStream input, OutputStream output) throws IOException {
	        int bytesRead;
	        while ((bytesRead = input.read(BUFFER))!= -1) {
	            output.write(BUFFER, 0, bytesRead);
	        }
	     }
			
		/**
		 * 对照片地址进行处理
		 * 
		 * @param photoPath
		 * @param zos
		 * @param imgUrl
		 * @param photoName
		 * @param nameList 
		 * @throws IOException
		 */
		public static void photo(List<String> pathlist, String imgUrl, String photoName, List<String> nameList) throws IOException {
			if (imgUrl != null && !imgUrl.equals("")) {
				if (imgUrl.contains(";")) {
					String[] images = imgUrl.split(";");
					for (int i = 0; i < images.length; i++) {
						nameList.add(photoName + (i + 1));
						pathlist.add(images[i]);
					}
				} else {
					nameList.add(photoName + 1);
					pathlist.add(imgUrl);
					// downloadZip( zos, photoPath ,photoName+1);
				}
			}
		}

		public static void downloadZip(ZipOutputStream zos, String photoPath, String photoName) throws IOException {
			// long start=System.currentTimeMillis();
			// 将照片转为byte
			byte[] image2byte = image2byte(photoPath);

			// 写入的路径
			ZipEntry e = null;
			try {
				e = new ZipEntry(photoName + ".jpg");
				zos.putNextEntry(e);
				zos.write(image2byte);
				zos.closeEntry();
			} catch (Exception se) {
				i++;
				System.out.println(i);
				if (photoName != null && photoName.contains("//")) {
					String[] split = photoName.split("//");
					if (split.length == 3) {
						split[0] = split[0] + i + "";
						photoName = split[0] + "//" + split[1] + "//" + split[2];
					} else if (split.length == 4) {
						split[2] = split[2] + i + "";
						photoName = split[0] + "//" + split[1] + "//" + split[2] + "//" + split[3];
					}
				}

				e = new ZipEntry(photoName + ".jpg");
				zos.putNextEntry(e);
				zos.write(image2byte);
				zos.closeEntry();
			}

			//
			// long end=System.currentTimeMillis();
			// System.out.println("zip ok ,use time:"+(end-start)+"hao miao ");

		}

		public static void fileDelete(String path) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
				// new File(path).mkdir();
			}
		}

		/**
		 * 将图片转换为byte数组
		 * 
		 * @param path
		 *            图片路径
		 * @return
		 */
		public static byte[] image2byte(String path) {
			// 定义byte数组
			byte[] data = null;
			// 输入流
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
			} catch (FileNotFoundException ex1) {
				ex1.printStackTrace();
			} catch (IOException ex1) {
				ex1.printStackTrace();
			}
			return data;
		}

		public static String FormetFileSize(long fileS) {
			DecimalFormat df = new DecimalFormat("#.00");
			String fileSizeString = "";
			if (fileS < 1024) {
				fileSizeString = df.format((double) fileS) + "B";
			} else if (fileS < 1048576) {
				fileSizeString = df.format((double) fileS / 1024) + "K";
			} else if (fileS < 1073741824) {
				fileSizeString = df.format((double) fileS / 1048576) + "M";
			} else {
				fileSizeString = df.format((double) fileS / 1073741824) + "G";
			}
			return fileSizeString;
		}

		/**
		 * 获取硬盘的每个盘符
		 * 
		 * @return
		 */
		public static String driver() {
			List<Double> list = new ArrayList<>();
			HashMap<Integer, Double> map = new HashMap<>();

			// 当前文件系统类
			FileSystemView fsv = FileSystemView.getFileSystemView();
			// 列出所有windows 磁盘
			File[] fs = File.listRoots();
			// 显示磁盘卷标
			for (int i = 0; i < fs.length; i++) {
				if (FormetFileSize(fs[i].getFreeSpace()).contains("G")) {
					String[] split = FormetFileSize(fs[i].getFreeSpace()).split("G");
					list.add(Double.parseDouble(split[0]));
					map.put(i, Double.parseDouble(split[0]));
				}
			}
			// 获取最大的盘符
			try {
				Collections.sort(list);
				Double double1 = list.get(list.size() - 1);
				List<Integer> list2 = new ArrayList<>();
				for (Integer key : map.keySet()) {
					if (map.get(key).equals(double1)) {
						list2.add(key);
					}
				}
				String systemDisplayName =fs[list2.get(0)].toString();
				return systemDisplayName;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "内存不够";

		}

	}

}
