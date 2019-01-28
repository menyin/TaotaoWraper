package fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

public class FastDFS {
    @Test
    public void test() throws IOException, MyException {
        ClientGlobal.init("E:/GitProjects/TaotaoWraper/taotao-web/src/main/resources/resource/fdfs-client.conf");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);//注意这里是StorageClient1，后面有个1
        String url = storageClient.upload_file1("C:/Users/win7/Desktop/aaa.png","png",null);
        System.out.println(url);
//        System.out.println(this.getClass().getResource("/").getPath());

    }


}
