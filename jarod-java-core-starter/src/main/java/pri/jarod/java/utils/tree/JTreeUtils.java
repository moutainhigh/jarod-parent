//package pri.jarod.java.utils.tree;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.beanutils.PropertyUtils;
//import org.apache.commons.lang3.StringUtils;
//import pri.jarod.java.json.JSON;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * @author kongdegang
// * @date 2020/7/20 14:42
// */
//public final class JTreeUtils {
//
//    /**
//     * 利用反射机制，构造树形结构的视图数据
//     * @param datas 源数据集合
//     * @param idProperty E中实体类的id属性
//     * @param parentIdProperty E中实体类的对应父类的id属性
//     * @param childrenProperty E中实体类的对应子节点属性
//     * @param levelProperty E中实体类的对应树层级的属性
//     * @param <E> 源实体类型
//     * @return
//     */
//    public static <E> List<E> createTrees(List<E> datas, String idProperty,
//                                          String parentIdProperty,
//                                          String childrenProperty,
//                                          String levelProperty) {
//        Objects.requireNonNull(parentIdProperty, "E泛型中的父类id属性名不可为空");
//        Objects.requireNonNull(parentIdProperty, "E泛型中的子类集合属性名不可为空");
//        Objects.requireNonNull(parentIdProperty, "E泛型中的实体类id属性名不可为空");
//
//        Map<Object, E> dataMap = new LinkedHashMap<>(datas.size());
//        List<E> retList = new ArrayList<>(datas.size());
//        Object id = null;
//        for (E data : datas) {
//            try {
//                id = PropertyUtils.getProperty(data, idProperty);
//            } catch (Exception e) {
//                throw new IllegalArgumentException("利用反射获取实体属性setter/getter失败");
//            }
//            try {
//                //noinspection unchecked
//                E cloneBean = (E) BeanUtils.cloneBean(data);
//                dataMap.put(id, cloneBean);
//            } catch (Exception e) {
//                throw new IllegalArgumentException("利用反射获取实体属性setter/getter失败");
//            }
//        }
//        try {
//            int level = 0;
//            boolean setLevel = StringUtils.isNotBlank(parentIdProperty);
//            for (E data : dataMap.values()) {
//                Object parentId = PropertyUtils.getProperty(data, parentIdProperty);
//                if ((parentId == null) || (!dataMap.containsKey(parentId))) {
//                    if (setLevel) {
//                        PropertyUtils.setProperty(data, levelProperty, level);
//                    }
//                    retList.add(data);
//                } else {
//                    E parent = dataMap.get(parentId);
//                    Object children = PropertyUtils.getProperty(parent, childrenProperty);
//                    if (children == null) {
//                        children = new ArrayList<>();
//                        PropertyUtils.setProperty(parent, childrenProperty, children);
//                    }
//                    if (!(children instanceof Collection)) {
//                        throw new ClassCastException("子节点属性[" + childrenProperty + "]无法转换为Collection集合");
//                    }
//                    if (setLevel){
//                        Object parentlevelObj = PropertyUtils.getProperty(parent, levelProperty);
//                        if (parentlevelObj instanceof Number){
//                            Number number = (Number) parentlevelObj;
//                            level = number.intValue();
//                        }
//                        PropertyUtils.setProperty(data, levelProperty, ++level);
//                    }
//                    Collection list = (Collection) children;
//                    list.add(data);
//                }
//            }
//        } catch (Exception e) {
//            throw new IllegalArgumentException("利用反射获取实体属性setter/getter失败");
//        }
//        return retList;
//    }
//
//    /**
//     *
//     *  data: [{
//     *           id: 1,
//     *           label: '一级 1',
//     *           children: [{
//     *             id: 4,
//     *             label: '二级 1-1',
//     *             children: [{
//     *               id: 9,
//     *               label: '三级 1-1-1'
//     *             }, {
//     *               id: 10,
//     *               label: '三级 1-1-2'
//     *             }]
//     *           }]
//     *         }, {
//     *           id: 2,
//     *           label: '一级 2',
//     *           children: [{
//     *             id: 5,
//     *             label: '二级 2-1'
//     *           }, {
//     *             id: 6,
//     *             label: '二级 2-2'
//     *           }]
//     *         }, {
//     *           id: 3,
//     *           label: '一级 3',
//     *           children: [{
//     *             id: 7,
//     *             label: '二级 3-1'
//     *           }, {
//     *             id: 8,
//     *             label: '二级 3-2'
//     *           }]
//     *         }]
//     *  find root
//     * @param args
//     */
//    public static void main(String[] args) {
//        String jsonStr = "[{\"id\":\"1\",\"name\":\"员工\",\"nextNodes\":[]},{\"id\":\"2\",\"name\":\"内部机构\",\"nextNodes\":[]},{\"id\":\"3\",\"parentId\":\"1\",\"name\":\"基本信息\",\"nextNodes\":[]},{\"id\":\"4\",\"parentId\":\"3\",\"name\":\"账号信息\",\"nextNodes\":[]}]";
//        new Gson().fromJson(jsonStr, List<TreeNode>.class);
//        List<TreeNode> collect = basicStandardCategories.stream()
//                .map(b -> {
//                    TreeNode treeNode = new TreeNode();
//                    treeNode.setId(b.getId());
//                    treeNode.setName(b.getName());
//                    treeNode.setParentId(b.getParentId());
//                    return treeNode;
//                }).collect(Collectors.toList());
//        List<TreeNode> trees = createTrees(collect, "id", "parentId",
//                "nextNodes", "level");
//        System.out.println(trees);
//
//    }
//    private  static class TreeNode {
//        private String id;
//        private String parentId;
//        private String name;
//        private List<TreeNode> nextNodes = new ArrayList<>();
//        private Integer level;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getParentId() {
//            return parentId;
//        }
//
//        public void setParentId(String parentId) {
//            this.parentId = parentId;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public List<TreeNode> getNextNodes() {
//            return nextNodes;
//        }
//
//        public void setNextNodes(List<TreeNode> nextNodes) {
//            this.nextNodes = nextNodes;
//        }
//
//        public Integer getLevel() {
//            return level;
//        }
//
//        public void setLevel(Integer level) {
//            this.level = level;
//        }
//    }
//}
