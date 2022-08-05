package com.sk.op.application.entity.repository;

import com.sk.op.application.entity.entity.Admin;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @EntityGraph("adminWithRole")
    Optional<Admin> findById(Long ids);

    @EntityGraph("adminWithRole")
    Optional<Admin> findByUsername(String username);

    @EntityGraph("adminWithRole")
    Page<Admin> findAll(Pageable pageable);

    @EntityGraph("adminWithRole")
    <S extends Admin> Page<S> findAll(Example<S> example, Pageable pageable);
}
