package com.zibengou.awful.backend.types;

public enum ActionType {
    /**
     * 关注 用户-主题 | 用户-用户
     */
    follow,
    /**
     * 分享 用户-消息
     */
    share,
    /**
     * 跟随 用户-消息
     */
    fork,
    /**
     * 点击 用户-消息 | 用户-回复
     */
    click,
    /**
     * 发布 用户-主题 | 用户-消息 | 用户-回复
     */
    publish,
}
