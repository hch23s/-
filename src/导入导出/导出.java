package 导入导出;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.qmhd.telecom.common.CSVUtils;
import com.qmhd.telecom.entity.Cable;
import com.qmhd.telecom.entity.Informant;

public class 导出 {

	public void exportsCSVPiece(HttpServletRequest req, HttpServletResponse res,String createTime) throws IOException, ParseException {
		List<Cable> list = new ArrayList<Cable>();
		List<Informant> listIn = new ArrayList<Informant>();
		String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".csv";//导出文件名
		List<String> dataList = new ArrayList<>();
		List<String> headers = new ArrayList<String>();
		headers.add("区域,包线人,工作量,公里系数,管道,VIP光缆,干线光缆,交接箱个数,公里系数,城区自建,郊区自建,城区划转,郊区划转\r\n");
		dataList.addAll(headers);
		String sql = null;
		int time = statisticsController.getTime(createTime);
		if (time < 0) {
			sql = "select maintain_area maintainArea,equipment,workload ,"
					+ "pi_ping_count piPingCount, vip_cable_len vipCableLen,"
					+ "artery_len arteryLen,box_count boxCount,suburb_area_stpc1 suburbAreaStpc1,"
					+ "suburb_area_stpc2 suburbAreaStpc2,suburb_area_stpc3 suburbAreaStpc3,suburb_area_stpc4 suburbAreaStpc4"
					+ " from informant where flag=0 and create_time like '%" + createTime + "%' group by equipment";
			RowMapper<Informant> rowMapper = new BeanPropertyRowMapper<>(Informant.class);
			listIn = template.query(sql, rowMapper);
			for (Informant informant : listIn) {
				StringBuilder str = new StringBuilder();
				str.append(informant.getMaintainArea() + ",");
				str.append(informant.getEquipment() + ",");
				str.append(informant.getWorkload() + ",");
				str.append(1 + ",");
				str.append(informant.getPiPingCount() + ",");
				str.append(informant.getVipCableLen() + ",");
				str.append(informant.getArteryLen() + ",");
				str.append(informant.getBoxCount() + ",");
				str.append(6.67 + ",");
				str.append(informant.getSuburbAreaStpc1() + ",");
				str.append(informant.getSuburbAreaStpc2() + ",");
				str.append(informant.getSuburbAreaStpc3() + ",");
				str.append(informant.getSuburbAreaStpc4() + ",");
				dataList.add(str.substring(0, str.length() - 1) + "\r\n");
			}
		} else {

			sql = "select c.maintain_area,c.equipment " + " from cable  c  where c.flag=1 and c.equipment!='' group by c.equipment";
			RowMapper<Cable> rowMapper = new BeanPropertyRowMapper<>(Cable.class);
			list = template.query(sql, rowMapper);

			for (Cable cable2 : list) {
				StringBuilder str = new StringBuilder();
				String maintanArea = cable2.getMaintainArea();//
				str.append(maintanArea + ",");
				String equipment = cable2.getEquipment();
				str.append(equipment + ",");
				
				
				// 计算交接箱总数
				Long boxCount = cableService.boxCount(equipment);
			
				// 计算vip光缆的长度
				String vipCableLen = cableService.vipCableLen(equipment);
				if (vipCableLen != null) {
					if (vipCableLen.length() > 7) {
						vipCableLen = vipCableLen.substring(0, 7);
					}
				}
				cable2.setVipCableLen(vipCableLen);
				// 计算干线光缆的长度
				String arteryLen = cableService.arteryLen(equipment);
				if (arteryLen != null) {
					if (arteryLen.length() > 7) {
						arteryLen = arteryLen.substring(0, 7);
					}
				}
				cable2.setArteryLen(arteryLen);

				// 计算城区自建
				String suburbAreaStpc1 = cableService.suburbAreaStpc1(equipment);
				if (suburbAreaStpc1 != null) {
					if (suburbAreaStpc1.length() > 7) {
						suburbAreaStpc1 = suburbAreaStpc1.substring(0, 7);
					}
				}
				cable2.setSuburbAreaStpc1(suburbAreaStpc1);
				// 计算郊区自建
				String suburbAreaStpc2 = cableService.suburbAreaStpc2(equipment);
				if (suburbAreaStpc2 != null) {
					if (suburbAreaStpc2.length() > 7) {
						suburbAreaStpc2 = suburbAreaStpc2.substring(0, 7);
					}
				}
				cable2.setSuburbAreaStpc2(suburbAreaStpc2);
				// 计算城区划转
				String suburbAreaStpc3 = cableService.suburbAreaStpc3(equipment);
				if (suburbAreaStpc3 != null) {
					if (suburbAreaStpc3.length() > 7) {
						suburbAreaStpc3 = suburbAreaStpc3.substring(0, 7);
					}
				}
				cable2.setSuburbAreaStpc3(suburbAreaStpc3);
				// 计算郊区划转
				String suburbAreaStpc4 = cableService.suburbAreaStpc4(equipment);
				if (suburbAreaStpc4 != null) {
					if (suburbAreaStpc4.length() > 7) {
						suburbAreaStpc4 = suburbAreaStpc4.substring(0, 7);
					}
				}
				cable2.setSuburbAreaStpc4(suburbAreaStpc4);

				// 计算总榄数，总长度
				Long cableCount = 0L;
				String cableLen = null;
				List<Cable> list2 = cableService.cableCount(equipment);
				for (int i = 0; i < list2.size(); i++) {
					cableCount = list2.get(i).getCableCount();
					cableLen = list2.get(i).getCableLen();
					if (cableLen.length() > 7 && cableLen != null) {
						cableLen = cableLen.substring(0, 7);
					}
				}
				cable2.setCableCount(cableCount);
				cable2.setCableLen(cableLen);

				// 计算管道总数，井总数，沟公里总长度
				Long piPingCount = 0L;
				Long wellNumCount = 0L;
				String ditchKmLen = null;

				List<Cable> piPing = cableService.piPingCount(equipment);
				for (int i = 0; i < piPing.size(); i++) {
					piPingCount = piPing.get(i).getPiPingCount();
					wellNumCount = piPing.get(i).getWellNumCount();

					if (wellNumCount == null) {
						wellNumCount = 0L;
					}

					ditchKmLen = piPing.get(i).getDitchKmCount();

				}
				cable2.setPiPingCount(piPingCount);
				cable2.setWellNumCount(wellNumCount);
				if (ditchKmLen != null) {
					if (ditchKmLen.length() > 7) {
						ditchKmLen = ditchKmLen.substring(0, 7);
					}
				}
				cable2.setDitchKmCount(ditchKmLen);

				// vipCableLen arteryLen suburbAreaStpc1 suburbAreaStpc2 suburbAreaStpc3
				// suburbAreaStpc4 ditchKmCount
				if (vipCableLen == null) {
					vipCableLen = "0";
				}
				if (arteryLen == null) {
					arteryLen = "0";
				}
				if (suburbAreaStpc1 == null) {
					suburbAreaStpc1 = "0";
				}
				if (suburbAreaStpc2 == null) {
					suburbAreaStpc2 = "0";
				}
				if (suburbAreaStpc3 == null) {
					suburbAreaStpc3 = "0";
				}
				if (suburbAreaStpc4 == null) {
					suburbAreaStpc4 = "0";
				}
				if (ditchKmLen == null) {
					ditchKmLen = "0";
				}
				Double parseInt1 = Double.parseDouble(vipCableLen);
				Double parseInt2 = Double.parseDouble(arteryLen);
				Double parseInt3 = Double.parseDouble(suburbAreaStpc1);
				Double parseInt4 = Double.parseDouble(suburbAreaStpc2);
				Double parseInt5 = Double.parseDouble(suburbAreaStpc3);
				Double parseInt6 = Double.parseDouble(suburbAreaStpc4);
				Double parseInt7 = Double.parseDouble(ditchKmLen);
				double workload = (((parseInt1 + parseInt2 + parseInt7)
						+ (parseInt3 + parseInt4 + parseInt5 + parseInt6) / 6.67) * 10);
				str.append(workload + ",");
				str.append(1+ ",");
				str.append(piPingCount + ",");
				str.append(piPingCount + ",");
				str.append(arteryLen + ",");
				str.append(boxCount + ",");
				str.append(6.67 + ",");
				str.append(suburbAreaStpc1 + ",");
				str.append(suburbAreaStpc2 + ",");
				str.append(suburbAreaStpc3 + ",");
				str.append(suburbAreaStpc4 +",");
				
				
				
				
				dataList.add(str.substring(0, str.length() - 1) + "\r\n");
			}
		}
		CSVUtils.CSVFileExport(res, dataList, fileName);
	}
}
