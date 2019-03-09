package com.little.g.common.params;

import com.little.g.common.dto.ListResultDTO;

import java.io.Serializable;

/**
 * Created by llg on 2019/2/1.
 */
public class TimeQueryParam implements Serializable{

    /**
     * 本次限制条数
     */
    protected Integer limit = 20;
    /**
     * 最后获取可能是页，也可能是时间戳
     */
    protected Long last = System.currentTimeMillis();

    public Integer getLimit() {
        if(limit == null) limit=20;
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getLast() {
        if(last == null || last==0) last=System.currentTimeMillis();
        return last;
    }

    public void setLast(Long last) {
        this.last = last;
    }


    public <T extends ListResultDTO> T  getResult(Class<T> clazz){
        try {
            T list=clazz.newInstance();
            list.setLast(last);
            list.setLimit(limit);
            return list;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
