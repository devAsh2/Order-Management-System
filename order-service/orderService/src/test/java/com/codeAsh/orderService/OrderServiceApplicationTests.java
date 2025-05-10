package com.codeAsh.orderService;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration test class for Order Service
 * Uses TestContainers to run MySQL in a Docker container for testing
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	// Creates a MySQL container for testing
	// @ServiceConnection automatically configures the connection
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

	// Injects the random port assigned to the application
	@LocalServerPort
	private Integer port;

	// Static block to start the MySQL container before any tests run
	static {
		mySQLContainer.start();
	}

	// Setup method that runs before each test
	@BeforeEach
	void setup() {
		// Configure RestAssured to use the application's base URI and port
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	/**
	 * Test method to verify order submission
	 * Sends a POST request to create an order and verifies the response
	 */
	@Test
	void shouldSubmitOrder() {
		// JSON payload for creating an order
		String submitOrderJson = """
				{
				    "orderLineItemsDtoList": [
					    {
					        "skuCode": "iphone_15",
					        "price": 1000,
					        "quantity": 1
					    }
				    ]
				}
				""";

		// Send POST request and verify response
		var responseBodyString = RestAssured.given()
		.contentType("application/json")
		.body(submitOrderJson)
		.when()
		.post("/api/order")
		.then()
		.log().all()
		.statusCode(201)
		.extract().body().asString();

		// Verify the response message
		assertThat(responseBodyString, Matchers.is("Order placed successfully"));
	}
}
