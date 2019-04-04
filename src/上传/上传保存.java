package �ϴ�;

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

public class �ϴ����� {
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
	model.put("msg", "�ϴ�ʧ��");
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
			// System.out.println(fileName.split("\\.")[0].split("-")[0].toString());// ����
			// System.out.println(fileName.split("\\.")[0].split("-")[1].toString());// Ա�����
			// System.out.println(fileName.split("\\.")[0].split("-")[2].toString());// ֤������
			// System.out.println(fileName.split("\\.")[0].split("-")[3].toString());// ֤����
			List<Certificate> c = new ArrayList<Certificate>();
			c = certificatesService.findByCodeForImport(fileName.split("\\.")[0].split("-")[3].toString(),
					fileName.split("\\.")[0].split("-")[2].toString(),
					fileName.split("\\.")[0].split("-")[1].toString());

			String m = "�������Ա,���Ż���Ա,��������Ա,��ͨ�Ż���Ա,���ˡ�����ͨ���豸����Ա,ά�޵繤����ѹ�� ,ͨ�ŵ�������Ա,����ͨ�Ű�ȫ����Ա,������������Ա,�����ά�޹�,��������Ա,����ͨ�Ż���Ա,ͨ���������Ա,�����豸����Ա,����Ա,�ƶ�ͨ�Ż���Ա,����ʦ,ͨ�Ź���ʦ,��ȫԱA��,��ȫԱB��,��ȫԱC��,�Ǹ߼��ӹ�,���޿ռ���ҵ,��ѹ�繤��ҵ,��ѹ�繤��ҵ,���������и���ҵ,������յ���ҵ,��ר,����,��ר,����,˶ʿ,��ʿ,������������ʦ,�ǻۼ�ͥ����ʦ";
			int shu = matchStringByIndexOf(m, fileName.split("\\.")[0].split("-")[2].toString());
			if (shu < 1) {
				model.put("msg", fileName + "֤�����Ʋ�����Ҫ��!");
				model.put("flag", false);
				model.put("code", "2");
				model.put("eror", "�ϴ�ʧ��!");
			} else {
				if (c == null || c.size() == 0) {
					model.put("msg", fileName + "û���ҵ���Ӧ֤��!");
					model.put("flag", false);
					model.put("code", "3");
					model.put("eror", "�ϴ�ʧ��!");
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
						model.put("msg", "����"+fileName+"�ɹ�");
					}else {
						model.put("msg", model.get("msg")+"/n"+"����"+fileName+"�ɹ�");
					}
					
					success++;
					ImageByteUtil.copy(war.getInputStream(e),new FileOutputStream(new File(filepath + fileName)));
				}
			}
		}
	}
} else {
	model.put("msg", "û����Ƭ");
}
war.close();
all=all-success;
model.put("success", "�ɹ�"+success+"����Ƭ��ʧ��"+all+"����Ƭ");
logService.addOperationLog(MenuName, null, success, 8);

return JsonUtil.toJson(model);
