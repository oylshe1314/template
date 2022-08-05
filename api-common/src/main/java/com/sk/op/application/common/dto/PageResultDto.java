package com.sk.op.application.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Schema(description = "页结果数据")
public class PageResultDto<R> {

    private final int pageNo;

    private final int pageSize;

    @Schema(description = "总数")
    private final long total;

    @Schema(description = "总页数")
    private final long pages;

    @Schema(description = "页结果数据")
    private final List<R> results;

    public PageResultDto(Pageable pageable, Page<R> page) {
        this.pageNo = pageable.getPageNumber() + 1;
        this.pageSize = pageable.getPageSize();
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
        this.results = page.getContent();
    }

    public <S> PageResultDto(Pageable pageable, Page<S> page, Function<S, R> mapper) {
        this.pageNo = pageable.getPageNumber() + 1;
        this.pageSize = pageable.getPageSize();
        this.total = page.getTotalElements();
        this.pages = page.getTotalPages();
        this.results = page.getContent().stream().map(mapper).collect(Collectors.toList());
    }
}
