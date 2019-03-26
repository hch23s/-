package 后端;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class sql修改 {
	//把数据库中的某个字段去除
	update cable set corresponding=replace(corresponding,'"','');
	
	//查询数据库中某个字段重复的数据
	select * from cable where cable_no in(
			select cable_no from cable GROUP BY cable_no having count(*)>1);
	
	//更改数据库 要加一个事物@Transactional
    private EntityManager entityManager;
    int c=0;
	try {
		String s="select zzzz from boxs";
		
		entityManager.createNativeQuery(s).getResultList();
		c++;
	} catch (Exception e) {
		
	}
if(c==0) {
	String s ="alter table boxs add column zzzz varchar(30);";//给表添加一个字段
	
	 entityManager.createNativeQuery(s).executeUpdate();
}

}
