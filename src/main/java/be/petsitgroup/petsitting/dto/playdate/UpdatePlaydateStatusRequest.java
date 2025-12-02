package be.petsitgroup.petsitting.dto.playdate;

public class UpdatePlaydateStatusRequest {

    private String status; // e.g. REQUESTED, CONFIRMED, COMPLETED, CANCELLED

    public UpdatePlaydateStatusRequest() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
