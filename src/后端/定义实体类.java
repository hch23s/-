package 后端;

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

public class 定义实体类 {
	
	//当string不知道多长时
	@Column(columnDefinition="TEXT") 
	//设置长度
	@Column(length=30) 
}
//设置外键
@ManyToOne
@JoinColumn(name = "t_task_item_category_id")
private TaskItemCategory taskItemCategory;

//时间类型
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
private Date    remindTime;              // 提醒时间

设置表名
@Entity
@Table(name = "t_task")
public class Task {
//主键自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;




