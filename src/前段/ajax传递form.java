package ǰ��;

public class ajax����form {
	var result = $('#form_parameters').serializeArray();  //��ȡ�����ݸ�ֵ��result�������ֵ����"name","nameֵ"; "value","valueֵ"��Map�������   
    
	 var resultJSON = {};                                 //����json����
	  for (var i = 0;i<result.length;i++){
	       resultJSON[result[i].name] = result[i].value; //ͨ��ѭ������װjson����ʽΪ{"nameֵ1":"valueֵ1","nameֵ2":"valueֵ2"��...}
	     }  
}
$.ajax({
    type: "POST",                  //�ύ��ʽ
    dataType: "json",              //Ԥ�ڷ��������ص���������
    url: "/static/action" ,          //Ŀ��url
    data: resultJSON, //�ύ������
    success: function (result) {
    if(result.code == 0){
        alert(result.msg);
        return;
    }
    if(result.available ==1){
        alert(result.msg);
    }

 error : function() {
        alert("�쳣��");
    }
���ƴ���
