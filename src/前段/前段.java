package ǰ��;

public class ǰ�� {
	<button type="button" class="layui-btn layui-btn-sm" onclick="javascript:history.back(-1);">����</button>
	
	//layui����ʾ���·ݵ�
	 laydate.render({
         elem: '#createTime' //ָ��Ԫ��
         ,type: 'month'
         ,isInitValue:'true'
         ,btns: ['clear', 'confirm']
         ,done: function(value, date, endDate){
             console.info(value); //�õ��������ɵ�ֵ���磺2017-08-18
             console.info(date); //�õ�����ʱ�����{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
             console.info(endDate); //�ý���������ʱ����󣬿�����Χѡ��range: true���Ż᷵�ء������Աͬ�ϡ�
             $("#createTime").val(value);
         }
     });
	 
	 //��ʾ�����ڵ�
	   laydate.render({
//         elem:"#createTime"
//         ,trigger: 'click'
//     })
}
	   
	   //��Χʱ��
	   laydate.render({
       	elem:"#finishDate1",
		    range: "~"
       });
	   
	   
	   
	   
	   
	   
	   
	   
	   
