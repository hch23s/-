package ǰ��;

public class js�ж��ֻ��� {
	function isMobileNumber(phone) {
	    var flag = false;
	    var message = "";
	    var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(15[0-3]{1})|(15[4-9]{1})|(18[0-9]{1})|(199))+\d{8})$/;
	    if (phone == '') {
	        // console.log("�ֻ����벻��Ϊ��");
	        message = "�ֻ����벻��Ϊ�գ�";
	    } else if (phone.length != 11) {
	        //console.log("������11λ�ֻ����룡");
	        message = "������11λ�ֻ����룡";
	    } else if (!myreg.test(phone)) {
	        //console.log("��������Ч���ֻ����룡");
	        message = "��������Ч���ֻ����룡";
	    } else {
	        flag = true;
	    }
	    if (message != "") {
	        // alert(message);
	    }
	    return flag;
	}

}
