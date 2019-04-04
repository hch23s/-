package 前段;

public class ajax传递form {
	var result = $('#form_parameters').serializeArray();  //获取表单内容赋值给result，表单里的值是以"name","name值"; "value","value值"的Map数组存在   
    
	 var resultJSON = {};                                 //定义json对象
	  for (var i = 0;i<result.length;i++){
	       resultJSON[result[i].name] = result[i].value; //通过循环，组装json，格式为{"name值1":"value值1","name值2":"value值2"，...}
	     }  
}
$.ajax({
    type: "POST",                  //提交方式
    dataType: "json",              //预期服务器返回的数据类型
    url: "/static/action" ,          //目标url
    data: resultJSON, //提交的数据
    success: function (result) {
    if(result.code == 0){
        alert(result.msg);
        return;
    }
    if(result.available ==1){
        alert(result.msg);
    }

 error : function() {
        alert("异常！");
    }
复制代码
