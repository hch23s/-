package 导入导出;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
 
public class 复制压缩包 {
 
    // 4MB buffer
    private static final byte[] BUFFER = new byte[4096 * 1024];
 
    /**
     * copy input to output stream - available in several StreamUtils or Streams classes 
     */    
    public static void copy(InputStream input, OutputStream output) throws IOException {
        int bytesRead;
        while ((bytesRead = input.read(BUFFER))!= -1) {
            output.write(BUFFER, 0, bytesRead);
        }
    }
 
    public static void main(String[] args) throws Exception {
        // read war.zip and write to append.zip
        ZipFile war = new ZipFile("E://1.zip",Charset.forName("GBK"));
        ZipOutputStream append = new ZipOutputStream(new FileOutputStream("E://3.zip"));
 
        // first, copy contents from existing war
        Enumeration<? extends ZipEntry> entries = war.entries();
        while (entries.hasMoreElements()) {
            ZipEntry e = entries.nextElement();
            System.out.println("copy: " + e.getName());
            append.putNextEntry(e);
            if (!e.isDirectory()) {
                copy(war.getInputStream(e), append);
            }
            append.closeEntry();
        }
 
        // now append some extra content
        ZipEntry e = new ZipEntry("一//answer.txt");
        System.out.println("append: " + e.getName());
        append.putNextEntry(e);
        append.write("42\n".getBytes());
        append.closeEntry();
 
        // close
        war.close();
        append.close();
    }
}
