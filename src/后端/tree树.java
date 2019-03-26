package ���;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qmhd.telecom.common.JsonUtil;
import com.qmhd.telecom.entity.Dept;
import com.qmhd.telecom.entity.Users;

public class tree�? {

	public void deptTreeRoutine(Map<String, Object> model, Users user) {
		List<Dept> deptList = new ArrayList<>();
		String js = null;

		String name = user.getDept().getName();
		if (name.equals("运维管理�?")) {
			deptList = this.findByLevel(1);
		} else {
			deptList = this.findByPids(user.getDept().getId());
		}
		Map<String, Object> map1 = new HashMap<>();
		List<Object> list1 = new ArrayList<>();

		// tree 第一�? 部门
		for (Dept dept : deptList) {
			map1 = new HashMap<>();
			map1.put("name", dept.getName());
			List<Object> postNature = this.getPostNature(dept.getName());
			map1.put("children", postNature);
			list1.add(map1);
		}

		js = JsonUtil.toJsonWithFields(list1);
		// js = JsonUtil.toJsonWithFields(deptList, "id", "name", "children","pid");

		model.put("isDept", 0);

		model.put("deptJson", js);
		model.put("deptList", deptList);
	}

	private List<Object> getPostNature(String deptName) {
		// 第二层的领导，支撑人员，网格负责�?
		List<Object> list2 = new ArrayList<>();

		Map<String, Object> map2 = new HashMap<>();

		map2 = new HashMap<>();
		map2.put("name", "领导");
		List<Object> userName = this.getUserName(map2.get("name").toString(), deptName);
		map2.put("children", userName);
		list2.add(map2);

		map2 = new HashMap<>();
		map2.put("name", "支撑人员");
		userName = this.getUserName(map2.get("name").toString(), deptName);
		map2.put("children", userName);
		list2.add(map2);

		map2 = new HashMap<>();
		map2.put("name", "网格负责�?");
		userName = this.getUserName(map2.get("name").toString(), deptName);
		map2.put("children", userName);
		list2.add(map2);
		return list2;

	}

	/**
	 * 获取人员
	 * 
	 * @param object
	 * @param deptName
	 */
	private List<Object> getUserName(String postNature, String deptName) {
		List<Object> list3 = new ArrayList<>();
		Map<String, Object> map3 = new HashMap<>();

		List<Users> findByPost = userService.findByPost(postNature, deptName);
		for (Users users : findByPost) {
			map3 = new HashMap<>();
			map3.put("name", users.getName());
			map3.put("userName", users.getUserName());
			list3.add(map3);
		}
		return list3;

	}
	
	 if(isDept==0){
   	  if($('#deptName').val() != null) {
   		  $('#deptName').val($('#deptName').val()+","+treeNode.getParentNode().getParentNode().name);
   	  }
   	  
   	  if($('#areaCenter').val() != null) {
   		  $('#areaCenter').val($('#areaCenter').val()+","+treeNode.getParentNode().name);
   	  }
   	  
   	  if($('#createrName').val() != null) {
   		  $('#createrName').val($('#createrName').val()+","+treeNode.name);
   	  }
   	  
   	  if($('#creater').val() != null) {
   		  $('#creater').val($('#creater').val()+","+treeNode.userName);
   	  }
   		  
   }
	
}

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>52周例行维护任务添�?</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <script type="text/javascript" src="/jquery/jquery-3.2.1.js"></script>

    <link rel="stylesheet" type="text/css" href="/ztree/css/metroStyle/metroStyle.css">
    <script type="text/javascript" src="/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="/ztree/js/jquery.ztree.excheck.min.js"></script>

    <script src="/layui/layui.js" charset="utf-8"></script>
    <!-- 注意：如果你直接复制�?有代码到本地，上述css路径�?要改成你本地�? -->
    <link type="text/css" rel="stylesheet" href="../css/machineRoom/machineRoom.css">
    <script type="text/javascript">


        var zTreeObj;
        var openIndex;
        var form;
        var userList;
        
        
           var setting = {
//        	                data : {   
//        	                    simpleData : {
//        	                        enable : true, //设置启用�?单数据格式[{id, pId, name}, {id, pId, name}]   
//        	    		    idKey : "id",  //节点数据中保存唯�?标识的属性名�?
//        	    		    pIdKey : "pId",  //节点数据中保存其父节点唯�?标识的属性名�?
//        	    	            rootPId : null,  //根节点id
//        	                        type: "type"
//        	                    }
//        	                },
//        		 callback: {
//          onClick: doSetSelectedNode
//      },
        	                check: {
        	                    enable: true,   //true / false 分别表示 显示 / 不显�? 复�?�框或单选框
        	    		autoCheckTrigger: true,   //true / false 分别表示 触发 / 不触�? 事件回调函数
        	    		chkStyle: "checkbox",   //勾�?�框类型(checkbox �? radio�?
        	    		chkboxType: { "Y": "s", "N": "s" }   //勾�?? checkbox 对于父子节点的关联关�?
        	                    /*
        	                        Y: 选择复�?�框�?
        	                        N: 取消复�?�框选中
        	                        p: 父级
        	                        s: 子级
        	                    */
        	                }
        	            };


        function openDept() {
        	 if($("#name").val()=="") {
             	layer.msg("请先选择任务名称");
             	return false;
             }
            openIndex = layer.open({
                type: 1,
                id: 'LAY_openIndex', //设定�?个id，防止重复弹�?
                shade: 0,
                title: '选择部门',
                area: ['400px', '360px'],
                shadeClose: true,//点击遮罩关闭
                content: $('#deptSelect'),
                btn:['确定', '取消'],
            	    yes: function doSetSelectedNode (index, layero){
            	 		 var deptName = "";
        				 var post = "";  
            	         var creater = "";
            	         var createrName = "";
                          
                
                        // 1.获得选中的的节点对象
                        var nodes = zTreeObj.getCheckedNodes(true);    // 与无复�?�框函数名有区别
                		console.info(nodes);
                        for ( var i in nodes) {
                            // 2.只获取用户数�?
			                if(nodes[i].userName!=null  &&nodes[i].userName !="undefined") {
			                	console.info(nodes[i].name);
			                	createrName=nodes[i].name+","+createrName;
			                    console.info(nodes[i].userName);
			                    creater=nodes[i].userName+","+creater;
				                console.info(nodes[i].getParentNode().name);  
				                post= nodes[i].getParentNode().name +","+post;
				                console.info(nodes[i].getParentNode().getParentNode().name); 
				                deptName=nodes[i].getParentNode().getParentNode().name+","+deptName;
	                        }
                        }
                        // 3.通过node节点数据更新页面内容(若此iframe的parent页面只有�?个则不需要[0])
                        $("#createrName").val(createrName.substring(0, createrName.length -1));
                        $("#creater").val(creater.substring(0, creater.length -1));
                        $("#post").val(post.substring(0, post.length -1));
                        $("#deptName").val(deptName.substring(0, deptName.length -1));
                 		layer.close(index);
            	    },
            	    btn2: function(index){
                            layer.close(index);
            	    }
            });
        }


        $(function () {
        	
        
        	
        	
        	
            zTreeObj = $.fn.zTree.init($("#tree"), setting, ${deptJson!'[]'});
//            zTreeObj.expandAll(true);
            console.info( ${deptJson!'[]'});
            
            
            
            layui.use(['laypage', 'layer', 'form', 'upload', 'laydate'], function () {

                $("#userName1").attr("disabled", true);
                $("#userName1").attr("placeholder", "");
                $("#userName1").css({"border": "0px", "margin-top": "0px"});
//           $(".layui-inline").css({"border": "1px", "margin-top": "1px"});

                var laypage = layui.laypage;
                form = layui.form;
                var layer = layui.layer;
                var upload = layui.upload;
                var laydate = layui.laydate;
                laydate.render({
                    elem: '#endTime', //指定元素
                });


                laydate.render({
                    elem: '#createDate', //指定元素
                });

                
            	form.on('select(name)', function(data){

            	    if($("#name").val()=="突击�?�?") {
            	    	   $("#i1").hide();
            	    	   $("#i2").hide();
            	    	   $("#i3").hide();
            	    	   $("#i4").hide();
            	    	   $("#projectNum3").val(0);
            	    	   $("#projectNum2").val(0);
            	    	   
            	    } else {
	            	       $("#i1").show();
	          	    	   $("#i2").show();
	          	    	   $("#i3").show();
	          	    	   $("#i4").show();
            	    }

            	});
                
                //全�??
                form.on('checkbox(allChoose)', function (data) {
                    var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
                    child.each(function (index, item) {
                        item.checked = data.elem.checked;
                    });
                    form.render('checkbox');
                });

                form.on('submit(formDemo)', function (data) {
//			 	    layer.msg(JSON.stringify(data.field));

                    var start = $("#beginTime").val();
                    var end = $("#endTime").val();

                    var startTime = new Date(Date.parse(start));
                    var endTime = new Date(Date.parse(end));
                 
                    if (startTime > endTime) {
                        layer.msg("�?始时间不能大于结束时�?");
                        return false;
                    }


                    $('#form2').attr("action", "/RoutineTasks/saveInspect");
                    $('#form2').submit();
                });



            <#---->
                $("#templateId").find("option[value='${(task.templateId)!''}']").attr("selected", true);
                $("#taskPeriod").find("option[value='${(task.taskPeriod)!''}']").attr("selected", true);
                $("#type").find("option[value='${(task.type)!''}']").attr("selected", true);
                $("#userName").find("option[value='${(task.userName)!''}']").attr("selected", true);
                form.render();
            })

        })

        function back() {
            window.location.href = "/back?n=112";

        }
        
        function chooseRoom() {
            layer.open({
                type: 2,
                shadeClose: true, //点击遮罩关闭
                offset: 't',
                zIndex:999999999,
                shade: 0.5,
                title: "机房信息", //不显示标�?
                maxmin: true, //�?启最大化�?小化按钮
                area: ['900px', '500px'],
                btn: ['确定', '取消'],
                content: '/RoutineTasks/machineRoomList', //捕获的元素，注意：最好该指定的元素要存放在body�?外层，否则可能被其它的相对元素所影响
                cancel: function () {
                    localStorage.clear();
                },
                btn1: function (index, layero) {
                    var officeName = localStorage.getItem("officeName");
                    var jfCode = localStorage.getItem("officeId");
                    var officeAttr = localStorage.getItem("officeAttr");
                    var area = localStorage.getItem("areaCenter");
                    var classGroup = localStorage.getItem("maintenanceTeams");
                    var ids = localStorage.getItem("ids");
                    
                    
                    if ($("#officeName").val() == "undefined" || $("#officeName").val() == '') {
                        $("#officeName").val(officeName);
                        $("#jfCode").val(jfCode);
                        $("#officeAttr").val(officeAttr);
                        $("#area").val(area);
                        $("#classGroup").val(classGroup);
                    }else {
                    	  $("#officeName").val($("#officeName").val()+","+officeName);
                          $("#jfCode").val($("#jfCode").val()+","+jfCode);
                          $("#officeAttr").val( $("#officeAttr").val()+","+officeAttr);
                          $("#area").val( $("#area").val()+","+area);
                          $("#classGroup").val( $("#classGroup").val()+","+classGroup);
                    }
                   
//                      $("#officeName").show();
                    localStorage.clear();
                    layer.close(index);
                },
                btn2: function (index, layero) {
                    localStorage.clear();
                    layer.close(index);
                }
            });
        }
        
        
        

    </script>


</head>

<body>
<form class="layui-form" id="form2" method="post">
    <fieldset class="layui-elem-field " style="margin-top: 20px;">
        <blockquote class="layui-elem-quote layui-text">
        	新增�?查任�?
        </blockquote>
        <div class="layui-field-box">
        
        	<input type="hidden" id="jfCode" name="jfCode" />
        	<input type="hidden" id="officeAttr" name="officeAttr" />
        	<input type="hidden" id="creater" name="creater" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="layui-form-item">
            <div class="layui-row" style='margin-top: 10px;'>
            
                <div class="layui-col-xs3">
                    <label class="layui-label"><span style="color:red">*    </span>任务名称</label>
                    <div class="layui-inline">
                        <select  id="name" name="name" lay-filter="name"  lay-verify="required">
                        	<option value="1">任务名称</option>
                        	<option  value="自查">自查</option>
                        	<option  value="互查">互查</option>
                        	<option  value="突击�?�?">突击�?�?</option>
                        </select>
                    </div>
                </div>
                    
                <div class="layui-col-xs3">
                    <label class="layui-label"><span style="color:red">*    </span>执行周期</label>
                    <div class="layui-inline">
                        <select class="name" id="taskPeriod" name="taskPeriod"lay-verify="required">
                        	<option value="">请�?�择执行周期</option>
             	            <option value="�?">�?</option>
                 	        <option value="�?">�?</option>
                 	        <option value="双月">双月</option>
                 	        <option value="季度">季度</option>
                 	        <option value="半年">半年</option>
                 	        <option value="�?">�?</option>
                 	        <option value="自定义周�?">自定义周�?</option>
                        </select>
                    </div>
                </div>
	                
                    
                <div class="layui-col-xs3">
                    <label class="layui-label"><span style="color:red">*    </span>创建时间</label>
                    <div class="layui-inline">
                        <input type="text" id="createDate" name="createDate1" placeholder="创建时间" lay-verify="required"
                        		readonly      class="layui-input">
                    </div>
                </div>

                <div class="layui-col-xs3">
                    <label class="layui-label"><span style="color:red">*    </span>截止时间</label>
                    <div class="layui-inline">
                        <input  type="text" id="endTime" name="finishDate1" placeholder="截止时间" lay-verify="required"
                        		readonly	      class="layui-input">
                    </div>
                </div>
                
                <div class="layui-form-item">
	                <label class="layui-label" ><span style="color:red">*    </span>�?查部�?</label>
	                <div class="layui-input-block" >
	                    <input  type="text" id="deptName" name="deptName"
	                            placeholder="�?查部�?"  style="width:85%" readonly
	                           class="layui-input " readonly=true onclick="openDept()" lay-verify="required">
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-label" ><span style="color:red">*    </span>岗位性质</label>
	                <div class="layui-input-block" >
	                    <input  type="text" id="post" name="post"
	                            placeholder="根据�?查部门获�?" value="${checkRecord.post!''}" style="width:85%"
	                           class="layui-input "  lay-verify="required" readonly>
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-label" ><span style="color:red">*    </span>执行�?</label>
	                <div class="layui-input-block" >
	                    <input  type="text" id="createrName" name="createrName"
	                            placeholder="根据�?查部门获�?" value="${checkRecord.createrName!''}" style="width:85%"
	                           class="layui-input "  lay-verify="required" readonly>
	                </div>
	            </div>
	            
	            <div class="layui-inline">
	                <label class="layui-label">项目1</label>
	                <div class="layui-inline">
	                    <input type="text" id="project1" name="project1" value="日常管理" 
	                    		readonly      class="layui-input">
	                </div>
	            </div>
	            
	            <div class="layui-inline">
	                <label class="layui-label"><span style="color:red">*    </span>�?查数�?</label>
	                <div class="layui-inline">
	                    <input type="text" id="projectNum1"   name="projectNum1"lay-verify="required"placeholder="请输入数�?"
	                    		onkeyup="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                            onafterpaste="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                    		      class="layui-input">
	                </div>
	                （个/�?/�?  例：�?1/月）
	            </div>
                <br>
	            <div class="layui-inline" name="project" id="i1">
	                <label class="layui-label">项目2</label>
	                <div class="layui-inline">
	                    <input type="text" id="project2" name="project2" value="日常维护" 
	                    		readonly      class="layui-input">
	                </div>
	            </div>
	            
	            <div class="layui-inline" name="project" id="i2">
	                <label class="layui-label"><span style="color:red">*    </span>�?查数�?</label>
	                <div class="layui-inline">
	                    <input type="text" id="projectNum2"  name="projectNum2"lay-verify="required"placeholder="请输入数�?"
	                    		onkeyup="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                            onafterpaste="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                    		      class="layui-input">
	                </div>
	                （个/�?/�?  例：�?1/月）
	            </div>
	                <br>
	            <div class="layui-inline" name="project" id="i3">
	                <label class="layui-label">项目3</label>
	                <div class="layui-inline">
	                    <input type="text" id="project3" name="project3" value="代维管理" 
	                    		readonly      class="layui-input">
	                </div>
	            </div>
	            
	            <div class="layui-inline" name="project" id="i4">
	                <label class="layui-label"><span style="color:red">*    </span>�?查数�?</label>
	                <div class="layui-inline">
	                    <input type="text" id="projectNum3"  name="projectNum3"lay-verify="required"placeholder="请输入数�?"
	                    		onkeyup="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                            onafterpaste="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                    		      class="layui-input">
	                </div>
	                （个/�?/�?  例：�?1/月）
	            </div>
	            
	            
	                
        <!--        <div class="layui-col-xs12">
	                <label class="layui-label"><span style="color:red">*    </span>总检查数�?</label>
	                <div class="layui-inline">
	                    <input type="text" id="projectCount" name="checkRecord.projectCount"lay-verify="required"placeholder="请输入数�?"
	                    		onkeyup="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                            onafterpaste="if(this.value.length==0){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
	                    		      class="layui-input">
	                </div>
	                （个/�?/�?  例：�?1/月）
	            </div>  -->
	                <br/>
	            
                <div class="layui-form-item">
	                <label class="layui-label" ><span style="color:red">*    </span>被查物理�?�?</label>
	                <div class="layui-input-block" >
	                    <input  type="text" id="officeName" name="officeName"readonly 
	                            placeholder="请输入物理局�?名称获得" value="${checkRecord.officeName!''}" style="width:85%"
	                           class="layui-input " readonly lay-verify="required" onclick="chooseRoom();">
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-label" ><span style="color:red">*    </span>被查部门</label>
	                <div class="layui-input-block" >
	                    <input  type="text" id="area" name="area"readonly
	                            placeholder="根据物理�?�?名称获得"style="width:85%"
	                           class="layui-input "  lay-verify="required">
	                </div>
	            </div>
	            
	            <div class="layui-form-item">
	                <label class="layui-label" ><span style="color:red">*    </span>被查网格</label>
	                <div class="layui-input-block" >
	                    <input  type="text" id="classGroup" name="classGroup" readonly
	                            placeholder="根据物理�?�?名称获得" value="${checkRecord.classGroup!''}" style="width:85%"
	                           class="layui-input "  lay-verify="required">
	                </div>
	            </div>
	                
	            <div class="layui-inline" style="width: 100%">
	                <label class="layui-label">任务描述</label>
	                <div class="layui-input-inline" style="width:703px;padding-left:0px; ">
	                    <textarea name="description" placeholder="任务描述" class="layui-textarea"
	                              style="width:85%;height: 120px">${(task.description)!''}</textarea>
	                </div>
	            </div>
	          
	          <div class="layui-col-xs6">
	              <label class="layui-label"style="margin-top: 6px">是否�?要上传照�?</label>
	              <div class="layui-input-inline">
		                <input type="radio" name="isPhoto" value="�?" title="�?" checked="">
		                <input type="radio" name="isPhoto" value="�?" title="�?">
		            </div>
	          </div>
	
	          <div class="layui-col-xs6">
	              <label class="layui-label" style="margin-top: 6px">是否�?要上传坐�?</label>
	              <div class="layui-input-inline">
		                <input type="radio" name="isCoordinate" value="�?" title="�?" checked="">
		                <input type="radio" name="isCoordinate" value="�?" title="�?">
		            </div>
	          </div>
	         
	          
	          <div class="layui-col-xs6">
	              <label class="layui-label" style="margin-top: 6px">是否�?要上传附�?</label>
	              <div class="layui-input-inline">
		                <input type="radio" name="isFile" value="�?" title="�?" checked="">
		                <input type="radio" name="isFile" value="�?" title="�?">
		            </div>
	          </div>
	          
	          </div>
	          <br>
	          <div class="layui-inline" id="deptDiv">
	              <label class="layui-label" style="">审批�?<span style="color:red">*</span></label>
                  <div class="layui-input-inline">
                      <input type="text" id="selectDept" name="selectDept"
                             value="<#if  task.deptName??>${task.deptName!}<#else>${className!''}/${groupName!''}</#if>" placeholder="选择执行部门"
                             class="layui-input " readonly=true onclick="openDept()" lay-verify="required">
                  </div>
              </div>
		          
        
            </div>
        </div>
    </fieldset>
    <div class="layui-nav-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="formDemo">确定</button>
            <button type="button" class="layui-btn layui-btn-normal" onclick="back();">返回</button>
        </div>
    </div>
</form>
<br>

</div>




<div id="deptSelect" style="display:none;padding:20px;">
    <div class="row">
        <div id="tree" class="ztree"></div>
    </div>
</div>
</body>
</html>

