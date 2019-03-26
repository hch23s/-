package 前段;

public class 上传文件夹 {
	<input type="file" webkitdirectory name="fileString" multiple class="file"> 
	       <script>
	       $("input[name=fileString]").change(function() {
	          //var names = [];
				
	          for (var i = 0; i < $(this).get(0).files.length; ++i) {
	             // names.push($(this).get(0).files[i].name);
					//console.log($(this).get(0).files[i].mozFullPath);
					//方式一：
					var filePath = $(this).val();
					console.log(filePath);
					//方式二：
	//				alert($('input[type=file]').val());
	          }
	          //console.log(names);
				//方式三：
	//			alert($("input[name=fileString]").val());
	
	      })
	      
	  </script>
}

