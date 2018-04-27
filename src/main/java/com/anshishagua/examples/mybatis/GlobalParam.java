package com.anshishagua.examples.mybatis;

/**
 * User: lixiao
 * Date: 2018/4/26
 * Time: 上午9:51
 */

public class GlobalParam {
    private Long id;
    private String name;
    private String value;
    private int deleted;
    private String typeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    @Override
    public String toString() {
        return "GlobalParam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", deleted=" + deleted +
                ", typeCode='" + typeCode + '\'' +
                '}';
    }
}