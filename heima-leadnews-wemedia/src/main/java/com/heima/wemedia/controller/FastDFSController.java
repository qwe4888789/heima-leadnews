package com.heima.wemedia.controller;

import com.heima.common.fastdfs.FastDFSClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 测试FastDFS
 */
@RestController
@RequestMapping("/file")
public class FastDFSController {
    @Autowired
    private FastDFSClientUtil clientUtil;
    @Value("${fileServerUrl}")
    private String fileServerUrl;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file){
        try {
            String filePath = clientUtil.uploadFile(file);
            return fileServerUrl+filePath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("上传失败");
        }
    }

    /**\
     * 删除文件
     */
    @DeleteMapping("/delFile")
    public String delFile(String fileId){
        try {
            clientUtil.delFile(fileId);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除失败");
        }
    }
}
