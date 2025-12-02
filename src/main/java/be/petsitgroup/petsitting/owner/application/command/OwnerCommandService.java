package be.petsitgroup.petsitting.owner.application.command;

public interface OwnerCommandService {

    Long registerOwner(RegisterOwnerCommand command);

    void updateProfile(UpdateOwnerProfileCommand command);
}
