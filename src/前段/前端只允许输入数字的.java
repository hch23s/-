package 前段;

public class 前端只允许输入数字的 {

	<input type="text" name="number"  placeholder="请输入数字"lay-verify="" value="${(room.number)!''}"  
    		onkeyup="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
            onafterpaste="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
            class="layui-input">
}
