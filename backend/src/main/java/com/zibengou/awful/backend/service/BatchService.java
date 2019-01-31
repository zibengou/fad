package com.zibengou.awful.backend.service;

import com.zibengou.awful.backend.model.DynamicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BatchService {

    @Autowired
    private CommonService common;

    /**
     * 获取用户动态信息
     *
     * @param time 截止时间戳
     * @param size 最大信息条数
     * @return
     */
    public List<DynamicInfo> getLatestDynamicInfos(Long time, int size) {
        return new ArrayList<>();
    }

    /**
     * 获取推荐信息
     *
     * @param time     截止时间戳
     * @param interval 刷新时间间隔
     * @param size     最大信息条数
     * @return
     */
    public List<DynamicInfo> getLatestRecommendInfos(Long time, Long interval, int size) {
        return new ArrayList<>();
    }

}
