import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsTest {
    static FileSystem fs =null;
    static {
        //获取配置
        Configuration conf = new Configuration();
        //获取hdfs文件系统的操作系统对象
        try {
            fs=FileSystem.get(new URI("hdfs://192.168.80.111:9000"),conf,"root");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        //readFileToConsole("/test/test02.txt");
        //readFileToLocal("/test/t1.txt");
        copyFromLocal();
    }
    //读取hdfs中的文件
    public static void readFileToConsole(String path) throws IOException {
        //具体对文件的操作
        FSDataInputStream fis = fs.open(new Path(path));
        IOUtils.copyBytes(fis, System.out, 4096, true);
    }

    //读取hdfs中的文件到电脑上
    public static void readFileToLocal(String path) throws IOException {
        //具体对文件的操作
        FSDataInputStream fis = null;
        FileOutputStream out = null;
        try {
            fis = fs.open(new Path(path));
            out = new FileOutputStream(new File("D:\\Test\\test01.txt"));
            IOUtils.copyBytes(fis,out,4096,true);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fis.close();
            out.close();
        }

    }
    //将电脑上的文件上传到hdfs
    public static void copyFromLocal() throws IOException {
        fs.copyFromLocalFile(new Path("D:\\Test\\word01.txt"),new Path("/wordcount/input"));
        System.out.println("完成上传");
    }
}
