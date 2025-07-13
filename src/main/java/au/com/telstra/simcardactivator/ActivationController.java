package au.com.telstra.simcardactivator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ActivationController {

    private final RestTemplate restTemplate;
    private final SimCardRecordRepository repository;

    public ActivationController(RestTemplate restTemplate, SimCardRecordRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @PostMapping("/activate")
    public ActivationResponse activateSim(@RequestBody ActivationRequest request) {
        // Prepare request to actuator
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(
                "{\"iccid\":\"" + request.getIccid() + "\"}", headers
        );

        // Send request to actuator
        ResponseEntity<ActivationResponse> response = restTemplate.postForEntity(
                "http://localhost:8444/actuate", requestEntity, ActivationResponse.class
        );

        ActivationResponse responseBody = response.getBody();
        boolean isActive = responseBody != null && responseBody.isSuccess();

        // Save to DB
        SimCardRecord simCardRecord = new SimCardRecord(
                request.getIccid(), request.getCustomerEmail(), isActive
        );
        repository.save(simCardRecord);

        return new ActivationResponse(isActive);
    }

    @GetMapping("/query")
    public SimCardRecord querySim(@RequestParam Long simCardId) {
        return repository.findById(simCardId).orElse(null);
    }
}
