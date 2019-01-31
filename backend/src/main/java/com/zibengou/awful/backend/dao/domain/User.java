package com.zibengou.awful.backend.dao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zibengou.awful.backend.types.RoleEnum;
import com.zibengou.awful.backend.types.SexEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Map;

@NodeEntity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public User() {
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @NotNull
    @Index(unique = true)
    private Long mid;

    @NotNull
    @Index(unique = true)
    @Size(max = 20,min = 4)
    private String name;

    @Properties
    private Map<String, Object> props;

    @Size(max = 100)
    @JsonIgnore
    private String pass;

    @Size(max = 100)
    private String desc;

    /**
     * 初始ID 即设备MAC地址
     */
    @NotNull
    @Index(unique = true)
    private String initId;

    private SexEnum sex;

    @JsonIgnore
    private List<RoleEnum> roles;

    @CreatedDate
    @DateLong
    @JsonIgnore
    private Date createTime;

    @LastModifiedDate
    @DateLong
    @JsonIgnore
    private Date updateTime;

    public void setSex(String sex) {
        try {
            this.sex = SexEnum.valueOf(sex.toUpperCase());
        } catch (Exception ignored) {
            this.sex = SexEnum.UNKNOW;
        }
    }
}
