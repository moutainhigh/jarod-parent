package pri.jarod.java.core.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BM25 相关度算法实现
 * <p>
 * 公式：   Similarity(word|documents)=IDFScore∗(k+1)∗tf / k∗(1.0−b+b∗|D|avgDl)+tf
 *
 * @author zhaojunfu
 * @author Jarod.Kong
 * @date 2020/9/10 11:09
 */
public class BM25 {
    //常量k，用来限制TF值的增长极限 默认1.2
    private double k = 1.2;
    //b是一个常数，它的作用是规定L对评分的影响有多大
    private double b;

    /**
     * 默认
     */
    public BM25() {
        super();
        this.k = 2;
        this.b = 0.75;
    }

    /**
     * BM25 相关度算法实现
     *
     * @param k 常量k，用来限制TF值的增长极限 默认1.2
     * @param b b是一个常数，它的作用是规定L对评分的影响有多大
     */
    public BM25(double k, double b) {
        super();
        this.k = k;
        this.b = b;
    }

    /**
     * BM25 相关度算法实现 k=1.2
     *
     * @param b b是一个常数，它的作用是规定L对评分的影响有多大
     */
    public BM25(double b) {
        super();
        this.b = b;
    }

    /**
     * 计算 关键词 在文档中的相关值
     *
     * @param idf 关键词的逆文档频率
     * @param tf  关键词在文档中的词频
     * @param l   当前文档长度/平均文档长度
     * @return
     */
    public double cal(double idf, double tf, double l) {
        double v = 0;
        v = (idf * (k + 1) * tf) / (k * (1.0 - b + b * l) + tf);
        return v;
    }

    /**
     * 计算 关键词 在文档中的相关值
     *
     * @param keywords 关键词
     * @param doc      关键词所在文档的全文
     * @param docs     所有文档集
     * @param idfMap   idf字典表
     * @return
     */
    public double cal(String keywords, String doc, List<String> docs, Map<String, Double> idfMap) {
        Double idf = idfMap.get(keywords);
        //noinspection AliControlFlowStatementWithoutBraces
        if (null == idf) {
            idf = 1.0d;
        }
        double tf = (double) StringUtils.count(doc, keywords) * keywords.length() / doc.length();
        double avgLength = calAvgLength(docs);
        double l = (double) doc.length() / avgLength;
        return cal(idf, tf, l);
    }

    private double calAvgLength(List<String> docs) {
        if (docs == null || docs.size() <= 0) {
            throw new RuntimeException("给定文档集不能为空");
        }

        int s = 0;
        for (String d : docs) {
            s += d.length();
        }
        return (double) s / (double) docs.size();
    }

    private static class StringUtils {
        /**
         * tf：是指当前文本中出现这个单词的频次，在这个文本里面出现越多当然越重要啦。
         *
         * @param doc      当前文本
         * @param keywords 这个单词
         * @return 频次
         */
        public static int count(String doc, String keywords) {
            int count = 0;
            Pattern p = Pattern.compile(keywords);
            Matcher m = p.matcher(doc);
            while (m.find()) {
                count++;
            }
            return count;
        }
    }

}