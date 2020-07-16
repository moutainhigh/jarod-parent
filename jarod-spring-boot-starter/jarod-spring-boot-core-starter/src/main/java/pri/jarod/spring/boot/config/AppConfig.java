package pri.jarod.spring.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @see Global 不是基于spring方式调用
 * @see AppConfig 基于面向对象的Spring注入方式 通过setter/getter调用
 * eg:
 * <code>
 *    @Autowire AppConfig appConfig;
 *    {
 *        appConfig.getProfile();
 *    }
 * </code>
 * @author kongdegang
 * @date 2020/7/3 9:14
 */
@Component
public class AppConfig {

    @Component
    @ConfigurationProperties(prefix = "pinyou")
    public class BaseConfig{

        /** 项目名称 */
        private  String name;

        /** 版本 */
        private  String version;

        /** 版权年份 */
        private  String copyrightYear;

        /** 实例演示开关 */
        private  boolean demoEnabled;

        /** 上传路径 */
        private  String profile;

        /** 获取地址开关 */
        private  boolean addressEnabled;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getCopyrightYear() {
            return copyrightYear;
        }

        public void setCopyrightYear(String copyrightYear) {
            this.copyrightYear = copyrightYear;
        }

        public boolean isDemoEnabled() {
            return demoEnabled;
        }

        public void setDemoEnabled(boolean demoEnabled) {
            this.demoEnabled = demoEnabled;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public boolean isAddressEnabled() {
            return addressEnabled;
        }

        public void setAddressEnabled(boolean addressEnabled) {
            this.addressEnabled = addressEnabled;
        }
    }

    @Autowired
    private BaseConfig baseConfig;

    public BaseConfig getBaseConfig() {
        return baseConfig;
    }

    @Value("${server.port}")
    private Integer serverPort;

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    

}
