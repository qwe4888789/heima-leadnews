package com.heima.test;

import com.heima.comment.CommentApplication;
import com.heima.model.comment.pojos.ApComment;
import org.junit.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommentApplication.class)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增
     */
    @Test
    public void testSave(){
        for(int i=1;i<=10;i++){
            ApComment apComment = new ApComment();
            apComment.setContent("测试内容"+i);
            apComment.setLikes(10+i);
            mongoTemplate.insert(apComment);
        }
    }

    /**
     * 查询
     */
    @Test
    public void testQuery(){
        //条件查询
        //1）创建Query查询对象
        //Query query = Query.query(Criteria.where("likes").lt(15));

        //Query query = Query.query(Criteria.where("likes").lt(20).and("content").is("测试内容7"));

        Query query = Query.query(Criteria.where("likes").lt(20));
        /**
         * 排序
         */
        query.with(Sort.by(Sort.Direction.DESC,"likes"));
        //分页
        /**
         * 参数一：页码，从0开始
         * 参数二：页码大小
         */
        //query.with(PageRequest.of(1,3));
        /**
         * 参数一：页面大小
         */
        query.limit(3);


        //2）执行查询
        List<ApComment> apComments = mongoTemplate.find(query, ApComment.class);
        apComments.forEach(System.out::println);
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate(){
        //1)创建Query对象
        Query query = Query.query(Criteria.where("_id").is("610900456a33a7554bbfb087"));

        //2）创建Update对象
        Update update = new Update();
        //update.set("content","Word很大");
        update.inc("likes",-1); //加1或减1

        //3)执行更新
        mongoTemplate.updateMulti(query,update,ApComment.class);
    }

    /**
     * 删除
     */
    @Test
    public void testDelete(){
        Query query = Query.query(Criteria.where("_id").is("610900456a33a7554bbfb087"));
        mongoTemplate.remove(query,ApComment.class);
    }
}
