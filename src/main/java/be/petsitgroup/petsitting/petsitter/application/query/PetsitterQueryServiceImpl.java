package be.petsitgroup.petsitting.petsitter.application.query;

import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetsitterQueryServiceImpl implements PetsitterQueryService {

    private final PetsitterRepository petsitterRepository;

    public PetsitterQueryServiceImpl(PetsitterRepository petsitterRepository) {
        this.petsitterRepository = petsitterRepository;
    }

    @Override
    public PetsitterView getByOwnerId(Long ownerId) {
        Petsitter p = petsitterRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Petsitter profile not found for owner"));

        return toView(p);
    }

    @Override
    public List<PetsitterView> findAvailableOn(LocalDate date) {
        // adapt to whatever method you have in PetsitterRepository
        List<Petsitter> list = petsitterRepository.findByAvailableDate(date);
        return list.stream()
                .map(this::toView)
                .collect(Collectors.toList());
    }

    private PetsitterView toView(Petsitter p) {
        return new PetsitterView(
                p.getId(),
                p.getName(),
                p.getEmail(),
                p.getPhoneNumber(),
                p.getHourlyRate(),
                p.getYearsExperience(),
                p.getRating(),
                p.getBio(),
                p.getAvailableDate());
    }
}
