package ǰ��;

public class ���ļ��ϴ� {


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
  <label class="layui-form-label">�ϴ��ļ�</label>
 <div class="layui-input-block">
 <div class="layui-upload">
 <button type="button" class="layui-btn layui-btn-normal" id="testList">ѡ����ļ�</button>
 <div class="layui-upload-list">
  <table class="layui-table">
  <thead>
  <tr><th>�ļ���</th>
  <th>��С</th>
  <th>״̬</th>
  <th>����</th>
  </tr></thead>
  <tbody id="demoList"></tbody>
  </table>
 </div>
 <button type="button" class="layui-btn" id="testListAction">��ʼ�ϴ�</button>
 </div>
   </div>
</div>
}




layui.use('upload', function(){
	   var $ = layui.jquery,upload = layui.upload;
	   //���ļ��б�ʾ��
	   var demoListView = $('#demoList')
	        ,uploadListIns = upload.render({
	        elem: '#testList'
	        ,url: '/upload'
	        ,accept: 'file'
	        ,data:{}  //�ɷ���չ���� key-value
	        ,multiple: true
	        ,auto: false
	        ,bindAction: '#testListAction'
	        ,choose: function(obj){
	          var files = this.files = obj.pushFile(); //��ÿ��ѡ����ļ�׷�ӵ��ļ�����
	          //��ȡ�����ļ�
	          obj.preview(function(index, file, result){
	            var tr = $(['<tr id="upload-'+ index +'">'
	              ,'<td>'+ file.name +'</td>'
	              ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
	              ,'<td>�ȴ��ϴ�</td>'
	              ,'<td>'
	              ,'<button class="layui-btn layui-btn-mini demo-reload layui-hide">�ش�</button>'
	              ,'<button class="layui-btn layui-btn-mini layui-btn-danger demo-delete">ɾ��</button>'
	              ,'</td>'
	              ,'</tr>'].join(''));
	  
	            //�����ش�
	            tr.find('.demo-reload').on('click', function(){
	              obj.upload(index, file);
	            });
	  
	            //ɾ��
	            tr.find('.demo-delete').on('click', function(){
	              delete files[index]; //ɾ����Ӧ���ļ�
	              tr.remove();
	              uploadListIns.config.elem.next()[0].value = ''; //��� input file ֵ������ɾ�������ͬ���ļ�����ѡ
	            });
	  
	            demoListView.append(tr);
	          });
	        }
	        ,done: function(res, index, upload){
	          if(res.code == 0) //�ϴ��ɹ�
	            var tr = demoListView.find('tr#upload-'+ index)
	              ,tds = tr.children();
	          tds.eq(2).html('<span style="color: #5FB878;">�ϴ��ɹ�</span>');
	          tds.eq(3).html(''); //��ղ���
	          return delete this.files[index]; //ɾ���ļ������Ѿ��ϴ��ɹ����ļ�
	  
	        } //codeΪ��̨�����������ݣ���������Լ�����
	  
	        //��ֻ̨�ܴ���json��ʽ���ݣ���Ȼ����error������
	  
	        ,error: function(index, upload){
	  
	     }
	  })
	});


public String uploadNoticeFile(MultipartFile fileList) {
	   try{
	      String pathname = filepath;
	      String timeMillis = Long.toString(System.currentTimeMillis());//ʱ���
	      String filename = timeMillis + fileList.getOriginalFilename();
	      File dir = new File(pathname);
	      if (!dir.exists()) {
	        dir.mkdirs();
	      }
	      String filepath = pathname + filename;
	      File serverFile = new File(filepath);
	      fileList.transferTo(serverFile);
	  
	      //�������ݿ�
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
	      String timeMillis = Long.toString(System.currentTimeMillis());//ʱ���
	      String filename = timeMillis + fileList.getOriginalFilename();
	      File dir = new File(pathname);
	      if (!dir.exists()) {
	        dir.mkdirs();
	      }
	      String filepath = pathname + filename;
	      File serverFile = new File(filepath);
	      fileList.transferTo(serverFile);
	  
	      //�������ݿ�
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









