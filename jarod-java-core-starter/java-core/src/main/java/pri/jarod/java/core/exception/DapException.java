package pri.jarod.java.core.exception;

import pri.jarod.java.core.ResultBean;


/**
 * 项目异常，捕获后返回前端
 *
 * @author Jarod.Kong
 */
public class DapException extends RuntimeException {

    private Integer code;

    public DapException(String msg) {
        super(msg);
        this.code = ResultBean.FAIL_CODE;
    }

    /**
     * 自定义错误信息
     *
     * @param message
     * @param code
     */
    public DapException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

