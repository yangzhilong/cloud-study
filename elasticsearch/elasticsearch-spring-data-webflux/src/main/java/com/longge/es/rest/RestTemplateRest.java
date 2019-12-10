 package com.longge.es.rest;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.longge.common.util.BeanMapper;
import com.longge.es.domain.User;
import com.longge.es.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 使用es 的rest api来操作ES
 * @author roger yang
 * @date 12/06/2019
 */
@RestController
@RequestMapping("/api/template")
public class RestTemplateRest {
    @Autowired
    private ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;
    
    /**
     *  添加
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public Mono<User> add(@RequestBody @Valid UserDto dto) {
        User user = BeanMapper.map(dto, User.class);
        if(Objects.isNull(user.getId())) {
            user.setId(1L);
        }
        return reactiveElasticsearchTemplate.save(user);
    }
    
    /**
     *   查询单条数据
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Mono<User> get(@PathVariable("id") String id) {
    	return reactiveElasticsearchTemplate.findById(id, User.class);
    }
    
    /**
     * 条件查询
     * @param dto
     * @return
     */
    @GetMapping("/search")
    public Flux<User> query(UserDto dto) {
    	Criteria criteria = Criteria.where("age").greaterThanEqual(dto.getAge());
    	criteria.and("id").in(1, 2);
    	
    	CriteriaQuery query = new CriteriaQuery(criteria);
    	
    	return reactiveElasticsearchTemplate.find(query, User.class);
    }
    
    /**
     *  更新单条记录
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public Mono<User> update(@RequestBody @Valid UserDto dto) {
    	User user = BeanMapper.map(dto, User.class);
    	return reactiveElasticsearchTemplate.save(user);
    }
    
    /**
     * 删除记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Mono<String> delete(@PathVariable("id") String id) {
    	return this.reactiveElasticsearchTemplate.deleteById(id, User.class);
    }
}
