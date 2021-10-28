/**
 * Copyright 2021 bejson.com
 */
package com.skyler.dto.futu;

/**
 * Auto-generated: 2021-10-24 0:2:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class StaticBasic {

    private Security security;

    private String id;
    private int lotSize;
    private int secType;
    private String name;
    private String listTime;
    private boolean delisting;
    private long listTimestamp;

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLotSize() {
        return lotSize;
    }

    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    public int getSecType() {
        return secType;
    }

    public void setSecType(int secType) {
        this.secType = secType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListTime() {
        return listTime;
    }

    public void setListTime(String listTime) {
        this.listTime = listTime;
    }

    public boolean isDelisting() {
        return delisting;
    }

    public void setDelisting(boolean delisting) {
        this.delisting = delisting;
    }

    public long getListTimestamp() {
        return listTimestamp;
    }

    public void setListTimestamp(long listTimestamp) {
        this.listTimestamp = listTimestamp;
    }
}