package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivationController {

    @Autowired
    private SimCardRecordRepository repository;

    @PostMapping("/activate")
    public ActivationResponse activateSim(@RequestBody ActivationRequest request) {
        // 👇 Mocking the response from actuator — always true for local testing
        boolean isActive = true;

        // Save to DB
        SimCardRecord record = new SimCardRecord(request.getIccid(), request.getCustomerEmail(), isActive);
        repository.save(record);

        return new ActivationResponse(isActive);
    }

    @GetMapping("/query")
    public SimCardRecord querySim(@RequestParam Long simCardId) {
        return repository.findById(simCardId).orElse(null);
    }
}
