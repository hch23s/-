package ǰ��;

public class ǰ��ֻ�����������ֵ� {

	<input type="text" name="number"  placeholder="����������"lay-verify="" value="${(room.number)!''}"  
    		onkeyup="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
            onafterpaste="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
            class="layui-input">
}
