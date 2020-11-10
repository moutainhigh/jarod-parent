package pri.jarod.java.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * common return
 *
 * @param <T>
 * @author xuxueli 2015-12-4 16:32:31
 * @author Jarod Kong Deprecated new 构造器，转为静态获取，增加分页，增加addMoreData,增加泛型
 * @author Jarod Kong 增加ResultBean#of(auth.FeignApiRespBean)
 * 用于feign调用返回的数据，转为ResultBean
 */
public class ResultBean<T> implements Serializable {
    public static final long serialVersionUID = 42L;
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;

    public static final ResultBean<String> SUCCESS = new ResultBean<String>(null);
    public static final ResultBean<String> FAIL = new ResultBean<String>(FAIL_CODE, "fail");

    private int code;
    private String msg = "success";
    private T content;
    private boolean isPageContent;
    /**
     * 增加附加信息属性
     * late init
     */
    private Map<String, Object> moreData;
    /**
     * 耗时 PT0.23S
     * 运行间隔以"P"开始，和上面一样也是用"T"分割日期和时间，如P1Y2M10DT2H30M15S
     */
    private String cost = null;

    private ResultBean(int code, String msg, T content) {
        this.content = content;
        this.code = code;
        this.msg = msg;
    }

    private ResultBean(int code, String msg, T content, boolean isPageContent) {
        this.content = content;
        this.code = code;
        this.msg = msg;
        this.isPageContent = isPageContent;
    }

    @Deprecated
    public ResultBean() {
    }

    private ResultBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultBean(T content) {
        this.code = SUCCESS_CODE;
        this.content = content;
    }

    private ResultBean(T content, boolean isPageContent) {
        this.code = SUCCESS_CODE;
        this.content = content;
        this.isPageContent = isPageContent;
    }

    public static <T> ResultBean<T> error(String msg) {
        return new ResultBean<>(FAIL_CODE, msg);
    }

    public static <T> ResultBean<T> data(int code, T data) {
        return new ResultBean<>(code, null, data);
    }

    public static <T> ResultBean<T> ok(T data) {
        return new ResultBean<>(data);
    }

    public static ResultBean<String> ok() {
        return SUCCESS;
    }

    public static <T> ResultBean<T> page(T data) {
        return new ResultBean<>(data, true);
    }

    public int getCode() {
        return code;
    }

    public ResultBean<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultBean<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getContent() {
        return content;
    }

    public ResultBean<T> setContent(T content) {
        this.content = content;
        return this;
    }

    public boolean isPageContent() {
        return isPageContent;
    }

    public void setPageContent(boolean pageContent) {
        isPageContent = pageContent;
    }

    @Override
    public String toString() {
        return "ResultBean [code=" + code + ", msg=" + msg + ", content=" + content + "]";
    }

    /**
     * 增加附加信息属性
     *
     * @param key   属性
     * @param value 属性值
     * @return 当前对象
     */
    public ResultBean<T> addMoreData(String key, Object value) {
        if (this.moreData == null) {
            this.moreData = new LinkedHashMap<>();
        }
        this.moreData.put(key, value);
        return this;
    }

    public Map<String, Object> getMoreData() {
        return moreData;
    }

    public BigDecimal toSeconds(Duration cost) {
        return BigDecimal.valueOf(cost.getSeconds()).add(BigDecimal.valueOf(cost.getNano(), 9));
    }

    public String getCost() {
        return cost;
    }

    public ResultBean<T> setCost(Duration cost) {
        if (cost != null) {
            this.cost = cost.toString();
        }
        return this;
    }
}
