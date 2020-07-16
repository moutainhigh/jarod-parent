package pri.jarod.spring.boot.core.page;

import pri.jarod.java.constant.Constants;
import pri.jarod.java.core.page.PageEntity;
import pri.jarod.spring.boot.core.ServletUtils;

/**
 * 表格数据处理
 *
 * @author ruoyi
 */
public class TableSupport {
    /**
     * 封装分页对象
     */
    public static PageEntity getPageDomain() {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageEntity.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pageEntity.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageEntity.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return pageEntity;
    }

    public static PageEntity buildPageRequest() {
        return getPageDomain();
    }
}
