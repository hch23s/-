package ǰ��;

public class ҳ����ת�ȴ� {
	function detail(){
  		layer.confirm("ȷ���ѵ�ǰ���ݽ������¼�����?",{icon:3,title:'��ʾ'},function(index){
  			window.location.href="/pigeonholeCable";
  			 layer.open({
                 type: 3
                 , shade: 0.01
                 , content: '�ϴ���'
             });
  			layer.close(index);
  		});
	}
}
