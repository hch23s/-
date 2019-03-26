import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

import com.qmhd.telecom.common.GetRequest;
import com.qmhd.telecom.common.photo.ImageByteUtil;
import com.qmhd.telecom.entity.BusinessHall;
import com.qmhd.telecom.entity.DidEquip;
import com.qmhd.telecom.entity.Equipments;
import com.qmhd.telecom.entity.ExchangeEquip;
import com.qmhd.telecom.entity.MachineRoom;
import com.qmhd.telecom.entity.MetropolitanAreaNetwork;
import com.qmhd.telecom.entity.Network;
import com.qmhd.telecom.entity.Power;
import com.qmhd.telecom.entity.TransportEquip;
import com.qmhd.telecom.entity.Users;
import com.qmhd.telecom.entity.WirelessEquip;

//删除图片
		if (imgUrl.contains(",")) {
			String[] split = imgUrl.split(",");
			for (String string : split) {
				File imageFile = new File(roomPhoto + "//" + string);
				if (imageFile.exists()) {
					imageFile.delete();
				}
			}
		} else {
			File imageFile = new File(roomPhoto + "//" + imgUrl);
			if (imageFile.exists()) {
				imageFile.delete();
			}
		}
/**
	 * 导出照片
	 * 
	 * @param params
	 * @param room
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/photo")
	public void exportPhoto(HttpServletResponse response, MachineRoom room, String ids, HttpServletResponse res,
			String exportDeviceType) throws Exception {
		
//		 // 获取当前时间
//		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		 Date date = new Date();
//		 Calendar c = Calendar.getInstance();
//		 c.set(Calendar.HOUR_OF_DAY, 11);
//		 c.set(Calendar.MINUTE, 38);
//		 c.set(Calendar.SECOND, 0);
//		 Date m6 = c.getTime();
//		
//		 System.out.println(m6);
//		
//		 try {
//			 System.out.println("12");// m6.getTime()-date.getTime()
//			 Thread.sleep(m6.getTime()-date.getTime());
//			 System.out.println("123");
//			 System.out.println(format.format(new Date()));
//		 } catch (InterruptedException e) {
//			 e.printStackTrace();
//		 }
		
		
		
		List<MachineRoom> findAll = new ArrayList<>();
		List<String> exportDeviceTypeList = new ArrayList<>();
		
		if (exportDeviceType != null && exportDeviceType.contains(",")) {
			String[] split = exportDeviceType.split(",");
			exportDeviceTypeList = Arrays.asList(split);
		} else {
			exportDeviceTypeList.add(exportDeviceType);
		}

		
		
		if (ids != null) {
			if (ids.contains(",")) {
				String[] split = ids.split(",");
				List<String> asList = Arrays.asList(split);
				List<Long> list = new ArrayList<>();
				for (String id : asList) {
					list.add(Long.parseLong(id));
				}
				findAll = machineRoomService.findByIds(list);
			} else {
				MachineRoom findById = machineRoomService.findById(Long.parseLong(ids));
				findAll.add(findById);
			}
		} else {
			// 导出全部的
			findAll = machineRoomService.findPhotoAll(room);
		}

		String officeName = "";// 第一层的名称
		String secondName = "";// 第二层名称
		String thirdlyName = "";// 第三层名称（设备才有）
		String zipFileName = "E://机房照片.zip";// 临时压缩包
		roomPhoto = "E://1.jpg";
		equipmentPhoto = "E://1.jpg";
		String imgUrl = "";
		String photoName = "";
		// 压缩文件路径
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));

		for (MachineRoom machineRoom : findAll) {
			// 创建机房文件夹
			officeName = machineRoom.getOfficeName();
			if (exportDeviceTypeList.contains("机房")) {
				secondName = "//机房照片//";

				// 导出机房的
				photoName = officeName + secondName + "//全景照片-";
				imgUrl = machineRoom.getImgUrl();
				ImageByteUtil.photo(roomPhoto, zos, imgUrl, photoName);

				imgUrl = machineRoom.getImgUrl1();
				photoName = officeName + secondName + "//门窗照片-";
				ImageByteUtil.photo(roomPhoto, zos, imgUrl, photoName);

				imgUrl = machineRoom.getImgUrl2();
				photoName = officeName + secondName + "//管孔照片-";
				ImageByteUtil.photo(roomPhoto, zos, imgUrl, photoName);
			}

			if (exportDeviceTypeList.contains("营业厅代维")) {
				// 导出营业厅数据代维
				List<BusinessHall> findByMachineRoomId = businessHallService.findByPhoto(machineRoom.getId());
				for (BusinessHall businessHall : findByMachineRoomId) {
					secondName = "//营业厅数据代维//";
					thirdlyName = businessHall.getHallName();// 营业厅名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("交换设备")) {
				// 导出交换设备
				List<ExchangeEquip> findByPhoto = exchangeEquipService.findByPhoto(machineRoom.getId());
				for (ExchangeEquip businessHall : findByPhoto) {
					secondName = "//交换设备//";
					thirdlyName = businessHall.getExchangeOfficeName();// 交换局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("支撑网络")) {
				// 导出支撑网络
				List<Network> findByPhoto2 = networkService.findByPhoto(machineRoom.getId());
				for (Network businessHall : findByPhoto2) {
					secondName = "//支撑网络//";
					thirdlyName = businessHall.getNetworkOffice();// 支撑网络局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("动力设备")) {
				// 动力设备
				List<Power> findByPhoto3 = powerService.findByPhoto(machineRoom.getId());
				for (Power businessHall : findByPhoto3) {
					secondName = "//动力设备//";
					thirdlyName = businessHall.getPowerId();// 监控局所名称
					// thirdlyName = businessHall.getMonitoringName();//监控局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("传输设备")) {
				// 传输设备
				List<TransportEquip> findByPhoto4 = transportEquipService.findByPhoto(machineRoom.getId());
				for (TransportEquip businessHall : findByPhoto4) {
					secondName = "//传输设备//";
					thirdlyName = businessHall.getNetElemName();// 网元名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("数据设备")) {
				// 数据设备
				List<Equipments> findByPhoto5 = equipmentsService.findByPhoto(machineRoom.getId());
				for (Equipments businessHall : findByPhoto5) {
					secondName = "//数据设备//";
					thirdlyName = businessHall.getManufactor();// 厂家

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("数据城域网")) {
				// 城域网设备
				List<MetropolitanAreaNetwork> findByPhoto6 = metropolitanAreaNetworkService
						.findByPhoto(machineRoom.getId());
				for (MetropolitanAreaNetwork businessHall : findByPhoto6) {
					secondName = "//城域网设备//";
					thirdlyName = businessHall.getDeviceName();// 设备名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("无线设备")) {
				// 无线设备
				List<WirelessEquip> findByPhoto7 = wirelessEquipService.findByPhoto(machineRoom.getId());
				for (WirelessEquip businessHall : findByPhoto7) {
					secondName = "//无线设备//";
//					thirdlyName = businessHall.getName3g();// 基站名称
				    thirdlyName = businessHall.getId().toString();// 基站名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("DID设备")) {
				// DID设备
				List<DidEquip> findByPhoto8 = didEquipService.findByPhoto(machineRoom.getId());
				for (DidEquip businessHall : findByPhoto8) {
					secondName = "//DID设备//";
					thirdlyName = businessHall.getDidRoomName();// did局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}
		}
		if (zos != null) {
			zos.close();
		}
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Users users = (Users) GetRequest.getSession().getAttribute("users");
		String zipName = users.getUserName() + "-" + sdf.format(new Date()) + ".zip";
		// 输出到客户端
		OutputStream out = null;
		out = response.getOutputStream();
		response.reset();
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(zipName.getBytes("utf-8"), "ISO-8859-1"));
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		out.write(org.apache.commons.io.FileUtils.readFileToByteArray(new File(zipFileName)));
		out.flush();
		out.close();

		// 删除压缩包
		ImageByteUtil.fileDelete(zipFileName);
	}