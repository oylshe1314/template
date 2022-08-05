package com.sk.op.application.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID> {

    T get(ID id);

    Page<T> query(Pageable pageable);

    Page<T> query(Pageable pageable, T params);
}
