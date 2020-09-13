package cn.sunline.compare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h3>compare-util</h3>
 *
 * <p></p>
 *
 * @Author zcz
 * @Date 2020-07-02 16:12
 */
@SpringBootApplication
@MapperScan("cn.sunline.compare.dao")
public class CompareApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompareApplication.class, args);
    }
}
