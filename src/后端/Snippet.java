package ���;

public class Snippet {
	
	<script type="text/javascript">
		var fileNames;
		 $(function () {
		    	
		    	<#if room??>
		    	fileNames = '${room.imgUrl!''}';
		    	
		    	if (fileNames != ''&& typeof(fileNames) != 'undefined') {
		    		 
		    	    if (!fileNames.endsWith(";")) {
		    	        fileNames = fileNames +";";
		    	    }
		    	}
		    	</#if>
		    	
		    	
		layui.use(['laypage','form','layer','tree','laydate','upload'], function(){
			   var laypage = layui.laypage;
			  var form = layui.form;
			  var layer = layui.layer;
			  var laydate = layui.laydate;
			  var upload = layui.upload;
		    	//ͼƬ�ϴ�
	      	  upload.render({
	                elem: '#test2'
	                ,url: '/fileupload/uploadImg/?${_csrf.parameterName}=${_csrf.token}'
	                ,data:{flag:1}
	                ,multiple: true
	                ,exts: 'jpg|png|bmp'
	                ,number:10
	                ,before: function(obj){
	                    //Ԥ�������ļ�ʾ������֧��ie8
	                    obj.preview(function(index, file, result){
	                        $('#demo2').append('<div class="layui-inline layui-form" id=""> ' +
	                                '<img onclick="bigRoom()" src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img"  height="100px" width="100px"><br/>' +
	                                '<input type="checkbox" name=""  lay-skin="primary" value="" > </div>'+
	                                '<div id="bigimgRoom" style="display: none;padding: 10px;">'+
								'<img alt="ͼƬ����" id="xuanzhuan" src="'+ result +'" alt="'+ file.name +'" height="300px" width="300px">'+
							'</div>'
	                      		 );
	                        form.render();
	                    });
	                }
	                ,allDone: function(obj){
	                    //���ļ�ȫ�����ύ�󣬲Ŵ���
	                    // console.info("------------------");
	                    // console.info(obj);
	                    // console.info(fileNames);
	                    // $("#picture").attr("value",fileNames.substring(0,fileNames.length-1));
	                    $("#picture").val(fileNames.substring(0,fileNames.length-1));
	                }
	                ,done: function(res, index, upload){
	                    //ÿ���ļ��ύһ�δ���һ�Ρ�
	                    // console.info(res);
	                    // console.info(res.path0);
	                    // console.info(index);
	                    // console.info(upload);
	                    if (fileNames == null || typeof(fileNames) == "undefined" || fileNames == ''){
	                        fileNames = res.path0 + ";";
	                    }else {
	                        fileNames = fileNames + res.path0 + ";";
	                    }
	                    console.info(fileNames);
	                    // $("#picture").val(fileNames.substring(0,fileNames.length-1));
	                    $('#demo2 input[type="checkbox"]').each(function(){
	                        var name = $(this).attr("name");
	                        if (name == ''){
	                            $(this).attr("name",res.path0);
	                        }
	
	                    });
	                }
	            });
			});
		});	
	
		 /** ɾ��ͼƬ **/
		    function delImages() {
		        var check_value = [];
		        var check_name = fileNames.split(';');
		        var result = '';
		        $('#demo2 input[type="checkbox"]:checked').each(function () {
		            $(this).parent().hide();
		            var name = $(this).attr("name");
		            check_value.push(name);
		            for (j = 0; j < check_name.length; j++) {
		                if (check_name[j] == name) {
		                    check_name.splice(j, 1);
		                    break;
		                }
		            }
	
		        });
		        $.each(check_name, function (i, val) {
		            if (val != '') {
		                result = result + val + ";";
		            }
		        })
		        console.info(result);
		     
		        if (check_value.length == 0) {
		            layer.msg("�빴ѡҪɾ����ͼƬ��");
		            return;
		        }
		      $.ajax({
		            url: "/room/delimage?id="+${room.id?c}+"&imgUrl="+check_value,
		            dataType: "json",
		            success: function (res) {
		                console.info(res);
		                if (res.status == 0) {
		                }
		                layer.msg(res.msg);
		            }
		        });
		       
		    };
	
		  
		    
		    function bigRoom(){
		    	layui.use(['layer'], function(){
					var layer = layui.layer;
					layer.open({
				 		type: 1,
				 		title: " ",
				 	    area:[ '500px', '500px'],
				 		shade: 0,
				 		shadeClose: true,//������ֹر�
				 		content: $("#bigimgRoom")
				 	});
				});
		    }
		    $(function() {
		    	  var r = 0;
		    	  $('img').click(function() {
		    	    r += 90;
		    	    $(this).css('transform', 'rotate(' + r + 'deg)');
		    	  });
		    	});
		    $(function() {
		  	  var r = 0;
		  	  $('#xuanzhuan').click(function() {
		  	    r += 90;
		  	    $(this).css('transform', 'rotate(' + r + 'deg)');
		  	  });
		  	});
	
		    
		    
		    <#if room?? && (room.imgUrl)??>
			    <#list (room.imgUrl)?split(";") as item>
				    function bigRoom${item_index}(){
				    	layui.use(['layer'], function(){
							var layer = layui.layer;
							layer.open({
						 		type: 1,
						 		area:[ '500px', '500px'],
						 		title: " ",
						 		shade: 0,
						 		content: $("#bigimgRoom${item_index}")
						 	});
						});
				    }
				    
				    var num = 0;
				    $("#input${item_index}").click(function(){
				    num ++;
				    alert(1);
				    console.info(2);
				    $("#xuanzhuan${item_index}").rotate(90*num);
				    });
				    
			    </#list>
			   
		    </#if>
	
	
	
	</script>
	<body>
	<blockquote class="layui-elem-quote">
	<b>������Ϣ</b>
	</blockquote>
	
	<input type="hidden" id="picture" name="imgUrl" value="${(room.imgUrl)!''}"/>
	
	<fieldset class="layui-elem-field">
	    <legend>������Ƭ</legend>
		    <div class="layui-field-box">
		    
			    <div class="layui-upload">
			    <legend>ȫ����Ƭ��ÿ�������ռ�һ�ţ�</legend>
			    <button type="button" class="layui-btn" id="test2">�ϴ��ܵ�ͼ</button>
	
		        <button type="button" class="layui-btn" id="" onclick="delImages();" style="margin-left:30px;">ɾ��</button>
		        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;margin-left:30px;">
		            Ԥ��ͼ��
		            <div class="layui-upload-list" id="demo2">
		            <#if room?? && (room.imgUrl)??>
		                <#list (room.imgUrl)?split(";") as item>
		                <div class="layui-inline" id="${item_index}">
		                    <#if item !=''>
		                        <img onclick="bigRoom${item_index}()" alt="ͼƬ����" src="/upload/${item}" height="100px" width="100px" class="layui-upload-img" >
		                        <br/>
		                        <input type="checkbox" name="${item}"  lay-skin="primary" value="${item_index}" >
		                        <div id="bigimgRoom${item_index}" style="display: none;padding: 10px;">
									<img alt="ͼƬ����" id="xuanzhuan${item_index}" src="/upload/${item}">
								</div>
		                    </#if>
		                </div>
		                </#list>
		            </#if>
		        </div>
	        </blockquote>
	        </div>
	        
	        
	        <div class="layui-upload">
			    <legend>�Ŵ���Ƭ</legend>
		        <button type="button" class="layui-btn" id="" onclick="delImages();" style="margin-left:30px;">ɾ��</button>
		        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;margin-left:30px;">
		            Ԥ��ͼ��
		            <div class="layui-upload-list" id="demo2">
		            <#if room?? && (room.imgUrl)??>
		                <#list (room.imgUrl)?split(";") as item>
		                <div class="layui-inline" id="${item_index}">
		                    <#if item !=''>
		                        <img onclick="bigRoom${item_index}()" alt="ͼƬ����" src="/upload/${item}" height="100px" width="100px" class="layui-upload-img" >
		                        <br/>
		                        <input type="checkbox" name="${item}"  lay-skin="primary" value="${item_index}" >
		                        <div id="bigimgRoom${item_index}" style="display: none;padding: 10px;">
									<img alt="ͼƬ����" id="xuanzhuan${item_index}" src="/upload/${item}" >
									<input id="input${item_index}"class="layui-btn"  type="button" value="�����ת90��"></input>
								</div>
		                    </#if>
		                </div>
		                </#list>
		            </#if>
		        </div>
	        </blockquote>
	    </div>
	      
	    <div class="layui-upload">
		    <legend>�ܿ���Ƭ</legend>
	        <button type="button" class="layui-btn" id="" onclick="delImages();" style="margin-left:30px;">ɾ��</button>
	        <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;margin-left:30px;">
	            Ԥ��ͼ��
	            <div class="layui-upload-list" id="demo2">
	            <#if room?? && (room.imgUrl)??>
	                <#list (room.imgUrl)?split(";") as item>
	                <div class="layui-inline" id="${item_index}">
	                    <#if item !=''>
	                        <img onclick="bigRoom${item_index}()" alt="ͼƬ����" src="/upload/${item}" height="100px" width="100px" class="layui-upload-img" >
	                        <br/>
	                        <input type="checkbox" name="${item}"  lay-skin="primary" value="${item_index}" >
	                        <div id="bigimgRoom${item_index}" style="display: none;padding: 10px;">
								<img alt="ͼƬ����" id="xuanzhuan${item_index}" src="/upload/${item}" height="300px" width="300px">
								<input id="input${item_index}"class="layui-btn"  type="button" value="�����ת90��"></input>
							</div>
	                    </#if>
	                </div>
	                </#list>
	            </#if>
	        </div>
	    </blockquote>
	</div>
	
}

