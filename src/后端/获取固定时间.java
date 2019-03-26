package 后端;

import java.util.Calendar;
import java.util.Date;

public class 获取固定时间 {
	 Calendar c = Calendar.getInstance();
     c.set(Calendar.HOUR_OF_DAY, 21);
     c.set(Calendar.MINUTE, 0);
     c.set(Calendar.SECOND, 0);
     Date m6 = c.getTime();
}
