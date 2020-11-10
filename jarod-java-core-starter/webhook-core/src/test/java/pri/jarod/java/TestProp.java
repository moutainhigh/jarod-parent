package pri.jarod.java;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author Jarod.Kong
 * @date 2020/11/10 14:50
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestProp implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;


}
