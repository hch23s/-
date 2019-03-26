package Ç°¶Î;

public class ajax {

	 function delFile(path,index) {
         var td =$("#upload-"+ index);
//         window.location.href = "/upload/delFilesss?path="+path;
         $.ajax({
             url: '/upload/delFilesss?path='+path,
             type: "get",
             success: function (res) {
         	if(res.msg=="É¾³ý³É¹¦") {
	            	$("#upload-"+ index).remove();
	                  layer.msg(res.msg);
         	}else {
         		 layer.msg("É¾³ýÊ§°Ü");
         	}
         }
         });
     }
}
