package com.sk.op.application.entity.entity;

import com.sk.op.application.entity.entity.base.EntityOperation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Role extends EntityOperation {

  private String name;

  private Integer state;
}
