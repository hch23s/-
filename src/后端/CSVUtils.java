package 后端;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * CSV操作(导出和导入)
 */
public class CSVUtils {

	/**
	 * 导出
	 *
	 * @param file     csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList 数据
	 * @param heads    表头
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
	 * 导出
	 *
	 * @param out      输出流
	 * @param dataList 数据
	 * @param heads    表头
	 * @return
	 */
	public static boolean exportCsvByOS(OutputStream out, List<String> dataList, String heads) {
		boolean isSucess = false;

		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			osw = new OutputStreamWriter(out, "GBK");
			bw = new BufferedWriter(osw);
			//循环表头
			if (heads != null && !heads.equals("")) {
				bw.append(heads).append("\r");
			}
			//循环数据
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
	 * 导入
	 *
	 * @param is csv文件(路径+文件)
	 * @return
	 */
	public static List<String> importCsv(InputStream is) {//使用Set过滤掉重复的数据，如台账中重复的机房数据
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
		// 默认使用UTF-8编码
//		Reader in = new FileReader(filename);

		String charset = getFileCharset(filename);
//		System.out.println(charset);

		// 指定编码
		InputStream inputStream = new FileInputStream(filename);
		Reader in = new BufferedReader(new InputStreamReader(inputStream, charset));
		Iterable<CSVRecord> dataSet = CSVFormat.EXCEL.parse(in);
//		return CSVFormat.EXCEL.parse(in);
		return dataSet;
	}

	/**
	 * 用于导入设备，因为同一机房中可能有多台相同的设备
	 *
	 * @param is
	 * @return
	 */
	public static List<String> importCsv2(InputStream is) {  //用于导入设备，因为同一机房中可能有多台相同的设备
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
	 * CSV文件导出
	 *
	 * @param response
	 * @param dataList 按CSV文件格式整理好的带 ","分隔，且带\r\n换行的数据集合
	 * @param fileName
	 * @throws IOException
	 */
	public static void CSVFileExport(HttpServletResponse response, List<String> dataList, String fileName) throws IOException {
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
//            response.addHeader("Content-Length", "" + file.length());
		response.setContentType("application/octet-stream");
		OutputStream os = response.getOutputStream();
		for (int i = 0; i < dataList.size(); i++) {
			os.write(dataList.get(i).getBytes("GBK"));//CSV文件是GBK编码
		}

		os.flush();
		os.close();
	}
	/**
	 * CSV文件导出
	 * @param response
	 * @param dataList 按CSV文件格式整理好的带 ","分隔，且带\r\n换行的数据集合
	 * @param fileName
	 * @throws IOException
	 */
//        public static void CSVFileExport(List<String> dataList,String fileName) throws IOException{
//        
//            OutputStream os = response.getOutputStream();
//            for (int i = 0; i < dataList.size(); i++) {
//    			os.write(dataList.get(i).getBytes("GBK"));//CSV文件是GBK编码
//    		}
//            os.flush();
//            os.close();
//        }

	/**
	 * 读取csv格式的文件，GBK字符集
	 *
	 * @param is
	 * @return
	 * @title csv
	 * @author 李东坡 2017年9月6日
	 * @description
	 */
	public static List<Map<String, Object>> copycsv(InputStream is) {
		List<Map<String, Object>> rs = new ArrayList<>();
		try {
			rs = read(is);
		} catch (IOException e) {
//    			 logger.error(">>>>>>>>>>导入CSV文件出错<<<<<<<<<<", e);
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
			case "feff": // 大端Unicode
				return "UnicodeBig";
			default:
				return "GBK";
		}
	}
}