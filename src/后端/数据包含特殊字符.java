package ���;

public class ���ݰ��������ַ� {
	public  String replace(String replace) {
		if(replace.contains("\"")) {
			replace=replace.replaceAll("\"", "��");
	    }
	    if(replace.contains(",")) {
	    	replace=replace.replaceAll(",", "��");
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
