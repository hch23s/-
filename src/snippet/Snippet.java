import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import com.qmhd.telecom.common.ReflectField;
import com.qmhd.telecom.common.photo.ImageByteUtil;
import com.qmhd.telecom.entity.DownloadManagement;
import com.qmhd.telecom.entity.MachineRoom;
import com.qmhd.telecom.entity.MessageReminding;
import com.qmhd.telecom.entity.Users;
import com.qmhd.telecom.room.service.DidEquipService;

SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");

		// ѹ���ļ�·��
		String zipFileName = driver + users.getUserName() + "-" + sdf.format(new Date()) + ".zip";
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
		
		//
		room = getRoom(room);
		Users user = (Users) req.getSession().getAttribute("users");
		if (user != null && user.getDept() != null) {
			Integer accessLevel = this.getAccessLevel(req);
			DidEquipService.setAreaCenter(room, user, accessLevel);
		}
		
		Object value = null;
		Map<String, Object> data = ReflectField.getFields(room,value);
		Map<String, Object> roomMap = new HashMap<>();
		for (String key:data.keySet()) {
			if(data.get(key)!=null) {
				roomMap.put(key, data.get(key));
			}
		}
		DownloadManagement dm = new DownloadManagement();
		if (ids != null) {
			dm.setIds(ids);
		} else {
			
//			dm.setMachineRoom(room);
		}
		
		dm.setEquipmentType(exportDeviceType);
		dm.setDeptId(users.getDept().getName());
		dm.setUserName(users.getUserName() + "   " + users.getName());
		dm.setClassGroup(users.getClassGroup());
		dm.setFlag(2);
		dm.setOrderDate(new Date());
		

		List<MachineRoom> findAll = new ArrayList<>();
		List<String> exportDeviceTypeList = new ArrayList<>();
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
		if (exportDeviceType != null && exportDeviceType.contains(",")) {
			String[] split = exportDeviceType.split(",");
			exportDeviceTypeList = Arrays.asList(split);
		} else {
			exportDeviceTypeList.add(exportDeviceType);
		}

	
		

		// ͼƬ·��
		ArrayList<String> pathList = new ArrayList<>();
		//ͼƬ����
		ArrayList<String> NameList = new ArrayList<>();
		// ������Ƭ����
		int roomNum = 0;

//		machineRoomService.roomPhotoPath(exportDeviceTypeList,pathList,NameList,roomNum,findAll);

		
		
		MessageReminding messageReminding = new MessageReminding();

		
		roomPhoto = "E://1.jpg";
		equipmentPhoto = "E://1.jpg";
		int count =0;//ʧ��ʱ������
		try {
			for (int i = 0; i < pathList.size(); i++) {
				if (i == roomNum) {
					ImageByteUtil.downloadZip(zos, equipmentPhoto, NameList.get(i) + 1);
				} else {
					ImageByteUtil.downloadZip(zos, roomPhoto, NameList.get(i) + 1);
				}
				count=i;
			}

			messageReminding.setTitle("��Ƭ����");
			messageReminding.setContent("�����ɹ�");
			messageReminding.setUserId(users.getId());
			messageReminding.setCreateTime(new Date());
			messageReminding.setFlag(1);
			messageRemindingService.save(messageReminding);

		} catch (Exception e) {
			dm.setExportStopNum(count);
			
			
			messageReminding.setTitle("��Ƭ����");
			messageReminding.setContent("����ʧ�ܣ��ڴ治�����������ڴ�");
			messageReminding.setUserId(users.getId());
			messageReminding.setCreateTime(new Date());
			messageReminding.setFlag(1);

			messageRemindingService.save(messageReminding);
			System.out.println("2");
		}finally {
			if (zos != null) {
				zos.close();
			}
		}
		downloadManagementService.save(dm);