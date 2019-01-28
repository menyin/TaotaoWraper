package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String picUpload(MultipartFile uploadFile) {
        HashMap<Object, Object> result = new HashMap<>();
        try {
            String classPath = this.getClass().getResource("/").getPath();
            String originalFilename = uploadFile.getOriginalFilename();
            String extName=originalFilename.substring(originalFilename.indexOf(".")+1);
            ClientGlobal.init(classPath+"/resource/fdfs-client.conf");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer=null;
            StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);//注意这里是StorageClient1，后面有个1
            String url = storageClient.upload_file1(uploadFile.getBytes(),extName,null);
            result.put("url",IMAGE_SERVER_URL+url);
            result.put("error",0);
            String resultStr = JsonUtils.objectToJson(result);
            return resultStr;
//            return "{\"url\":\""+IMAGE_SERVER_URL+url+"\",\"error\":0}" ;
        } catch (Exception e) {
            System.out.println("后端出错，赶紧检查代码");
//            return "{\"message\":\"图片上传失败\",\"error\":1}" ;
            result.put("message","图片上传失败");
            result.put("error",1);
            String resultStr = JsonUtils.objectToJson(result);
            return resultStr;

        }
    }
}
