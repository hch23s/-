package ���;

public class ��б�ܵ����� {

	public static void main(String[] args) {
		String s="sds\\s";
		System.out.println(s);
		if(s.contains("\\")) {
			s = s.replaceAll("\\\\", "\\\\\\\\");
		}
		int y=10%2;
		System.out.println(s);
	}
}
