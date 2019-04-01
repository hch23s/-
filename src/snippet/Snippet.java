import java.util.List;

import com.qmhd.telecom.common.photo.ImageByteUtil;
import com.qmhd.telecom.entity.BusinessHall;
import com.qmhd.telecom.entity.DidEquip;
import com.qmhd.telecom.entity.Equipments;
import com.qmhd.telecom.entity.ExchangeEquip;
import com.qmhd.telecom.entity.MetropolitanAreaNetwork;
import com.qmhd.telecom.entity.Network;
import com.qmhd.telecom.entity.Power;
import com.qmhd.telecom.entity.TransportEquip;
import com.qmhd.telecom.entity.WirelessEquip;

// 创建机房文件夹
			officeName = machineRoom.getOfficeName().trim();
			if (exportDeviceTypeList.contains("机房")) {
				if(machineRoom.getImgUrl()!=null||machineRoom.getImgUrl1()!=null||machineRoom.getImgUrl2()!=null) {
					secondName = "//机房照片//";
					// 导出机房的
					photoName = officeName + secondName + "全景照片-";
					imgUrl = machineRoom.getImgUrl();
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
	
					imgUrl = machineRoom.getImgUrl1();
					photoName = officeName + secondName + "门窗照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
	
					imgUrl = machineRoom.getImgUrl2();
					photoName = officeName + secondName + "管孔照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}
			roomNum = pathList.size();
			if (exportDeviceTypeList.contains("营业厅代维")) {
				// 导出营业厅数据代维
				List<BusinessHall> findByMachineRoomId = businessHallService.findByPhoto(machineRoom.getId());
				for (BusinessHall businessHall : findByMachineRoomId) {
					secondName = "//营业厅数据代维//";
					thirdlyName = businessHall.getId().toString();// 营业厅名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("交换设备")) {
				// 导出交换设备
				List<ExchangeEquip> findByPhoto = exchangeEquipService.findByPhoto(machineRoom.getId());
				for (ExchangeEquip businessHall : findByPhoto) {
					secondName = "//交换设备//";
					thirdlyName = businessHall.getId().toString();
					;// 交换局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("支撑网络")) {
				// 导出支撑网络
				List<Network> findByPhoto2 = networkService.findByPhoto(machineRoom.getId());
				for (Network businessHall : findByPhoto2) {
					secondName = "//支撑网络//";
					thirdlyName = businessHall.getId().toString();
					;// 支撑网络局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("动力设备")) {
				// 动力设备
				List<Power> findByPhoto3 = powerService.findByPhoto(machineRoom.getId());
				for (Power businessHall : findByPhoto3) {
					secondName = "//动力设备//";
					// thirdlyName = businessHall.getPowerId();// 监控局所名称
					// thirdlyName = businessHall.getMonitoringName();//监控局所名称
					thirdlyName = businessHall.getId().toString();
					// 监控局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("传输设备")) {
				// 传输设备
				List<TransportEquip> findByPhoto4 = transportEquipService.findByPhoto(machineRoom.getId());
				for (TransportEquip businessHall : findByPhoto4) {
					secondName = "//传输设备//";
					// thirdlyName = businessHall.getNetElemName();// 网元名称
					thirdlyName = businessHall.getId().toString();
					;// 监控局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("数据设备")) {
				// 数据设备
				List<Equipments> findByPhoto5 = equipmentsService.findByPhoto(machineRoom.getId());
				for (Equipments businessHall : findByPhoto5) {
					secondName = "//数据设备//";
					// thirdlyName = businessHall.getManufactor();// 厂家
					thirdlyName = businessHall.getId().toString();
					;// 监控局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("数据城域网")) {
				// 城域网设备
				List<MetropolitanAreaNetwork> findByPhoto6 = metropolitanAreaNetworkService
						.findByPhoto(machineRoom.getId());
				for (MetropolitanAreaNetwork businessHall : findByPhoto6) {
					secondName = "//城域网设备//";
					// thirdlyName = businessHall.getDeviceName();// 设备名称
					thirdlyName = businessHall.getId().toString();
					;// 监控局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("无线设备")) {
				// 无线设备
				List<WirelessEquip> findByPhoto7 = wirelessEquipService.findByPhoto(machineRoom.getId());
				for (WirelessEquip businessHall : findByPhoto7) {
					secondName = "//无线设备//";
					// thirdlyName = businessHall.getName3g();// 基站名称
					// thirdlyName = businessHall.getId().toString();// 基站名称
					thirdlyName = businessHall.getId().toString();
					;// 监控局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("DID设备")) {
				// DID设备
				List<DidEquip> findByPhoto8 = didEquipService.findByPhoto(machineRoom.getId());
				for (DidEquip businessHall : findByPhoto8) {
					secondName = "//DID设备//";
					// thirdlyName = businessHall.getDidRoomName();// did局所名称
					thirdlyName = businessHall.getId().toString();
					// 监控局所名称

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//近景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//远景照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//资产标签照片-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}
		