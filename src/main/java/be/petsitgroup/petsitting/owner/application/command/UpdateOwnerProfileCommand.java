package be.petsitgroup.petsitting.owner.application.command;

public class UpdateOwnerProfileCommand {

    private Long ownerId;
    private String name;
    private String address;
    private String phoneNumber;

    public UpdateOwnerProfileCommand() {
    }

    public UpdateOwnerProfileCommand(Long ownerId, String name,
            String address, String phoneNumber) {
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
