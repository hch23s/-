package 前段;

public class 页面跳转等待 {
	function detail(){
  		layer.confirm("确定把当前数据进行重新计算吗?",{icon:3,title:'提示'},function(index){
  			window.location.href="/pigeonholeCable";
  			 layer.open({
                 type: 3
                 , shade: 0.01
                 , content: '上传中'
             });
  			layer.close(index);
  		});
	}
}
