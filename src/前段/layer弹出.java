package 前段;

public class layer弹出 {
	layer.msg("请勾选你要删除的数据！",{offset: '150px'});//offset设置里顶处的距离
	
	

	
	function exportExl(){
  		layer.confirm("确定导出数据?",{icon:3,title:'提示'},function(index){
  			window.location.href="/boxs/exportBoxs";
  			$("#form2").attr("action","/boxs/exportBoxs");//可以吧from表单的数据带上
  		    $("#form2").submit();
  			layer.close(index);
  		});
  	}
	
	function check(physicalOffice) {
   		var physicalOffice=encodeURIComponent(physicalOffice);
        var url =  "/room/detail?physicalOffice=" +physicalOffice ;
        Layer.open({
            type: 2 //此处以iframe举例
            , title: "综合机房详情"
            , skin: 'layui-layer-rim'
            , area:[(HomeWidth + 20) + 'px', '600px']
            , shade: 0.5
            , maxmin: true
            , content: url
            , success: function (layero) {

            }
        });
    }
  	
	
	function importModel(){
  		importIndex = layer.open({
  			type:1,
  			shade:0,
  			title:'导入',
  			area:['600px','450px'],
  			shadeClose:true,//点击遮罩关闭;
  			offset:['45px'],
  			content: $('#importModal')
  		})
  	}
	<!-- 导入弹出框 -->
	<div id="importModal" style="display: none; padding: 20px;">
		<div class="row">
			<p class="help-block">
				请按照模版上传文件 <a href="../document/管道模板.csv" type="button"
					class="layui-btn layui-btn-small" style="color: white;">下载管道模板</a>

			</p>
		</div>
		<br>
		<div class="layui-box layui-upload-button">
			<button class="layui-btn layui-btn-small" id="test1">
				<i class="layui-icon">&#xe62f;</i>导入
			</button>
		</div>
	</div>
}
