package com.heima.common.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:fastdfs.properties") // 加载properties文件
@Import(FdfsClientConfig.class) // FdfsClientConfig配置底层读取fastdfs.properties中的参数连接FastDFS
public class FdfsConfiguration {
}
