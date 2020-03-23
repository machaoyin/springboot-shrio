package com.mcy.springbootshiro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表
 *
 * @author
 */
@Entity
public class TbMenu {
    private Integer id;
    private String name;
    private String url;
    private Integer idx;
    @JsonIgnore
    private TbMenu parent;
    @JsonIgnore
    private List<TbMenu> children = new ArrayList<>();

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    @ManyToOne
    @CreatedBy
    public TbMenu getParent() {
        return parent;
    }

    public void setParent(TbMenu parent) {
        this.parent = parent;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    @OrderBy(value = "idx")
    public List<TbMenu> getChildren() {
        return children;
    }

    public void setChildren(List<TbMenu> children) {
        this.children = children;
    }

    @Transient
    public Integer getParentId() {
        return parent == null ? null : parent.getId();
    }
}
