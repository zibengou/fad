package com.zibengou.awful.backend.dao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@NodeEntity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    public Comment() {
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @NotNull
    @Index(unique = true)
    private Long mid;

    private String name;

    @Properties
    private Map<String, Object> props;

    @NotNull
    private String content;

    private String img;

    private List<String> links;

    /***
     * 可点击图标类型
     */
    private List<String> ctypes;


    @CreatedDate
    @DateLong
    @JsonIgnore
    private Date createTime;

    @LastModifiedDate
    @DateLong
    @JsonIgnore
    private Date updateTime;
}
