package pri.jarod.java.core.util;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.util.*;

/**
 * tree工具
 *
 * @author Jarod.Kong
 * @date 2020/7/30 10:02
 */
@UtilityClass
public class TreeUtils {

    public static <E> List<E> createTrees(List<E> datas, String idProperty,
                                          String parentIdProperty,
                                          String childrenProperty,
                                          String levelProperty,
                                          Integer levelStartIndex) {
        if (datas == null || datas.isEmpty()){
            return Collections.emptyList();
        }
        Objects.requireNonNull(parentIdProperty, "E泛型中的父类id属性名不可为空");
        Objects.requireNonNull(parentIdProperty, "E泛型中的子类集合属性名不可为空");
        Objects.requireNonNull(parentIdProperty, "E泛型中的实体类id属性名不可为空");

        Map<Object, E> dataMap = Maps.newLinkedHashMap();
        List<E> retList = Lists.newArrayList();
        Object id = null;
        for (E data : datas) {
            try {
                id = PropertyUtils.getProperty(data, idProperty);
            } catch (Exception e) {
                throw new IllegalArgumentException("利用反射获取实体属性setter/getter失败", e);
            }
            try {
                //noinspection unchecked
                E cloneBean = (E) BeanUtils.cloneBean(data);
                dataMap.put(id, cloneBean);
            } catch (Exception e) {
                throw new IllegalArgumentException("利用反射获取实体属性setter/getter失败", e);
            }
        }
        try {
            int level = levelStartIndex == null ? 0 : levelStartIndex;
            boolean setLevel = StrUtil.isNotBlank(levelProperty);
            for (E data : dataMap.values()) {
                Object parentId = PropertyUtils.getProperty(data, parentIdProperty);
                if ((parentId == null) || (!dataMap.containsKey(parentId))) {
                    if (setLevel) {
                        PropertyUtils.setProperty(data, levelProperty, level);
                    }
                    retList.add(data);
                } else {
                    E parent = dataMap.get(parentId);
                    Object children = PropertyUtils.getProperty(parent, childrenProperty);
                    if (children == null) {
                        children = new ArrayList<E>();
                        PropertyUtils.setProperty(parent, childrenProperty, children);
                    }
                    if (!(children instanceof List)) {
                        throw new ClassCastException("子节点属性[" + childrenProperty + "]无法转换为Collection集合");
                    }
                    if (setLevel) {
                        Object parentlevelObj = PropertyUtils.getProperty(parent, levelProperty);
                        if (parentlevelObj instanceof Number) {
                            Number number = (Number) parentlevelObj;
                            level = number.intValue();
                        }
                        PropertyUtils.setProperty(data, levelProperty, ++level);
                    }
                    List<E> list = (List<E>) children;
                    list.add(data);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("利用反射获取实体属性setter/getter失败", e);
        }
        return retList;
    }
}
