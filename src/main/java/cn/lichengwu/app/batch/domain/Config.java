package cn.lichengwu.app.batch.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Config
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 2:20 PM
 */
public class Config implements Serializable {

    private static final long serialVersionUID = -2646206960894262539L;
    private int id;

    private String namespace;

    private String name;

    private String value;

    private Date addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private Date updateTime;

    private String note;

}
