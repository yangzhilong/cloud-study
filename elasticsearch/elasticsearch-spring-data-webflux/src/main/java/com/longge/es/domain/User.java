 package com.longge.es.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Getter;
import lombok.Setter;

/**
 *  这里为什么要把type写成_doc呢，因为es的7版本已经将type废弃了，type统一为_doc，如果你要不写，springboot会默认取类的名字，也就是user作为type
 * @author roger yang
 * @date 12/06/2019
 */
@Getter
@Setter
@Document(indexName = "user", type="_doc", createIndex=true)
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6586038811906245946L;
    @Id
    private Long id;
    @Field
    private String username;
    @Field
    private Integer age;
}
