package ���;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ��ȡʱ������һ�� {
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String firstDay = "";
		String lastDay = "";
		// ��ȡǰ�µĵ�һ��
		Calendar cal_1 = Calendar.getInstance();// ��ȡ��ǰ����
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// ����Ϊ1��,��ǰ���ڼ�Ϊ���µ�һ��
		firstDay = format.format(cal_1.getTime());
		System.out.println("-----1------firstDay:" + firstDay);
		// ��ȡǰ�µ����һ��
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 0);// ����Ϊ1��,��ǰ���ڼ�Ϊ���µ�һ��
		lastDay = format.format(cale.getTime());
		System.out.println("-----2------lastDay:" + lastDay);

		// ��ȡ��ǰ�µ�һ�죺
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);// ����Ϊ1��,��ǰ���ڼ�Ϊ���µ�һ��
		String first = format.format(c.getTime());
		System.out.println("===============first:" + first);

		// ��ȡ��ǰ�����һ��
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		System.out.println("===============last:" + last);
	}
}
