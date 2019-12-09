 package com.longge.es.rest;

import java.util.Objects;

import javax.validation.Valid;

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

import reactor.core.publisher.Mono;

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
    public Mono<GlobalResponse<Long>> add(@RequestBody @Valid UserDto dto) {
        User user = BeanMapper.map(dto, User.class);
        if(Objects.isNull(user.getId())) {
            user.setId(1L);
        }
        user = userRepository.save(user).block();
        return Mono.just(GlobalResponse.buildSuccess(user.getId()));
    }
    
    /**
     *   查询单条数据
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Mono<GlobalResponse<UserDto>> get(@PathVariable("id") Long id) {
    	Mono<User> opt = userRepository.findById(id);
    	User user = opt.block();
    	if(null != user) {
    		return Mono.just(GlobalResponse.buildSuccess(BeanMapper.map(user, UserDto.class)));
    	}
    	return Mono.just(GlobalResponse.buildFail("404", "no data"));
    }

    /**
     *  更新单条记录
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/update")
    public Mono<GlobalResponse<Boolean>> update(@RequestBody @Valid UserDto dto) {
    	Mono<User> opt = userRepository.save(BeanMapper.map(dto, User.class));
    	opt.subscribe();
    	return Mono.just(GlobalResponse.buildSuccess(Boolean.TRUE));
    }
    
    /**
     * 删除记录
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Mono<GlobalResponse<Boolean>> delete(@PathVariable("id") Long id) {
    	userRepository.deleteById(id).block();
    	return Mono.just(GlobalResponse.buildSuccess(Boolean.TRUE));
    }
}
