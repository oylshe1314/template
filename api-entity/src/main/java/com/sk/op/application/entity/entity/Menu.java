package com.sk.op.application.entity.entity;

import com.sk.op.application.entity.entity.base.EntityOperation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Menu extends EntityOperation {

  private Long parentId;

  private Integer type;

  private String name;

  private String path;

  private Integer sortBy;

  private Integer state;
}
