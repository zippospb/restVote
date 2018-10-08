package ru.zippospb.restvote.service;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.zippospb.restvote.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract class AbstractServiceTest {
    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @Autowired
    protected CacheManager cacheManager;

    void validateRootCause(Runnable runnable) {
        assertThrows(ConstraintViolationException.class, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}
