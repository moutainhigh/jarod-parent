package pri.jarod.java.core.util;


import com.google.common.collect.Lists;
import pri.jarod.java.core.exception.DapException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 用法
 * 基于java环境（非spring的@validate)下进行实体类的javax.bean校验检查
 *
 * @author Jarod.Kong
 * @date 2020/8/11 10:22
 */
public enum JValidUtils {
    INSTANCE;

    private JValidUtils(){}

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();
    private List<String> validateMsg = Lists.newArrayList();
    private boolean valid;
    private Class<?>[] groupClz = null;

    public <T> List<String> validate(T t) {
        return validate(t, true);
    }

    public <T> List<String> validateNotNull(T t) {
        return validate(t, false);
    }

    public <T> List<String> validate(T t, boolean allowNull) {
        if (!allowNull && t == null) {
            throw new DapException("实体类为空，NPE");
        }
        List<String> messageList = new ArrayList<>();
        if (t != null) {
            Validator validator = FACTORY.getValidator();
            Set<ConstraintViolation<T>> constraintViolations = groupClz != null ? validator.validate(t, groupClz) : validator.validate(t);

            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                messageList.add(constraintViolation.getMessage());
            }
        }
        return messageList;
    }

//    public static void main(String[] args) {
//        DapStandardBasic dapStandardBasic = new DapStandardBasic();
//        JValidUtils jValidUtils = JValidUtils.INSTANCE.checkValid(dapStandardBasic);
//        boolean valid = jValidUtils.isValid();
//        String validateMsg = jValidUtils.getValidateMsg();
//        System.out.println("校验情况：" + valid + ", 信息:" + validateMsg);
//    }

    public String getValidateMsg() {
        return String.join(";", validateMsg);
    }

    public <T> JValidUtils checkValid(T t) {
        isValid(t, false);
        return this;
    }

    public JValidUtils setGroupClzs(Class<?>[] groupClz) {
        if (groupClz != null) {
            Class<?>[] clz = Arrays.copyOf(groupClz, groupClz.length);
            this.groupClz = clz;
        }
        return this;
    }

    public <T> JValidUtils checkValid(T t, boolean allowNull) {
        isValid(t, allowNull);
        return this;
    }

    public boolean isValid() {
        return valid;
    }

    public <T> boolean isValidNotNull(T t) {
        return isValid(t, true);
    }

    public <T> boolean isValid(T t, boolean allowNull) {
        List<String> validate = validate(t, allowNull);
        validateMsg = validate;
        this.valid = validate.isEmpty();
        return validate.isEmpty();
    }
}
