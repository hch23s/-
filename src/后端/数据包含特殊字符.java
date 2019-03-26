package 后端;

public class 数据包含特殊字符 {
	public  String replace(String replace) {
		if(replace.contains("\"")) {
			replace=replace.replaceAll("\"", "“");
	    }
	    if(replace.contains(",")) {
	    	replace=replace.replaceAll(",", "，");
	    }
	    if(replace.contains("\n")) {
	    	replace=replace.replaceAll("\\n", " ");
	    }
	    if(replace.contains("\r\n")) {
	    	replace=replace.replaceAll("\\r\\n", " ");
	    }
		return replace;
	}
}
