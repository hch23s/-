package 前段;

public class 前段 {
	<button type="button" class="layui-btn layui-btn-sm" onclick="javascript:history.back(-1);">返回</button>
	
	//layui中显示到月份的
	 laydate.render({
         elem: '#createTime' //指定元素
         ,type: 'month'
         ,isInitValue:'true'
         ,btns: ['clear', 'confirm']
         ,done: function(value, date, endDate){
             console.info(value); //得到日期生成的值，如：2017-08-18
             console.info(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
             console.info(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
             $("#createTime").val(value);
         }
     });
	 
	 //显示到日期的
	   laydate.render({
//         elem:"#createTime"
//         ,trigger: 'click'
//     })
}
	   
	   //范围时间
	   laydate.render({
       	elem:"#finishDate1",
		    range: "~"
       });
	   
	   
	   
	   
	   
	   
	   
	   
	   
