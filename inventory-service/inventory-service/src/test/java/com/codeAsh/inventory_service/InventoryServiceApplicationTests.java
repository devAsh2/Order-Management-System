package com.codeAsh.inventory_service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class InventoryServiceApplicationTests {

	// MySQL container for integration tests
	// Configure database name, username and password
	@Container
	@ServiceConnection
	// TODO: Initialize MySQL container
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.3.0")
	.withDatabaseName("testdb")
	.withUsername("test")
	.withPassword("test");

	// Port that Spring Boot assigns for tests
	@LocalServerPort
	private Integer port;

	// Set up RestAssured before each test
	@BeforeEach
	void setup() {
		// TODO: Configure RestAssured base URI and port
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
 	}

	// Start MySQL container before any tests run
	static {
		// TODO: Start MySQL container
		mySQLContainer.start();
	}

	// Clean up resources after all tests complete
	@AfterAll
	static void cleanup() {
		// TODO: Close MySQL container
		mySQLContainer.close();
	}

	// Test inventory availability check for in-stock and out-of-stock scenarios
	@Test 
	void shouldReadInventory() {
		// TODO: Test in-stock scenario with valid SKU and quantity
		boolean response = RestAssured.given()
		.when()
		.get("/api/inventory?skuCode=iphone_15&quantity=1")
		.then()
		.log().all()
		.statusCode(200)
		.extract().response().as(Boolean.class);

		assertTrue(response);
		// TODO: Test out-of-stock scenario with valid SKU and large quantity
		boolean negativeResponse = RestAssured.given()
		.when()
		.get("/api/inventory?skuCode=iphone_15&quantity=101")
		.then()
		.log().all()
		.statusCode(200)
		.extract().response().as(Boolean.class);

		assertFalse(negativeResponse);
	}

	// Test inventory check with non-existent SKU code
	@Test
	void shouldHandleNonExistentSkuCode() {
		// TODO: Test scenario with non-existent SKU code

		boolean response = RestAssured.given()
		.when()
		.get("/api/inventory?skuCode=NONEXISTENT&quantity=1")
		.then()
		.log().all()
		.statusCode(200)
		.extract().response().as(Boolean.class);

		assertFalse(response);
	}

	
}
