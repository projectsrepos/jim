/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 *
 *
 */

package com.jim.im.model;

/**
 * @author <a href="mailto:linjianfei@wxchina.com">linjianfei</a>
 * @version 1.0
 * @since 2016-07-05
 */
public class TimeConsuming {
    private long id;
    private String mdtContent;
    private long timePoint;

    public long getId() {
        return id;
    }

    public String getMdtContent() {

        return mdtContent;
    }

    public void setMdtContent(String mdtContent) {
        this.mdtContent = mdtContent;
    }

    public long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(long timePoint) {
        this.timePoint = timePoint;
    }

    public TimeConsuming(String md5Content, long timePoint) {
        this.mdtContent = md5Content;
        this.timePoint = timePoint;
    }

    public TimeConsuming(){}
}
