package stepDefinitions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SimCardActivatorStepDefinitions {

    private String iccid;
    private String customerEmail;
    private final RestTemplate restTemplate = new RestTemplate();

    @Given("the SIM card ICCID is {string} and customer email is {string}")
    public void the_sim_card_iccid_and_customer_email(String iccid, String email) {
        this.iccid = iccid;
        this.customerEmail = email;
    }

    @When("I submit the activation request")
    public void i_submit_the_activation_request() {
        String url = "http://localhost:8080/activate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"iccid\": \"%s\", \"customerEmail\": \"%s\"}", iccid, customerEmail);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("the SIM card should be activated with ID {int}")
    public void the_sim_card_should_be_activated_with_id(Integer id) {
        String url = "http://localhost:8080/query?simCardId=" + id;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(true, response.getBody().get("active"));
    }

    @Then("the SIM card should not be activated with ID {int}")
    public void the_sim_card_should_not_be_activated_with_id(Integer id) {
        String url = "http://localhost:8080/query?simCardId=" + id;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(false, response.getBody().get("active"));
    }
}
