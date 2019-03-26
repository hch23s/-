package ���;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * CSV����(�����͵���)
 */
public class CSVUtils {

	/**
	 * ����
	 *
	 * @param file     csv�ļ�(·��+�ļ���)��csv�ļ������ڻ��Զ�����
	 * @param dataList ����
	 * @param heads    ��ͷ
	 * @return
	 */
	public static boolean exportCsv(File file, List<String> dataList, String heads) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			return exportCsvByOS(out, dataList, heads);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * ����
	 *
	 * @param out      �����
	 * @param dataList ����
	 * @param heads    ��ͷ
	 * @return
	 */
	public static boolean exportCsvByOS(OutputStream out, List<String> dataList, String heads) {
		boolean isSucess = false;

		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			osw = new OutputStreamWriter(out, "GBK");
			bw = new BufferedWriter(osw);
			//ѭ����ͷ
			if (heads != null && !heads.equals("")) {
				bw.append(heads).append("\r");
			}
			//ѭ������
			if (dataList != null && !dataList.isEmpty()) {
				for (String data : dataList) {
					bw.append(data).append("\r");
				}
			}
			isSucess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSucess = false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return isSucess;
	}

	/**
	 * ����
	 *
	 * @param is csv�ļ�(·��+�ļ�)
	 * @return
	 */
	public static List<String> importCsv(InputStream is) {//ʹ��Set���˵��ظ������ݣ���̨�����ظ��Ļ�������
		List<String> dataList = new LinkedList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, "GBK"));
			String line;
			String data = "";
			while ((line = br.readLine()) != null) {
				data += line;
				int count = StringUtils.count(data, "\"");
				if (count % 2 != 0) {
					continue;
				}
				dataList.add(data);
				data = "";
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}

	public static Iterable<CSVRecord> importCsv(String filename) throws IOException {
		// Ĭ��ʹ��UTF-8����
//		Reader in = new FileReader(filename);

		String charset = getFileCharset(filename);
//		System.out.println(charset);

		// ָ������
		InputStream inputStream = new FileInputStream(filename);
		Reader in = new BufferedReader(new InputStreamReader(inputStream, charset));
		Iterable<CSVRecord> dataSet = CSVFormat.EXCEL.parse(in);
//		return CSVFormat.EXCEL.parse(in);
		return dataSet;
	}

	/**
	 * ���ڵ����豸����Ϊͬһ�����п����ж�̨��ͬ���豸
	 *
	 * @param is
	 * @return
	 */
	public static List<String> importCsv2(InputStream is) {  //���ڵ����豸����Ϊͬһ�����п����ж�̨��ͬ���豸
		List<String> dataList = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, "GBK"));
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}

	/**
	 * CSV�ļ�����
	 *
	 * @param response
	 * @param dataList ��CSV�ļ���ʽ����õĴ� ","�ָ����Ҵ�\r\n���е����ݼ���
	 * @param fileName
	 * @throws IOException
	 */
	public static void CSVFileExport(HttpServletResponse response, List<String> dataList, String fileName) throws IOException {
		// ����response��Header
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
//            response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream");
		OutputStream os = response.getOutputStream();
		for (int i = 0; i < dataList.size(); i++) {
			os.write(dataList.get(i).getBytes("GBK"));//CSV�ļ���GBK����
		}

		os.flush();
		os.close();
	}
	/**
	 * CSV�ļ�����
	 * @param response
	 * @param dataList ��CSV�ļ���ʽ����õĴ� ","�ָ����Ҵ�\r\n���е����ݼ���
	 * @param fileName
	 * @throws IOException
	 */
//        public static void CSVFileExport(List<String> dataList,String fileName) throws IOException{
//        
//            OutputStream os = response.getOutputStream();
//            for (int i = 0; i < dataList.size(); i++) {
//    			os.write(dataList.get(i).getBytes("GBK"));//CSV�ļ���GBK����
//    		}
//            os.flush();
//            os.close();
//        }

	/**
	 * ��ȡcsv��ʽ���ļ���GBK�ַ���
	 *
	 * @param is
	 * @return
	 * @title csv
	 * @author ��� 2017��9��6��
	 * @description
	 */
	public static List<Map<String, Object>> copycsv(InputStream is) {
		List<Map<String, Object>> rs = new ArrayList<>();
		try {
			rs = read(is);
		} catch (IOException e) {
//    			 logger.error(">>>>>>>>>>����CSV�ļ�����<<<<<<<<<<", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rs;
	}

	private static List<Map<String, Object>> read(InputStream is) throws IOException {
		List<String> header = new ArrayList<>();
		List<Map<String, Object>> rs = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "GBK"));
		String line = null;
		int index = 0;
		while ((line = reader.readLine()) != null) {
			String[] ss = line.split(",");
			if (index == 0) {
				header = Arrays.asList(ss);
			} else {
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < ss.length; i++) {
					map.put(header.get(i), ss[i]);
				}
				rs.add(map);
			}
			index++;
		}
		is.close();
		return rs;
	}

	private static String getFileCharset(String filename) throws IOException {
		InputStream input = new FileInputStream(filename);
		byte[] bs = new byte[2];
		input.read(bs, 0, bs.length);

//		System.out.println(Hex.encodeHexString(bs));
		switch (Hex.encodeHexString(bs)) {
			case "efbb": // UTF-8
				return "UTF-8";
			case "fffe": // Unicode
				return "Unicode";
			case "feff": // ���Unicode
				return "UnicodeBig";
			default:
				return "GBK";
		}
	}
}