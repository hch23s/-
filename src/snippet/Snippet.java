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

// ���������ļ���
			officeName = machineRoom.getOfficeName().trim();
			if (exportDeviceTypeList.contains("����")) {
				if(machineRoom.getImgUrl()!=null||machineRoom.getImgUrl1()!=null||machineRoom.getImgUrl2()!=null) {
					secondName = "//������Ƭ//";
					// ����������
					photoName = officeName + secondName + "ȫ����Ƭ-";
					imgUrl = machineRoom.getImgUrl();
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
	
					imgUrl = machineRoom.getImgUrl1();
					photoName = officeName + secondName + "�Ŵ���Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
	
					imgUrl = machineRoom.getImgUrl2();
					photoName = officeName + secondName + "�ܿ���Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}
			roomNum = pathList.size();
			if (exportDeviceTypeList.contains("Ӫҵ����ά")) {
				// ����Ӫҵ�����ݴ�ά
				List<BusinessHall> findByMachineRoomId = businessHallService.findByPhoto(machineRoom.getId());
				for (BusinessHall businessHall : findByMachineRoomId) {
					secondName = "//Ӫҵ�����ݴ�ά//";
					thirdlyName = businessHall.getId().toString();// Ӫҵ������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// ���������豸
				List<ExchangeEquip> findByPhoto = exchangeEquipService.findByPhoto(machineRoom.getId());
				for (ExchangeEquip businessHall : findByPhoto) {
					secondName = "//�����豸//";
					thirdlyName = businessHall.getId().toString();
					;// ������������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("֧������")) {
				// ����֧������
				List<Network> findByPhoto2 = networkService.findByPhoto(machineRoom.getId());
				for (Network businessHall : findByPhoto2) {
					secondName = "//֧������//";
					thirdlyName = businessHall.getId().toString();
					;// ֧�������������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// �����豸
				List<Power> findByPhoto3 = powerService.findByPhoto(machineRoom.getId());
				for (Power businessHall : findByPhoto3) {
					secondName = "//�����豸//";
					// thirdlyName = businessHall.getPowerId();// ��ؾ�������
					// thirdlyName = businessHall.getMonitoringName();//��ؾ�������
					thirdlyName = businessHall.getId().toString();
					// ��ؾ�������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// �����豸
				List<TransportEquip> findByPhoto4 = transportEquipService.findByPhoto(machineRoom.getId());
				for (TransportEquip businessHall : findByPhoto4) {
					secondName = "//�����豸//";
					// thirdlyName = businessHall.getNetElemName();// ��Ԫ����
					thirdlyName = businessHall.getId().toString();
					;// ��ؾ�������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// �����豸
				List<Equipments> findByPhoto5 = equipmentsService.findByPhoto(machineRoom.getId());
				for (Equipments businessHall : findByPhoto5) {
					secondName = "//�����豸//";
					// thirdlyName = businessHall.getManufactor();// ����
					thirdlyName = businessHall.getId().toString();
					;// ��ؾ�������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("���ݳ�����")) {
				// �������豸
				List<MetropolitanAreaNetwork> findByPhoto6 = metropolitanAreaNetworkService
						.findByPhoto(machineRoom.getId());
				for (MetropolitanAreaNetwork businessHall : findByPhoto6) {
					secondName = "//�������豸//";
					// thirdlyName = businessHall.getDeviceName();// �豸����
					thirdlyName = businessHall.getId().toString();
					;// ��ؾ�������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("�����豸")) {
				// �����豸
				List<WirelessEquip> findByPhoto7 = wirelessEquipService.findByPhoto(machineRoom.getId());
				for (WirelessEquip businessHall : findByPhoto7) {
					secondName = "//�����豸//";
					// thirdlyName = businessHall.getName3g();// ��վ����
					// thirdlyName = businessHall.getId().toString();// ��վ����
					thirdlyName = businessHall.getId().toString();
					;// ��ؾ�������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}

			if (exportDeviceTypeList.contains("DID�豸")) {
				// DID�豸
				List<DidEquip> findByPhoto8 = didEquipService.findByPhoto(machineRoom.getId());
				for (DidEquip businessHall : findByPhoto8) {
					secondName = "//DID�豸//";
					// thirdlyName = businessHall.getDidRoomName();// did��������
					thirdlyName = businessHall.getId().toString();
					// ��ؾ�������

					imgUrl = businessHall.getFileName();
					photoName = officeName + secondName + thirdlyName + "//������Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName1();
					photoName = officeName + secondName + thirdlyName + "//Զ����Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);

					imgUrl = businessHall.getFileName2();
					photoName = officeName + secondName + thirdlyName + "//�ʲ���ǩ��Ƭ-";
					ImageByteUtil.photo(pathList, imgUrl, photoName,nameList);
				}
			}
		