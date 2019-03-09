package com.little.g.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lengligang on 2018/7/4.
 */
public class ListResultDTO<T> implements Serializable {
    /**
     * 本次限制条数
     */
    private Integer limit = 20;
    /**
     * 最后获取可能是页，也可能是时间戳
     */
    private Long last;
    /**
     * 列表
     */
    private List<T> list;
    /**
     * 是否结束
     */
    private boolean end = true;



    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getLast() {
        return last;
    }

    public void setLast(Long last) {
        this.last = last;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
        if(list==null || list.size() < limit){
            this.end =true;
        }else {
            this.end = false;
        }
    }

    public void setList2(List<T> list) {
        this.list = list;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ListResultDTO{");
        sb.append("limit=").append(limit);
        sb.append(", last=").append(last);
        sb.append(", list=").append(list);
        sb.append(", end=").append(end);
        sb.append('}');
        return sb.toString();
    }
}
