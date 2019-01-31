package com.zibengou.awful.backend.types;

public enum ResourceType {

    /***
     * 用户
     */
    USER(10),
    /***
     * 话题
     */
    TOPIC(20),
    /***
     * 信息体
     */
    MESSAGE(21),

    /***
     * 回复
     */
    COMMENT(22);

    private int type;

    ResourceType(int type) {
        this.type = type;
    }

    public static ResourceType getBytype(int type) {
        for (ResourceType t : ResourceType.values()) {
            if (t.getType() == type) {
                return t;
            }
        }
        return null;
    }

    public static ResourceType getByMid(Long mid) {
        Long type = mid;
        while (mid > 10) {
            type = mid;
            mid /= 10;
        }
        return getBytype(type.intValue());
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
