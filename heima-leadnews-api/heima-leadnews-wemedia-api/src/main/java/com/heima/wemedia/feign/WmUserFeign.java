package com.heima.wemedia.feign;

import com.heima.common.dtos.Result;
import com.heima.model.wemedia.pojos.WmUser;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 自媒体用户Feign接口
 *   name：服务ID
 *   path: 路径，抽取方法的公共路径
 *   注意：一旦在一个项目出现多个Feign接口同名（name），就会冲突
 *      解决办法：给每个Feign接口添加  contextId 属性，定义该接口的别名，防止接口冲突
 */
@FeignClient(name="leadnews-wemedia",path ="/api/wmUser" ,contextId = "wmUserFeign")
public interface WmUserFeign {

    /**
     * 根据apUserId查询自媒体账户是否存在
     */
    @GetMapping("/findUser/{apUserId}")
    public Result<WmUser> findUser(@PathVariable("apUserId") Integer apUserId);

    /**
     * 添加自媒体账户
     * @param record
     * @return
     */
    @PostMapping("/save")
    public Result<WmUser> save(@RequestBody WmUser record);
}
