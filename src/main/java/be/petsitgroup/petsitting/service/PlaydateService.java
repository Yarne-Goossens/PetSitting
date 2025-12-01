package be.petsitgroup.petsitting.service;

import be.petsitgroup.petsitting.dto.CreatePlaydateRequest;
import be.petsitgroup.petsitting.dto.PlaydateResponse;
import be.petsitgroup.petsitting.dto.UpdatePlaydateStatusRequest;

import java.util.List;

public interface PlaydateService {

    PlaydateResponse createPlaydate(CreatePlaydateRequest request);

    List<PlaydateResponse> getMyPlaydatesAsOwner();

    List<PlaydateResponse> getMyPlaydatesAsPetsitter();

    PlaydateResponse updateStatus(Long playdateId, UpdatePlaydateStatusRequest request);
}
