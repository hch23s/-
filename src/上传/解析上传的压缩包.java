package 上传;

public class 解析上传的压缩包 {
	public class FileUtils {
		 
		//日志
	 
		private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
	 
		/**
		 * 对zip类型的文件进行解压
		 *
		 * @author mmy
		 * @date 2018年1月8日
		 */
		public static List<FileModel> unzip(MultipartFile file) {
			// 判断文件是否为zip文件
			String filename = file.getOriginalFilename();
			if (!filename.endsWith("zip")) {
				LOGGER.info("传入文件格式不是zip文件" + filename);
				new BusinessException("传入文件格式错误" + filename);
			}
			List<FileModel> fileModelList = new ArrayList<FileModel>();
			String zipFileName = null;
			// 对文件进行解析
			try {
				ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream(), Charset.forName("GBK"));
				BufferedInputStream bs = new BufferedInputStream(zipInputStream);
				ZipEntry zipEntry;
				byte[] bytes = null;
				while ((zipEntry = zipInputStream.getNextEntry()) != null) { // 获取zip包中的每一个zip file entry
					zipFileName = zipEntry.getName();
					Assert.notNull(zipFileName, "压缩文件中子文件的名字格式不正确");
					FileModel fileModel = new FileModel();
					fileModel.setFileName(zipFileName);
					bytes = new byte[(int) zipEntry.getSize()];
					bs.read(bytes, 0, (int) zipEntry.getSize());
					InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
					fileModel.setFileInputstream(byteArrayInputStream);
					fileModelList.add(fileModel);
				}
			} catch (Exception e) {
				LOGGER.error("读取部署包文件内容失败,请确认部署包格式正确:" + zipFileName, e);
				new BusinessException("读取部署包文件内容失败,请确认部署包格式正确:" + zipFileName);
			}
			return fileModelList;
		}
	 
	}
	public class FileModel implements Serializable{
		private static final long serialVersionUID = 13846812783412684L;
		String fileName;			//解压后文件的名字
		String fileType;			//文件类型
		InputStream fileInputstream;		//解压后每个文件的输入流
	 
	 
		public String getFileName() {
			return this.fileName;
		}
	 
	 
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	 
	 
		public String getFileType() {
			return this.fileType;
		}
	 
	 
		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
	 
	 
		public InputStream getFileInputstream() {
			return this.fileInputstream;
		}
	 
	 
		public void setFileInputstream(InputStream fileInputstream) {
			this.fileInputstream = fileInputstream;
		}
	 
	 
		public String toString() {
			return "FileModel{fileName=\'" + this.fileName + '\'' + ", fileType=\'" + this.fileType + '\''
					+ ", fileInputstream=" + this.fileInputstream + '}';
		}
	}


}
