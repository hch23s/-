package ���뵼��;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ͼƬ�������ļ��� {

	 public static void main(String[] a) throws IOException{
         
         FileInputStream in=new FileInputStream("E://1.jpg");  
         //�����ļ���
         boolean mkdir = new File("E://1").mkdir();
         
         FileOutputStream out= new FileOutputStream("E:/1/2.jpg");  
         BufferedInputStream bufferedIn=new BufferedInputStream(in);  
         BufferedOutputStream bufferedOut=new BufferedOutputStream(out);  
         byte[] by=new byte[1024];
         while (bufferedIn.read(by)!=-1) {  
             bufferedOut.write(by);  
         }  
         //���������е�����ȫ��д��  
         bufferedOut.flush();  
         bufferedIn.close();  
         bufferedOut.close();  
         //ɾ��Դ�ļ�
//         File  file = new File("E://1.jpg");  
         // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��  
//         if (file.isFile() && file.exists()) {  
//             file.delete();  
//         } 
}
}