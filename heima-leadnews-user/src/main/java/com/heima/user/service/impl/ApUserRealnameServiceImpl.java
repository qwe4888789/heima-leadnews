package com.heima.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.feign.ApAuthorFeign;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.user.dtos.ApAuthorTypeEnum;
import com.heima.model.user.dtos.AppUserStatusEnum;
import com.heima.model.user.dtos.AuthDTO;
import com.heima.model.user.dtos.WmUserStatusEnum;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.mapper.ApUserRealnameMapper;
import com.heima.user.service.ApUserRealnameService;
import com.heima.utils.common.BeanHelper;
import com.heima.wemedia.feign.WmUserFeign;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * APP实名认证信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class ApUserRealnameServiceImpl extends ServiceImpl<ApUserRealnameMapper, ApUserRealname> implements ApUserRealnameService {
    @Autowired
    private WmUserFeign wmUserFeign;
    @Autowired
    private ApUserMapper apUserMapper;
    @Autowired
    private ApAuthorFeign apAuthorFeign;

    @Override
    public Result authFail(AuthDTO dto) {
        try {
            //1.修改status和reason
            ApUserRealname apUserRealname = new ApUserRealname();
            apUserRealname.setId(dto.getId());
            apUserRealname.setStatus(AppUserStatusEnum.AUTH_FAIL.getValue());
            apUserRealname.setReason(dto.getMsg());
            updateById(apUserRealname);

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    @GlobalTransactional // 添加seata分布式事务
    public Result authPass(AuthDTO dto) {

        try {
            //1.修改实名认证表的status
            ApUserRealname apUserRealname = new ApUserRealname();
            apUserRealname.setId(dto.getId());
            apUserRealname.setStatus(AppUserStatusEnum.AUTH_SUCCESS.getValue());
            apUserRealname.setReason("");
            updateById(apUserRealname);

            //2.在自媒体用户表添加账户
            //1）查询App用户信息
            apUserRealname = getById(dto.getId());
            //2）查询是否存在该自媒体账户
            WmUser wmUser = wmUserFeign.findUser(apUserRealname.getUserId()).getData();
            //3）如果不存在，则新增数据
            //4）查询当前App用户对象
            ApUser apUser = apUserMapper.selectById(apUserRealname.getUserId());

            if(wmUser==null){
                //5）把App用户对象数据拷贝到WmUser对象
                wmUser = BeanHelper.copyProperties(apUser, WmUser.class);
                wmUser.setStatus(WmUserStatusEnum.OK.getValue());
                wmUser.setType(0);
                wmUser.setCreatedTime(new Date());
                wmUser.setApUserId(apUserRealname.getUserId());

                //6）保存数据
                wmUser = wmUserFeign.save(wmUser).getData();
            }

            //3.在文章作者表添加作者
            ApAuthor apAuthor = apAuthorFeign.findApAuthor(apUserRealname.getUserId()).getData();
            if(apAuthor==null){
                apAuthor = new ApAuthor();
                apAuthor.setName(apUser.getName());
                apAuthor.setType(ApAuthorTypeEnum.MEDIA_USER.getValue());
                apAuthor.setUserId(apUser.getId());
                apAuthor.setCreatedTime(new Date());
                //设置自媒体用户ID
                apAuthor.setWmUserId(wmUser.getId());

                //保存数据
                apAuthorFeign.save(apAuthor);
            }

            //模拟错误
            //int i = 100/0;

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }
}
