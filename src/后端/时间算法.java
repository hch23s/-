package ���;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ʱ���㷨 {

	public static void main(String[] args) throws ParseException {
		 String createTime ="2018-10";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		 Date dateOld=sdf.parse(createTime);//ת��ʱ��
		 
		 //��ȡ��ǰʱ��
		 String dateNow = sdf.format(new Date());
		 
		 //������ʱ��������
		 Calendar c = Calendar.getInstance();
		 c.setTime(sdf.parse(createTime));
		 int y1 = c.get(Calendar.YEAR);
        int m1 = c.get(Calendar.MONTH);
         
        c.setTime(sdf.parse(dateNow));
        int y2 = c.get(Calendar.YEAR);
        int m2 = c.get(Calendar.MONTH);
         
        int y = Math.abs(y2 - y1);
        int m = y * 12 + Math.abs(m1-m2);
 
        long d1 = sdf.parse(createTime).getTime();
        long d2 = sdf.parse(dateNow).getTime();
        int d = (int) (Math.abs(d2-d1) / (1000 * 60 * 60 * 24));
         
        System.out.println("�����: " + y);
        System.out.println("�����: " + m);
        System.out.println("�����: " + d);
	}
}
