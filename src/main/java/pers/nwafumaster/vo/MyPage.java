package pers.nwafumaster.vo;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author Windlinxy
 * @description
 * @date 2023-01-29 18:05
 **/
public class MyPage<T> extends Page<T> {
    @JsonIgnore
    protected List<OrderItem> orders;
    @JsonIgnore
    protected boolean optimizeCountSql;
    @JsonIgnore
    protected boolean searchCount;
    @JsonIgnore
    protected boolean optimizeJoinOfCountSql;
    @JsonIgnore
    protected String countId;
    @JsonIgnore
    protected Long maxLimit;


    public MyPage(long current, long size) {
        super(current, size);
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "records=" + records +
                ", total=" + total +
                ", size=" + size +
                ", current=" + current +
                '}';
    }
}
