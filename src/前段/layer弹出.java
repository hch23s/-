package ǰ��;

public class layer���� {
	layer.msg("�빴ѡ��Ҫɾ�������ݣ�",{offset: '150px'});//offset�����ﶥ���ľ���
	
	

	
	function exportExl(){
  		layer.confirm("ȷ����������?",{icon:3,title:'��ʾ'},function(index){
  			window.location.href="/boxs/exportBoxs";
  			$("#form2").attr("action","/boxs/exportBoxs");//���԰�from�������ݴ���
  		    $("#form2").submit();
  			layer.close(index);
  		});
  	}
	
	function check(physicalOffice) {
   		var physicalOffice=encodeURIComponent(physicalOffice);
        var url =  "/room/detail?physicalOffice=" +physicalOffice ;
        Layer.open({
            type: 2 //�˴���iframe����
            , title: "�ۺϻ�������"
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
  			title:'����',
  			area:['600px','450px'],
  			shadeClose:true,//������ֹر�;
  			offset:['45px'],
  			content: $('#importModal')
  		})
  	}
	<!-- ���뵯���� -->
	<div id="importModal" style="display: none; padding: 20px;">
		<div class="row">
			<p class="help-block">
				�밴��ģ���ϴ��ļ� <a href="../document/�ܵ�ģ��.csv" type="button"
					class="layui-btn layui-btn-small" style="color: white;">���عܵ�ģ��</a>

			</p>
		</div>
		<br>
		<div class="layui-box layui-upload-button">
			<button class="layui-btn layui-btn-small" id="test1">
				<i class="layui-icon">&#xe62f;</i>����
			</button>
		</div>
	</div>
}
