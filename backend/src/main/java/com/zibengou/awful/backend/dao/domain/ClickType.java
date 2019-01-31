package com.zibengou.awful.backend.dao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.neo4j.ogm.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * @author stone
 */
@NodeEntity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClickType implements Serializable {

    private static final long serialVersionUID = 10001L;

    public ClickType() {
    }

    public ClickType(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @NotNull
    @Index(unique = true)
    private String name;

    private String icon;

    @Properties
    private Map<String, Object> props;
}
