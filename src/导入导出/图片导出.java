package ���뵼��;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
 
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
 
public class ͼƬ���� {
 
    /**
     * ʵ��ͼƬ��byte����֮��Ļ���ת��
     * @param args
     */
    public static void main(String[] args) {
        //����·��
        String path = "D:\\ǰ��\\ǰ��ͼƬ����\\jqueryboxImg\\img\\bz1.png";
        byte[] data = image2byte(path);
        System.out.println(data.length);
    }
 
    /**
     * ��ͼƬת��Ϊbyte����
     * @param path ͼƬ·��
     * @return
     */
    public static byte[] image2byte(String path){
        //����byte����
        byte[] data = null;
        //������
        FileImageInputStream input = null;
        try {
          input = new FileImageInputStream(new File(path));
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          byte[] buf = new byte[1024];
          int numBytesRead = 0;
          while ((numBytesRead = input.read(buf)) != -1) {
          output.write(buf, 0, numBytesRead);
          }
          data = output.toByteArray();
          output.close();
          input.close();
        }
        catch (FileNotFoundException ex1) {
          ex1.printStackTrace();
        }
        catch (IOException ex1) {
          ex1.printStackTrace();
        }
        return data;
     }
 
      //byte���鵽ͼƬ
      public void byte2image(byte[] data,String path){
        if(data.length<3||path.equals("")) return;
        try{
        FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
        imageOutput.write(data, 0, data.length);
        imageOutput.close();
        System.out.println("Make Picture success,Please find image in " + path);
        } catch(Exception ex) {
          System.out.println("Exception: " + ex);
          ex.printStackTrace();
        }
      }
      //byte���鵽16�����ַ���
      public String byte2string(byte[] data){
        if(data==null||data.length<=1) return "0x";
        if(data.length>200000) return "0x";
        StringBuffer sb = new StringBuffer();
        int buf[] = new int[data.length];
        //byte����ת����ʮ����
        for(int k=0;k<data.length;k++){
          buf[k] = data[k]<0?(data[k]+256):(data[k]);
        }
        //ʮ����ת����ʮ������
        for(int k=0;k<buf.length;k++){
          if(buf[k]<16) sb.append("0"+Integer.toHexString(buf[k]));
          else sb.append(Integer.toHexString(buf[k]));
        }
        return "0x"+sb.toString().toUpperCase();
      } 
}
