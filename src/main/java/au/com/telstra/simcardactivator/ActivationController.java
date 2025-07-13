package au.com.telstra.simcardactivator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivationController {

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody ActivationRequest request) {
        if (request.getIccid() == null || request.getCustomerEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing ICCID or email");
        }

        // MOCK logic (no actual API call)
        return ResponseEntity.ok("SIM activated successfully (mock)");
    }
}
