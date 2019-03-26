package 导入导出;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class 图片导出到文件夹 {

	 public static void main(String[] a) throws IOException{
         
         FileInputStream in=new FileInputStream("E://1.jpg");  
         //创建文件夹
         boolean mkdir = new File("E://1").mkdir();
         
         FileOutputStream out= new FileOutputStream("E:/1/2.jpg");  
         BufferedInputStream bufferedIn=new BufferedInputStream(in);  
         BufferedOutputStream bufferedOut=new BufferedOutputStream(out);  
         byte[] by=new byte[1024];
         while (bufferedIn.read(by)!=-1) {  
             bufferedOut.write(by);  
         }  
         //将缓冲区中的数据全部写出  
         bufferedOut.flush();  
         bufferedIn.close();  
         bufferedOut.close();  
         //删除源文件
//         File  file = new File("E://1.jpg");  
         // 路径为文件且不为空则进行删除  
//         if (file.isFile() && file.exists()) {  
//             file.delete();  
//         } 
}
}