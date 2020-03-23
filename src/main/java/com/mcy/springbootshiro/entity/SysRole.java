package com.mcy.springbootshiro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/***
 * 角色及角色对应的菜单权限
 * @author
 *parent 为null时为角色，不为null时为权限
 */
public class SysRole {
    private Integer id;
    private String name;    //名称
    @JsonIgnore
    private SysRole parent;
    private Integer idx;    //排序
    @JsonIgnore
    private List<SysRole> children = new ArrayList<>();

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @CreatedBy
    public SysRole getParent() {
        return parent;
    }

    public void setParent(SysRole parent) {
        this.parent = parent;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    public List<SysRole> getChildren() {
        return children;
    }

    public void setChildren(List<SysRole> children) {
        this.children = children;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    //获取父节点id
    @Transient
    public Integer getParentId() {
        return parent == null ? null : parent.getId();
    }
}
