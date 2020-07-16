package pri.jarod.bigdata.flink;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * 抽象传输dtc
 *
 * @author kongdegang
 * @date 2020/7/9 9:45
 */
@Slf4j
public abstract class BaseStreamDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Map<String, Class<? extends BaseStreamDto>>
            SERVICE_FILE_SYSTEMS = new HashMap<String, Class<? extends BaseStreamDto>>();

    @Setter
    @Getter
    private Timestamp sourceTimestamp;

    public static Class<? extends BaseStreamDto> getBaseStreamDtoClass(String scheme) throws IOException {
        if (!FILE_SYSTEMS_LOADED) {
            loadFileSystems();
        }
        Class<? extends BaseStreamDto> clazz = null;
        if (clazz == null) {
            clazz = SERVICE_FILE_SYSTEMS.get(scheme);
        }
        if (clazz == null) {
            throw new IOException("No BaseStreamDto for scheme: " + scheme);
        }
        return clazz;
    }

    // making it volatile to be able to do a double checked locking
    private volatile static boolean FILE_SYSTEMS_LOADED = false;

    private static void loadFileSystems() {
        synchronized (BaseStreamDto.class) {
            if (!FILE_SYSTEMS_LOADED) {
                ServiceLoader<BaseStreamDto> serviceLoader = ServiceLoader.load(BaseStreamDto.class);
                for (BaseStreamDto baseStreamDto : serviceLoader) {
                    BaseStreamDto fs = null;
                    try {
                        fs = baseStreamDto;
                        try {
                            SERVICE_FILE_SYSTEMS.put(fs.getScheme(), fs.getClass());
                        } catch (Exception e) {
                            log.warn("Cannot load: " + fs + " from " +
                                    ClassUtil.findContainingJar(fs.getClass()), e);
                        }
                    } catch (ServiceConfigurationError ee) {
                        log.warn("Cannot load filesystem", ee);
                    }
                }
                FILE_SYSTEMS_LOADED = true;
            }
        }
    }

    protected abstract String getScheme();
}
