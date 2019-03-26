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

//ɾ��ͼƬ
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
	 * ������Ƭ
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
		
//		 // ��ȡ��ǰʱ��
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
			// ����ȫ����
			findAll = machineRoomService.findPhotoAll(room);
		}

		String officeName = "";// ��һ�������
		String secondName = "";// �ڶ�������
		String thirdlyName = "";// ���������ƣ��豸���У�
		String zipFileName = "E://������Ƭ.zip";// ��ʱѹ����
		roomPhoto = "E://1.jpg";
		equipmentPhoto = "E://1.jpg";
		String imgUrl = "";
		String photoName = "";
		// ѹ���ļ�·��
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));

		for (MachineRoom machineRoom : findAll) {
			// ���������ļ���
			officeName = machineRoom.getOfficeName();
			if (exportDeviceTypeList.contains("����")) {
				secondName = "//������Ƭ//";

				// ����������
				photoName = officeName + secondName + "//ȫ����Ƭ-";
				imgUrl = machineRoom.getImgUrl();
				ImageByteUtil.photo(roomPhoto, zos, imgUrl, photoName);

				imgUrl = machineRoom.getImgUrl1();
				photoName = officeName + secondName + "//�Ŵ���Ƭ-";
				ImageByteUtil.photo(roomPhoto, zos, imgUrl, photoName);

				imgUrl = machineRoom.getImgUrl2();
				photoName = officeName + secondName + "//�ܿ���Ƭ-";
				ImageByteUtil.photo(roomPhoto, zos, imgUrl, photoName);
			}

			if (exportDeviceTypeList.contains("Ӫҵ����ά")) {
				// ����Ӫҵ�����ݴ�ά
				List<BusinessHall> findByMachineRoomId = businessHallService.findByPhoto(machineRoom.getId());
				for (BusinessHall businessHall : findByMachineRoomId) {
					secondName = "//Ӫҵ�����ݴ�ά//";
					thirdlyName = businessHall.getHallName();// Ӫҵ������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// ���������豸
				List<ExchangeEquip> findByPhoto = exchangeEquipService.findByPhoto(machineRoom.getId());
				for (ExchangeEquip businessHall : findByPhoto) {
					secondName = "//�����豸//";
					thirdlyName = businessHall.getExchangeOfficeName();// ������������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("֧������")) {
				// ����֧������
				List<Network> findByPhoto2 = networkService.findByPhoto(machineRoom.getId());
				for (Network businessHall : findByPhoto2) {
					secondName = "//֧������//";
					thirdlyName = businessHall.getNetworkOffice();// ֧�������������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// �����豸
				List<Power> findByPhoto3 = powerService.findByPhoto(machineRoom.getId());
				for (Power businessHall : findByPhoto3) {
					secondName = "//�����豸//";
					thirdlyName = businessHall.getPowerId();// ��ؾ�������
					// thirdlyName = businessHall.getMonitoringName();//��ؾ�������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// �����豸
				List<TransportEquip> findByPhoto4 = transportEquipService.findByPhoto(machineRoom.getId());
				for (TransportEquip businessHall : findByPhoto4) {
					secondName = "//�����豸//";
					thirdlyName = businessHall.getNetElemName();// ��Ԫ����

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// �����豸
				List<Equipments> findByPhoto5 = equipmentsService.findByPhoto(machineRoom.getId());
				for (Equipments businessHall : findByPhoto5) {
					secondName = "//�����豸//";
					thirdlyName = businessHall.getManufactor();// ����

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("���ݳ�����")) {
				// �������豸
				List<MetropolitanAreaNetwork> findByPhoto6 = metropolitanAreaNetworkService
						.findByPhoto(machineRoom.getId());
				for (MetropolitanAreaNetwork businessHall : findByPhoto6) {
					secondName = "//�������豸//";
					thirdlyName = businessHall.getDeviceName();// �豸����

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// �����豸
				List<WirelessEquip> findByPhoto7 = wirelessEquipService.findByPhoto(machineRoom.getId());
				for (WirelessEquip businessHall : findByPhoto7) {
					secondName = "//�����豸//";
//					thirdlyName = businessHall.getName3g();// ��վ����
				    thirdlyName = businessHall.getId().toString();// ��վ����

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}

			if (exportDeviceTypeList.contains("DID�豸")) {
				// DID�豸
				List<DidEquip> findByPhoto8 = didEquipService.findByPhoto(machineRoom.getId());
				for (DidEquip businessHall : findByPhoto8) {
					secondName = "//DID�豸//";
					thirdlyName = businessHall.getDidRoomName();// did��������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(equipmentPhoto, zos, imgUrl, photoName);
				}
			}
		}
		if (zos != null) {
			zos.close();
		}
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		Users users = (Users) GetRequest.getSession().getAttribute("users");
		String zipName = users.getUserName() + "-" + sdf.format(new Date()) + ".zip";
		// ������ͻ���
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

		// ɾ��ѹ����
		ImageByteUtil.fileDelete(zipFileName);
	}