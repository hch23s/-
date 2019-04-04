package 上传;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.qmhd.telecom.common.JsonUtil;
import com.qmhd.telecom.common.StringUtils;
import com.qmhd.telecom.common.photo.ImageByteUtil;
import com.qmhd.telecom.entity.Certificate;

public class 上传保存 {
	public boolean uploadFile(HttpServletRequest req, MultipartFile file) {
        Boolean flag;
        String fileName = file.getOriginalFilename();
        String filePath = filepath + fileName;
        try {
            file.transferTo(new File(filePath));
            flag = true;
        }
        catch (
            IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            flag = false;
        }
        return flag;
    }
}
int success = 0;
int all= 0;
boolean flag = uploadFile(req, files);
if (!flag) {
	model.put("msg", "上传失败");
	return JsonUtil.toJson(model);
}
ZipFile war = new ZipFile(filepath + files.getOriginalFilename(), Charset.forName("GBK"));
Enumeration<? extends ZipEntry> entries = war.entries();
if (null != entries) {
	while (entries.hasMoreElements()) {
	    all++;
		ZipEntry e = entries.nextElement();
		String fileName = e.getName();
		if (!e.isDirectory()) {
			// System.out.println(fileName.split("\\.")[0].split("-")[0].toString());// 姓名
			// System.out.println(fileName.split("\\.")[0].split("-")[1].toString());// 员工编号
			// System.out.println(fileName.split("\\.")[0].split("-")[2].toString());// 证书名称
			// System.out.println(fileName.split("\\.")[0].split("-")[3].toString());// 证书编号
			List<Certificate> c = new ArrayList<Certificate>();
			c = certificatesService.findByCodeForImport(fileName.split("\\.")[0].split("-")[3].toString(),
					fileName.split("\\.")[0].split("-")[2].toString(),
					fileName.split("\\.")[0].split("-")[1].toString());

			String m = "传输机务员,电信机务员,光缆线务员,光通信机务员,光纤、数字通信设备调试员,维修电工（低压） ,通信电力机务员,网络通信安全管理员,计算机网络管理员,计算机维修工,交换机务员,数据通信机务员,通信网络管理员,网络设备调试员,线务员,移动通信机务员,建造师,通信工程师,安全员A本,安全员B本,安全员C本,登高架子工,有限空间作业,高压电工作业,低压电工作业,焊接与热切割作业,制冷与空调作业,中专,高中,大专,本科,硕士,博士,智能组网工程师,智慧家庭工程师";
			int shu = matchStringByIndexOf(m, fileName.split("\\.")[0].split("-")[2].toString());
			if (shu < 1) {
				model.put("msg", fileName + "证书名称不符合要求!");
				model.put("flag", false);
				model.put("code", "2");
				model.put("eror", "上传失败!");
			} else {
				if (c == null || c.size() == 0) {
					model.put("msg", fileName + "没有找到对应证件!");
					model.put("flag", false);
					model.put("code", "3");
					model.put("eror", "上传失败!");
				} else {
					if (StringUtils.isEmpty(c.get(0).getUrl())) {
						c.get(0).setUrl(fileName);
					} else {
						int n = 0;
						String url = c.get(0).getUrl();
						String[] split = url.split(",");
						for (int j = 0; j < split.length; j++) {
							if (!split[j].equals(fileName)) {
								n++;
							}
						}
						if (n > 0 && n == split.length) {
							url = url + "," + fileName;
						}
						c.get(0).setUrl(url);
					}
					
					certificatesService.save(c);
					if(success==0){
						model.put("msg", "导入"+fileName+"成功");
					}else {
						model.put("msg", model.get("msg")+"/n"+"导入"+fileName+"成功");
					}
					
					success++;
					ImageByteUtil.copy(war.getInputStream(e),new FileOutputStream(new File(filepath + fileName)));
				}
			}
		}
	}
} else {
	model.put("msg", "没有照片");
}
war.close();
all=all-success;
model.put("success", "成功"+success+"张照片，失败"+all+"张照片");
logService.addOperationLog(MenuName, null, success, 8);

return JsonUtil.toJson(model);
