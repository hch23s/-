package ���;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class sql�޸� {
	//�����ݿ��е�ĳ���ֶ�ȥ��
	update cable set corresponding=replace(corresponding,'"','');
	
	//��ѯ���ݿ���ĳ���ֶ��ظ�������
	select * from cable where cable_no in(
			select cable_no from cable GROUP BY cable_no having count(*)>1);
	
	//�������ݿ� Ҫ��һ������@Transactional
    private EntityManager entityManager;
    int c=0;
	try {
		String s="select zzzz from boxs";
		
		entityManager.createNativeQuery(s).getResultList();
		c++;
	} catch (Exception e) {
		
	}
if(c==0) {
	String s ="alter table boxs add column zzzz varchar(30);";//�������һ���ֶ�
	
	 entityManager.createNativeQuery(s).executeUpdate();
}

}
