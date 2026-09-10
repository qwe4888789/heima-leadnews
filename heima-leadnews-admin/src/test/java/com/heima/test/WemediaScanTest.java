package com.heima.test;

import com.heima.admin.AdminApplication;
import com.heima.admin.service.WemediaNewsScanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminApplication.class)
public class WemediaScanTest {

    @Autowired
    private WemediaNewsScanService wemediaNewsScanService;

    @Test
    public void testWemediaNewsScan(){
        wemediaNewsScanService.autoScanByMediaNewsId(6174);
    }

}
