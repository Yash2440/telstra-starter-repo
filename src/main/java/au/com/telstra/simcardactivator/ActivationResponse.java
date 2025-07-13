package au.com.telstra.simcardactivator;

public class ActivationResponse {
    private boolean success;

    public ActivationResponse() {
        // Default constructor needed for JSON deserialization
    }

    public ActivationResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
