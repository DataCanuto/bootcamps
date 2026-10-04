package springbootai.budgeting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ToolCallingIT {

    @Test
    void testApplicationContextLoads() {
        assertNotNull(null, "Application should load successfully");
    }
}
