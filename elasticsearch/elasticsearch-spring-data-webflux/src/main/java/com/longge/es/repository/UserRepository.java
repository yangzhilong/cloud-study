package com.longge.es.repository;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.longge.es.domain.User;

@Repository
public interface UserRepository extends ReactiveElasticsearchRepository<User, Long> {
}
