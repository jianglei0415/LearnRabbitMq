package com.jl.learn.rabbitmq.enumaration;

/**
 * @author jianglei
 * @date 2020/2/18 0:43
 */
public enum DelayTypeEnum {
    DELAY_10s(1), DELAY_60s(2);

    private Integer delayType;

    DelayTypeEnum(Integer delayType) {
        this.delayType = delayType;
    }

    public Integer getDelayType() {
        return delayType;
    }

    public void setDelayType(Integer delayType) {
        this.delayType = delayType;
    }

    public static DelayTypeEnum getDelayTypeEnumByValue(int delayType) {
        for (DelayTypeEnum delayTypeEnum : DelayTypeEnum.values()) {
            if (delayTypeEnum.delayType == delayType) {
                return delayTypeEnum;
            }
        }
        return null;
    }
}
