package 前段;

public class js {
	
	
     //鼠标焦点
	$(document).ready(function(){
	  $("input").focus(function(){
	    $("input").css("background-color","#FFFFCC");
	  });
	  $("input").blur(function(){
	    $("input").css("background-color","#D6D6FF");
	  });
	});
	</script>
	</head>
	<body>
	Enter your name: <input type="text" />
	<p>请在上面的输入域中点击，使其获得焦点，然后在输入域外面点击，使其失去焦点。</p>
	</body>
	</html>
}
