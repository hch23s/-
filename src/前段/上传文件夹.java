package ǰ��;

public class �ϴ��ļ��� {
	<input type="file" webkitdirectory name="fileString" multiple class="file"> 
	       <script>
	       $("input[name=fileString]").change(function() {
	          //var names = [];
				
	          for (var i = 0; i < $(this).get(0).files.length; ++i) {
	             // names.push($(this).get(0).files[i].name);
					//console.log($(this).get(0).files[i].mozFullPath);
					//��ʽһ��
					var filePath = $(this).val();
					console.log(filePath);
					//��ʽ����
	//				alert($('input[type=file]').val());
	          }
	          //console.log(names);
				//��ʽ����
	//			alert($("input[name=fileString]").val());
	
	      })
	      
	  </script>
}

