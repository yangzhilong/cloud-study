 package com.longge.es.domain;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * @author roger yang
 * @date 12/06/2019
 */
@Getter
@Setter
@Document(indexName = "user")
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6586038811906245946L;
    
    private Long id;
    private String username;
    private Integer age;
}
