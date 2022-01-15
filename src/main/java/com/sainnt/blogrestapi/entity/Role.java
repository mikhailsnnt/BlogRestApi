package com.sainnt.blogrestapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")} )
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
}
