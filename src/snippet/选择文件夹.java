package snippet;
import java.awt.Component;
import javax.swing.JFileChooser;
 
 
public class ѡ���ļ��� {
public static void main(String[] args) {
JFileChooser chooser = new JFileChooser();
/*
* ����JFileChooser�Ե������ļ��п�ѡ�� 1��ֻѡ��Ŀ¼JFileChooser.DIRECTORIES_ONLY
* 2��ֻѡ���ļ�JFileChooser.FILES_ONLY
* 3��Ŀ¼�����ļ�������JFileChooser.FILES_AND_DIRECTORIES
*/
chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 
 
// ������ѡĿ¼chooser.showSaveDialog(parent);
Component parent = null;
int returnVal = chooser.showSaveDialog(parent);
 
 
// ���ѡ�е��ļ�����JFileChooser.APPROVE_OPTION
// ��������Ŀ¼�����ѡ�е��ļ�����һ�£��ɹ����Ƿ���0
if (returnVal == JFileChooser.APPROVE_OPTION) {
 
 
// ���·��
String selectPath = chooser.getSelectedFile().getPath();
System.out.println("��ѡ���Ŀ¼�ǣ�" + selectPath);
}
System.exit(0);
}
}
