package ���뵼��;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ��ȡ�ļ����������ļ��� {
        /**�ڵ�����**/
        private String text; 
        /**�ڵ�·��**/
        private String fileName; 
         /**�ӽڵ�**/
        private List<��ȡ�ļ����������ļ���> children = new ArrayList<��ȡ�ļ����������ļ���>();
    
    public static ��ȡ�ļ����������ļ��� readfile(��ȡ�ļ����������ļ��� tn){
        try {

            File file = new File(tn.fileName);
            System.out.println( file.getName());
            tn.text = file.getName();
            if (!file.isDirectory()) {
                //System.out.println(file.getName());
                
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(tn.fileName + "\\" + filelist[i]);
                    ��ȡ�ļ����������ļ��� tn1 = new ��ȡ�ļ����������ļ���();
                    tn1.text = readfile.getName();
                    tn1.fileName = tn.fileName + "\\" + filelist[i];
                    if (!readfile.isDirectory()) {
                        tn.children.add(tn1);
                    } else if (readfile.isDirectory()) {
                        tn.children.add(readfile(tn1));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return tn;
    }
    
    public static void main(String[] args){
        ��ȡ�ļ����������ļ��� tn = new ��ȡ�ļ����������ļ���();
        tn.fileName = "C:\\Users\\Administrator\\Downloads";
        readfile(tn);
        System.out.println(tn);
        System.out.println(tn.text);
    }
}