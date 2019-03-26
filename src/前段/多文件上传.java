package 前段;

public class 多文件上传 {


1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
<div class="layui-form-item">
  <label class="layui-form-label">上传文件</label>
 <div class="layui-input-block">
 <div class="layui-upload">
 <button type="button" class="layui-btn layui-btn-normal" id="testList">选择多文件</button>
 <div class="layui-upload-list">
  <table class="layui-table">
  <thead>
  <tr><th>文件名</th>
  <th>大小</th>
  <th>状态</th>
  <th>操作</th>
  </tr></thead>
  <tbody id="demoList"></tbody>
  </table>
 </div>
 <button type="button" class="layui-btn" id="testListAction">开始上传</button>
 </div>
   </div>
</div>
}




layui.use('upload', function(){
	   var $ = layui.jquery,upload = layui.upload;
	   //多文件列表示例
	   var demoListView = $('#demoList')
	        ,uploadListIns = upload.render({
	        elem: '#testList'
	        ,url: '/upload'
	        ,accept: 'file'
	        ,data:{}  //可放扩展数据 key-value
	        ,multiple: true
	        ,auto: false
	        ,bindAction: '#testListAction'
	        ,choose: function(obj){
	          var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
	          //读取本地文件
	          obj.preview(function(index, file, result){
	            var tr = $(['<tr id="upload-'+ index +'">'
	              ,'<td>'+ file.name +'</td>'
	              ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
	              ,'<td>等待上传</td>'
	              ,'<td>'
	              ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">重传</button>'
	              ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">删除</button>'
	              ,'</td>'
	              ,'</tr>'].join(''));
	  
	            //单个重传
	            tr.find('.demo-reload').on('click', function(){
	              obj.upload(index, file);
	            });
	  
	            //删除
	            tr.find('.demo-delete').on('click', function(){
	              delete files[index]; //删除对应的文件
	              tr.remove();
	              uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
	            });
	  
	            demoListView.append(tr);
	          });
	        }
	        ,done: function(res, index, upload){
	          if(res.code == 0) //上传成功
	            var tr = demoListView.find('tr#upload-'+ index)
	              ,tds = tr.children();
	          tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
	          tds.eq(3).html(''); //清空操作
	          return delete this.files[index]; //删除文件队列已经上传成功的文件
	  
	        } //code为后台传回来的数据，具体多少自己定，
	  
	        //后台只能传回json格式数据，不然会走error函数；
	  
	        ,error: function(index, upload){
	  
	     }
	  })
	});


public String uploadNoticeFile(MultipartFile fileList) {
	   try{
	      String pathname = filepath;
	      String timeMillis = Long.toString(System.currentTimeMillis());//时间戳
	      String filename = timeMillis + fileList.getOriginalFilename();
	      File dir = new File(pathname);
	      if (!dir.exists()) {
	        dir.mkdirs();
	      }
	      String filepath = pathname + filename;
	      File serverFile = new File(filepath);
	      fileList.transferTo(serverFile);
	  
	      //存入数据库
	      NoticeFile noticeFile = new NoticeFile();
	      noticeFile.setNoFileName(filename);
	      noticeFile.setNoFilePath(filepath);
	      noticeFile.setNoId(0L);
	      noticeFileRepository.save(noticeFile);
	      return "1";
	  
	    }catch (Exception e) {
	      e.printStackTrace();
	      return "0";
	    }
	  
	  }


public String uploadNoticeFile(MultipartFile fileList) {
	   try{
	      String pathname = filepath;
	      String timeMillis = Long.toString(System.currentTimeMillis());//时间戳
	      String filename = timeMillis + fileList.getOriginalFilename();
	      File dir = new File(pathname);
	      if (!dir.exists()) {
	        dir.mkdirs();
	      }
	      String filepath = pathname + filename;
	      File serverFile = new File(filepath);
	      fileList.transferTo(serverFile);
	  
	      //存入数据库
	      NoticeFile noticeFile = new NoticeFile();
	      noticeFile.setNoFileName(filename);
	      noticeFile.setNoFilePath(filepath);
	      noticeFile.setNoId(0L);
	      noticeFileRepository.save(noticeFile);
	      return "1";
	  
	    }catch (Exception e) {
	      e.printStackTrace();
	      return "0";
	    }
	  
	  }









