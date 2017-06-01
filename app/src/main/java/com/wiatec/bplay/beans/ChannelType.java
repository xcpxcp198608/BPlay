package com.wiatec.bplay.beans;

/**
 * Created by patrick on 2017/2/13.
 */

public class ChannelType {
    private int id;
    private String name;
    private String icon;
    private int flag;
    private short isLock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public short getIsLock() {
        return isLock;
    }

    public void setIsLock(short isLock) {
        this.isLock = isLock;
    }

    @Override
    public String toString() {
        return "ChannelType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", flag=" + flag +
                ", isLock=" + isLock +
                '}';
    }
}
