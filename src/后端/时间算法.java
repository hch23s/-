package 后端;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class 时间算法 {

	public static void main(String[] args) throws ParseException {
		 String createTime ="2018-10";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		 Date dateOld=sdf.parse(createTime);//转化时间
		 
		 //获取当前时间
		 String dateNow = sdf.format(new Date());
		 
		 //对两个时间进行相减
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
         
        System.out.println("相差年: " + y);
        System.out.println("相差月: " + m);
        System.out.println("相差天: " + d);
	}
}
