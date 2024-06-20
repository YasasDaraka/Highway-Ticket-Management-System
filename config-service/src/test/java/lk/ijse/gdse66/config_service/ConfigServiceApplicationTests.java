package lk.ijse.gdse66.config_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootTest
@EnableDiscoveryClient
@EnableConfigServer
class ConfigServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
