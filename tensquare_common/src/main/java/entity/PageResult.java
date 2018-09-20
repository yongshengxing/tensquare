package entity;

import java.util.List;

/***
 * @author dengliming
 * @email 295004647@qq.com
 * @version 1.0
 * @create 2018-09-19 16:48
 **/
public class PageResult<T> {

    private Integer total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Integer total, List<T> rows) {

        this.total = total;
        this.rows = rows;
    }

    public Integer getTotal() {

        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
