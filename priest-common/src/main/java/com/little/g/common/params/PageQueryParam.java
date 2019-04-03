package com.little.g.common.params;

import java.io.Serializable;

/**
 * Created by llg on 2019/2/1.
 */
public class PageQueryParam implements Serializable{

    /**
     * 本次限制条数
     */
    protected Integer limit = 10;
    /**
     * 最后获取可能是页，也可能是时间戳
     */
    protected Integer page = 1;

    public Integer getLimit() {
        if(limit == null) limit=20;
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        if(page == null || page<=0 ) page=1;
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
