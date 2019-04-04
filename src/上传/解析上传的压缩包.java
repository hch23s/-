package �ϴ�;

public class �����ϴ���ѹ���� {
	public class FileUtils {
		 
		//��־
	 
		private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
	 
		/**
		 * ��zip���͵��ļ����н�ѹ
		 *
		 * @author mmy
		 * @date 2018��1��8��
		 */
		public static List<FileModel> unzip(MultipartFile file) {
			// �ж��ļ��Ƿ�Ϊzip�ļ�
			String filename = file.getOriginalFilename();
			if (!filename.endsWith("zip")) {
				LOGGER.info("�����ļ���ʽ����zip�ļ�" + filename);
				new BusinessException("�����ļ���ʽ����" + filename);
			}
			List<FileModel> fileModelList = new ArrayList<FileModel>();
			String zipFileName = null;
			// ���ļ����н���
			try {
				ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream(), Charset.forName("GBK"));
				BufferedInputStream bs = new BufferedInputStream(zipInputStream);
				ZipEntry zipEntry;
				byte[] bytes = null;
				while ((zipEntry = zipInputStream.getNextEntry()) != null) { // ��ȡzip���е�ÿһ��zip file entry
					zipFileName = zipEntry.getName();
					Assert.notNull(zipFileName, "ѹ���ļ������ļ������ָ�ʽ����ȷ");
					FileModel fileModel = new FileModel();
					fileModel.setFileName(zipFileName);
					bytes = new byte[(int) zipEntry.getSize()];
					bs.read(bytes, 0, (int) zipEntry.getSize());
					InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
					fileModel.setFileInputstream(byteArrayInputStream);
					fileModelList.add(fileModel);
				}
			} catch (Exception e) {
				LOGGER.error("��ȡ������ļ�����ʧ��,��ȷ�ϲ������ʽ��ȷ:" + zipFileName, e);
				new BusinessException("��ȡ������ļ�����ʧ��,��ȷ�ϲ������ʽ��ȷ:" + zipFileName);
			}
			return fileModelList;
		}
	 
	}
	public class FileModel implements Serializable{
		private static final long serialVersionUID = 13846812783412684L;
		String fileName;			//��ѹ���ļ�������
		String fileType;			//�ļ�����
		InputStream fileInputstream;		//��ѹ��ÿ���ļ���������
	 
	 
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
