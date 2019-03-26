package 导入导出;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class 图片下载到压缩包 {
    public static String downloadUrl="http://www.baidu.com/img/baidu_sylogo1.gif";
    public static String downloadUrl2="http://www.baidu.com/img/baidu_sylogo1.gif";
    public static String downloadUrl3="http://www.baidu.com/img/baidu_sylogo1.gif";
    public static String downloadUrl4="E://1.jpg";
    
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        downloadZip();  
    }
    
    public static void downloadZip() throws Exception{
        long start=System.currentTimeMillis();
        String [] urls={downloadUrl,downloadUrl2,downloadUrl3};
        ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream("D:/1.zip"));
        
        for(String u:urls){
            String ext=u.substring(u.lastIndexOf("."), u.length());
            String fileName=""+System.currentTimeMillis()+ext;
            URL url = new URL(u);
            URLConnection c = url.openConnection();
            c.connect();
            int fileLength=c.getContentLength();
            System.out.println("file size:"+fileLength);
            
            InputStream is = c.getInputStream();
            
            outStream.putNextEntry(new ZipEntry(fileName));
            
            byte[] buffer=new byte[2048];
            int len;
            while((len=is.read(buffer))!=-1){
                outStream.write(buffer,0,len);
            }
            
            //关闭流，省略
            is.close();
        }
        //关闭流，省略
        outStream.close();
        long end=System.currentTimeMillis();
        System.out.println("zip ok ,use time:"+(end-start)+"hao miao ");
        
    }

    
    
}