package pri.jarod.java.core.exception;


import pri.jarod.java.core.ResultBean;

/**
 * 项目异常，捕获后返回前端
 * {@link GlobalExceptionHandler#methodArgumentNotValid(HttpServletRequest, MethodArgumentNotValidException)} (HttpServletRequest, DapThrowException)}
 * 针对一些异常必须要抛出而不是被{@link cn.com.bluemoon.dap.config.ControllerAOP}捕获后进行封装才输出
 * 比如 文件上传的axios实现
 *
 * @author Jarod.Kong
 */
public class DapThrowException extends DapException {

    private Integer code;

    public DapThrowException(String msg) {
        super(msg);
        this.code = ResultBean.FAIL_CODE;
    }

    /**
     * 自定义错误信息
     *
     * @param message
     * @param code
     */
    public DapThrowException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }
}

