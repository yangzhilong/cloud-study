 package com.longge.es.rest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.longge.common.dto.GlobalResponse;
import com.longge.common.util.BeanMapper;
import com.longge.es.domain.User;
import com.longge.es.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

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
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    /**
     *  添加
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public GlobalResponse<String> add(@RequestBody @Valid UserDto dto) {
        User user = BeanMapper.map(dto, User.class);
        if(Objects.isNull(user.getId())) {
            user.setId(1L);
        }
        IndexQuery query = new IndexQueryBuilder().withId(String.valueOf(dto.getId())).withObject(user).build();
        String docId = elasticsearchRestTemplate.index(query);
        return GlobalResponse.buildSuccess(docId);
    }
    
    /**
     *   查询单条数据
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public GlobalResponse<UserDto> get(@PathVariable("id") String id) {
    	GetQuery query = GetQuery.getById(id);
    	User user = elasticsearchRestTemplate.queryForObject(query, User.class);
    	if(null != user) {
    		return GlobalResponse.buildSuccess(BeanMapper.map(user, UserDto.class));
    	}
    	return GlobalResponse.buildFail("404", "no data");
    }
    
    /**
     * 条件查询
     * @param dto
     * @return
     */
    @GetMapping("/search")
    public GlobalResponse<List<UserDto>> query(UserDto dto) {
    	Criteria criteria = Criteria.where("age").greaterThanEqual(dto.getAge());
    	criteria.and("id").in(1, 2);
    	
    	CriteriaQuery query = new CriteriaQuery(criteria);
    	List<User> list = elasticsearchRestTemplate.queryForList(query, User.class);
    	if(CollectionUtils.isNotEmpty(list)) {
    		return GlobalResponse.buildSuccess(BeanMapper.mapList(list, UserDto.class));
    	}
    	return GlobalResponse.buildSuccess(Collections.emptyList());
    }
    
    /**
     * 条件查询
     * @param dto
     * @return
     */
    @GetMapping("/search2")
    public GlobalResponse<List<UserDto>> query2(UserDto dto) {
    	IdsQueryBuilder qb = new IdsQueryBuilder();
    	qb.addIds(String.valueOf(dto.getId()));
    	SearchQuery query = new NativeSearchQuery(qb);
    	List<User> list = elasticsearchRestTemplate.queryForList(query, User.class);
    	if(CollectionUtils.isNotEmpty(list)) {
    		return GlobalResponse.buildSuccess(BeanMapper.mapList(list, UserDto.class));
    	}
    	return GlobalResponse.buildSuccess(Collections.emptyList());
    }
    
    /**
     *  更新单条记录
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/update/{id}")
    public GlobalResponse<Boolean> update(@PathVariable("id") String id, @RequestBody @Valid UserDto dto) {
    	User user = BeanMapper.map(dto, User.class);
    	IndexRequest indexRequest = new IndexRequest();
    	indexRequest.source(new Gson().toJson(user), XContentType.JSON);
    	UpdateQuery query = new UpdateQueryBuilder().withClass(User.class).withId(id).withIndexRequest(indexRequest).build();
    	elasticsearchRestTemplate.update(query);
    	return GlobalResponse.buildSuccess(Boolean.TRUE);
    }
    
    /**
     * 删除记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public GlobalResponse<Boolean> delete(@PathVariable("id") String id) {
    	String docId = this.elasticsearchRestTemplate.delete(User.class, id);
    	log.info("delete user doc id is:{}", docId);
    	return GlobalResponse.buildSuccess(Boolean.TRUE);
    }
}
