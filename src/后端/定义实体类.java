package ���;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.qmhd.telecom.entity.TaskItemCategory;

public class ����ʵ���� {
	
	//��string��֪���೤ʱ
	@Column(columnDefinition="TEXT") 
	//���ó���
	@Column(length=30) 
}
//�������
@ManyToOne
@JoinColumn(name = "t_task_item_category_id")
private TaskItemCategory taskItemCategory;

//ʱ������
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
private Date    remindTime;              // ����ʱ��

���ñ���
@Entity
@Table(name = "t_task")
public class Task {
//��������
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;




