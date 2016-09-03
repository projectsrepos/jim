/*
 * Copyright 2014 Jim. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jim.im.db.base;

/**
 * 分页信息
 *
 * @version 1.0
 */
public class PageInfo {

    /** 记录开始位置 */
    private int from;

    /** 记录结束位置 */
    private int to;

    /** 页码，从1开始 */
    private int index;

    /** 页大小 */
    private int size;

    /** 总页数 */
    private int pages;

    /** 总记录数 */
    private int total;

    /** 翻页自动倒序 */
    private boolean autoDesc;

    /**
     * @param _index 页码：从1开始
     * @param _size 页大小：大于0
     * @param _total 总记录数
     */
    public PageInfo(int _index, int _size, int _total) {
        if (_index < 1)
            throw new IllegalArgumentException("index: " + _index);
        if (_size < 1)
            throw new IllegalArgumentException("size: " + _size);
        if (_total < 0)
            throw new IllegalArgumentException("total: " + _total);
        index = _index;
        size = _size;
        total = _total;
        pages = total / size;
        if (total % size > 0) {
            pages++;
        }
        if (index > pages) {
            index = pages;
        }
        if (pages == 0) {
            index = 1;
        }
        from = (index - 1) * size;
        if (from > total) {
            from = total;
        }
        to = from + size;
        if (to > total) {
            to = total;
        }
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        if (isDesc() && index == pages) {
            return total - (pages - 1) * size;
        }
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isFirst() {
        return (index == 1);
    }

    public boolean isLast() {
        return (index == pages);
    }

    public boolean isDesc() {
        return autoDesc && (index > pages / 2);
    }

    public int getDescFrom() {
        return (pages - index) * size;
    }

    public boolean isAutoDesc() {
        return autoDesc;
    }

    public void setAutoDesc(boolean autoDesc) {
        this.autoDesc = autoDesc;
    }

}
