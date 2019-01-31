package com.zibengou.awful.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class PageableResponse<T> {
    private Long time;
    private Long nextTime;
    private Integer size;
    private List<T> data;

    public PageableResponse() {
    }

    public PageableResponse(Long time, Integer size) {
        this.time = time;
        this.size = size;
    }
}
