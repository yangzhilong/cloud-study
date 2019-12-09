 package com.longge.es.rest;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.longge.es.repository.UserRepository;

/**
 * 使用springboot data for es 的Repository来操作ES
 * @author roger yang
 * @date 12/06/2019
 */
@RestController
@RequestMapping("/api/repository")
public class RepositoryRest {
    @Autowired
    private UserRepository userRepository;
    
    /**
     *  添加
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public GlobalResponse<Long> add(@RequestBody @Valid UserDto dto) {
        User user = BeanMapper.map(dto, User.class);
        if(Objects.isNull(user.getId())) {
            user.setId(1L);
        }
        user = userRepository.save(user);
        return GlobalResponse.buildSuccess(user.getId());
    }
    
    /**
     *   查询单条数据
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public GlobalResponse<UserDto> get(@PathVariable("id") Long id) {
    	Optional<User> opt = userRepository.findById(id);
    	if(opt.isPresent()) {
    		return GlobalResponse.buildSuccess(BeanMapper.map(opt.get(), UserDto.class));
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
    	QueryBuilder query = null;
    	userRepository.search(query);
    	return GlobalResponse.buildSuccess(Collections.emptyList());
    }

    /**
     *  更新单条记录
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public GlobalResponse<Boolean> update(@RequestBody @Valid UserDto dto) {
    	userRepository.save(BeanMapper.map(dto, User.class));
    	return GlobalResponse.buildSuccess(Boolean.TRUE);
    }
    
    /**
     * 删除记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public GlobalResponse<Boolean> delete(@PathVariable("id") Long id) {
    	userRepository.deleteById(id);
    	return GlobalResponse.buildSuccess(Boolean.TRUE);
    }
}
