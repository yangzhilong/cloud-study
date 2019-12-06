 package com.longge.es.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

/**
 * @author roger yang
 * @date 12/06/2019
 */
@Getter
@Setter
public class UserDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8760527788921956437L;
    
    private Long id;
    @NotNull
    private String username;
    @NotNull
    @Range(min = 1, max = 150)
    private Integer age;
}
