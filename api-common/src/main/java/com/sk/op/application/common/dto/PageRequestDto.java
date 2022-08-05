package com.sk.op.application.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Schema(description = "分布请示参数")
public class PageRequestDto<Q> {

    @NotNull
    @Min(value = 1, message = "页码最小1开始")
    @Schema(description = "页码")
    private final Integer pageNo;

    @NotNull
    @Range(min = 10, max = 100, message = "页数量须在10-100之间")
    @Schema(description = "页数量")
    private final Integer pageSize;

    @Schema(description = "查询参数")
    private final Q query;

    @JsonCreator
    public PageRequestDto(@JsonProperty("pageNo") Integer pageNo, @JsonProperty("pageSize") Integer pageSize, @JsonProperty("query") Q query) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.query = query;
    }
}
