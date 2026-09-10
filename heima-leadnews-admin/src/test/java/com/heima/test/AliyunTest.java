package com.heima.test;

import com.heima.admin.AdminApplication;
import com.heima.common.aliyun.GreenImageScan;
import com.heima.common.aliyun.GreenTextScan;
import com.heima.common.fastdfs.FastDFSClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class AliyunTest {
    @Autowired
    private GreenTextScan greenTextScan;
    @Autowired
    private FastDFSClientUtil clientUtil;
    @Autowired
    private GreenImageScan greenImageScan;

    /**
     * 检测文本
     */
    @Test
    public void testTextScan() throws Exception {
        List<String> content = new ArrayList<>();
        content.add("Word很大");
        content.add("冰毒");

        Map result = greenTextScan.greeTextScan(content);
        System.out.println(result);
    }

    /**
     * 检测文本
     */
    @Test
    public void testImageScan() throws Exception {
       //1.到FastDFS下载图片
        /** group1/M00/00/00/wKiEhWDtVImAeNjmAAB5v-vu4bQ824.jpg
         * 参数一：组名称   group1
         * 参数二：路径     M00/00/00/wKiEhWDtVImAeNjmAAB5v-vu4bQ824.jpg
         */
        byte[] bytes = clientUtil.download("group1", "M00/00/00/wKhChWD1wlWATTjtAADM1HBdA5A10.jpeg");

        //2.封装数据
        List<byte[]> list = new ArrayList<>();
        list.add(bytes);

        //3.调用阿里云进行检测
        Map result = greenImageScan.imageScan(list);
        System.out.println(result);
    }
}
