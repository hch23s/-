package 前段;

public class 页面加载全部 {
	document.onreadystatechange = function () {
        if (document.readyState == "complete") {
            document.body.style.display = "block";
        } else {
            document.body.style.display = "none";
        };
    };
}
