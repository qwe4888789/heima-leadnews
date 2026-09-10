package com.heima.common.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 抽取的通用的Controller类
 */
public abstract class AbstractController<T>{

    protected IService<T> coreService;

    //构造函数
    public AbstractController(IService<T> coreService) {
        this.coreService = coreService;
    }

    /**
     * 删除记录
     *
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public Result delete(@PathVariable(name = "id") Integer id) {
        boolean flag = coreService.removeById(id);

        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 添加记录
     *
     * @param record
     * @return
     */
    @PostMapping("/save")
    public Result<T> save(@RequestBody T record) {
        boolean flag = coreService.save(record);
        if (!flag) {
            return Result.error();
        }
        return Result.ok(record);
    }

    //更新数据
    @PutMapping("/update")
    public Result update(@RequestBody T record) {
        boolean flag = coreService.updateById(record);
        if (!flag) {
            return Result.error();
        }
        return Result.ok();
    }

    /**
     * 查询全部数据
     */
    @GetMapping("/list")
    public Result findAll(){
        List<T> list = coreService.list();
        return Result.ok(list);
    }

    /**
     * 根据id查询
     */
    @GetMapping("/one/{id}")
    public Result findOne(@PathVariable("id")Integer id){
        T t = coreService.getById(id);
        return Result.ok(t);
    }

    /**
     * 通用条件分页查询
     * @param pageRequestDto
     * @return
     */
    @PostMapping("/list")
    public Result<PageInfo<T>> searchByPage(@RequestBody PageRequestDto<T> pageRequestDto) {
        Page page = new Page(pageRequestDto.getPage(), pageRequestDto.getSize());

        //条件 name 查询
        QueryWrapper<T> queryWrapper = getWrapper(pageRequestDto.getBody());
        IPage iPage = coreService.page(page, queryWrapper);
        PageInfo<T> pageInfo = new PageInfo(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), iPage.getPages(), iPage.getRecords());
        return Result.ok(pageInfo);
    }

    private QueryWrapper<T> getWrapper(T body) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (body == null) {
            return queryWrapper;
        }
        Field[] declaredFields = body.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            try {
                //遇到 id注解 则直接跳过 不允许实现根据主键查询
                if (declaredField.isAnnotationPresent(TableId.class) || declaredField.getName().equals("serialVersionUID")) {
                    //跳过
                    continue;
                }
                String fieldName = declaredField.getName();
                String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                Method readMethod;
                try {
                    readMethod = body.getClass().getMethod(getterName);
                } catch (NoSuchMethodException nme) {
                    getterName = "is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    readMethod = body.getClass().getMethod(getterName);
                }
                Object value = readMethod.invoke(body);

                //如果是字符串
                TableField annotation = declaredField.getAnnotation(TableField.class);
                //如果传递的值为空则不做处理
                if(value != null) {
                    //如是字符串 则用like
                    if (value.getClass().getName().equals("java.lang.String")) {
                        queryWrapper.like(annotation.value(), value);
                    } else {
                        //否则使用=号
                        queryWrapper.eq(annotation.value(), value);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return queryWrapper;
    }

}