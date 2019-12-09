package com.longge.es.repository;

import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.longge.es.domain.User;

@Repository
public class UserRepository extends SimpleElasticsearchRepository<User, Long> {

}
