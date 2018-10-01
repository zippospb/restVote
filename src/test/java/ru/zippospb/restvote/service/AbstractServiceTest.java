package ru.zippospb.restvote.service;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {
//TODO добавить во все сервис-тесты проверку на not found
    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }
}
