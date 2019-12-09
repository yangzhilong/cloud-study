 package com.longge.es.rest;

import java.util.Collections;
import java.util.List;
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

import com.longge.common.dto.GlobalResponse;
import com.longge.common.util.BeanMapper;
import com.longge.es.domain.User;
import com.longge.es.dto.UserDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 使用es 的rest api来操作ES
 * @author roger yang
 * @date 12/06/2019
 */
@RestController
@RequestMapping("/api/template")
@Slf4j
public class RestTemplateRest {
    @Autowired
    private ReactiveElasticsearchTemplate reactiveElasticsearchTemplate;
    
    /**
     *  添加
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public Mono<GlobalResponse<Void>> add(@RequestBody @Valid UserDto dto) {
        User user = BeanMapper.map(dto, User.class);
        if(Objects.isNull(user.getId())) {
            user.setId(1L);
        }
        Mono<User> result = reactiveElasticsearchTemplate.save(user);
        result.subscribe();
        return Mono.just(GlobalResponse.buildSuccess());
    }
    
    /**
     *   查询单条数据
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Mono<GlobalResponse<UserDto>> get(@PathVariable("id") String id) {
    	Mono<User> result = reactiveElasticsearchTemplate.findById(id, User.class);
    	User user = result.block();
    	if(null != user) {
    		return Mono.just(GlobalResponse.buildSuccess(BeanMapper.map(user, UserDto.class)));
    	}
    	return Mono.just(GlobalResponse.buildFail("404", "no data"));
    }
    
    /**
     * 条件查询
     * @param dto
     * @return
     */
    @GetMapping("/search")
    public Mono<GlobalResponse<List<UserDto>>> query(UserDto dto) {
    	Criteria criteria = Criteria.where("age").greaterThanEqual(dto.getAge());
    	criteria.and("id").in(1, 2);
    	
    	CriteriaQuery query = new CriteriaQuery(criteria);
    	
    	Flux<User> list = reactiveElasticsearchTemplate.find(query, User.class);
    	if(list.hasElements().block()) {
    		return Mono.just(GlobalResponse.buildSuccess(BeanMapper.mapList(list.toIterable(), UserDto.class)));
    	}
    	return Mono.just(GlobalResponse.buildSuccess(Collections.emptyList()));
    }
    
    /**
     *  更新单条记录
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public GlobalResponse<Boolean> update(@RequestBody @Valid UserDto dto) {
    	User user = BeanMapper.map(dto, User.class);
    	Mono<User> result = reactiveElasticsearchTemplate.save(user);
    	result.subscribe();
    	return GlobalResponse.buildSuccess(Boolean.TRUE);
    }
    
    /**
     * 删除记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public GlobalResponse<Boolean> delete(@PathVariable("id") String id) {
    	Mono<String> result = this.reactiveElasticsearchTemplate.deleteById(id, User.class);
    	log.info("delete user doc id is:{}", result.block());
    	return GlobalResponse.buildSuccess(Boolean.TRUE);
    }
}
