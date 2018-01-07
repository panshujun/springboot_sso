package com.sunfund.userSys.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户实体类
 *
 * Created by bysocket on 07/02/2017.
 */
@Entity
@Table(name = "sf_role")
public class Role {

    /**
     *  主键
     */
    @Id
    @GeneratedValue
    @Getter @Setter private int id;

    /**
     * 角色名
     */
    @Column(name = "name")
    @Getter @Setter private String name;

}
