package com.atguigu.commonutils.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException{

    @ApiModelProperty("状态码")
    private Integer code;

    private String message;

    public GuliException(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "message=" + this.getMessage() +
                ", code=" + this.getCode() +
                "}";
    }
}
