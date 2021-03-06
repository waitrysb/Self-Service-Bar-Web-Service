package com.SelfServiceBarWeb.model.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Muki on 2018/11/6
 */
public class ChangeLightRequest {
    @ApiModelProperty(value = "更改模式：开、关、增加亮度、降低亮度")
    private ChangeLightStateModeEnum mode;

    @ApiModelProperty(value = "token类型：管理员或用户")
    private TokenTypeEnum tokenTypeEnum;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "对亮度的修改值，可能是offset或者是指定的亮度值，亮度的范围为0~100")
    private Integer luminanceValue;

    @ApiModelProperty(value = "对色温的修改值，色温的范围为2700~6500")
    private Integer color_temperature;

    public Integer getLuminanceValue() {
        return luminanceValue;
    }

    public void setLuminanceValue(Integer luminanceValue) {
        this.luminanceValue = luminanceValue;
    }

    public TokenTypeEnum getTokenTypeEnum() {
        return tokenTypeEnum;
    }

    public void setTokenTypeEnum(TokenTypeEnum tokenTypeEnum) {
        this.tokenTypeEnum = tokenTypeEnum;
    }

    public ChangeLightStateModeEnum getMode() {
        return mode;
    }

    public void setMode(ChangeLightStateModeEnum mode) {
        this.mode = mode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getColor_temperature() {
        return color_temperature;
    }

    public void setColor_temperature(Integer color_temperature) {
        this.color_temperature = color_temperature;
    }
}
