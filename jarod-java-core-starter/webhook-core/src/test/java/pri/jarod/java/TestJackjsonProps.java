package pri.jarod.java;

import cn.hutool.core.io.resource.ClassPathResource;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;

import java.io.*;
import java.util.Properties;

/**
 * @author Jarod.Kong
 * @date 2020/11/10 14:44
 */
public class TestJackjsonProps {
    public static void main(String[] args) throws IOException {
        JavaPropsMapper mapper = new JavaPropsMapper();
        Properties properties = new Properties();
        ClassPathResource classPathResource = new ClassPathResource("webhook.properties");
        properties.load(classPathResource.getStream());
        TestProp otherValue = mapper.readPropertiesAs(properties, TestProp.class);
        System.out.println(otherValue);
    }
}
