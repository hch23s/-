package 配置文件;

public class yml {
	spring:
		  http:
		    multipart:
		      enabled: true
		      max-file-size: 3MB(这里是限制的文件大小)
		      max-request-size: 3MB(这里是限制的文件大小)
}
