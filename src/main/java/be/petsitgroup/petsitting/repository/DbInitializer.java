package be.petsitgroup.petsitting.repository;

import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Pets;
import be.petsitgroup.petsitting.model.Petsitter;
import be.petsitgroup.petsitting.model.Playdate;
import be.petsitgroup.petsitting.model.Review;
import be.petsitgroup.petsitting.model.Role;
import be.petsitgroup.petsitting.repository.OwnerRepository;
import be.petsitgroup.petsitting.repository.PetsRepository;
import be.petsitgroup.petsitting.repository.PetsitterRepository;
import be.petsitgroup.petsitting.repository.PlaydateRepository;
import be.petsitgroup.petsitting.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DbInitializer {

    private static final Logger log = LoggerFactory.getLogger(DbInitializer.class);

    @Bean
    public CommandLineRunner initData(OwnerRepository ownerRepository,
            PetsRepository petRepository,
            PetsitterRepository petsitterRepository,
            PlaydateRepository playdateRepository,
            ReviewRepository reviewRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {

            if (ownerRepository.count() > 0) {
                log.info("Database already contains data, skipping initialization.");
                return;
            }

            log.info("Starting database initialization...");

            // 1) Owners
            Owner owner1 = new Owner();
            owner1.setName("Alice Owner");
            owner1.setEmail("owner1@example.com");
            owner1.setPassword(passwordEncoder.encode("password1"));
            owner1.setAddress("Owner Street 1");
            owner1.setPhoneNumber("0499000001");
            owner1.setRole(Role.OWNER);
            owner1 = ownerRepository.save(owner1);

            Owner owner2 = new Owner();
            owner2.setName("Bob Sitter");
            owner2.setEmail("petsitter1@example.com");
            owner2.setPassword(passwordEncoder.encode("password2"));
            owner2.setAddress("Sitter Avenue 2");
            owner2.setPhoneNumber("0499000002");
            owner2.setRole(Role.PETSITTER); // owner who is also a petsitter
            owner2 = ownerRepository.save(owner2);

            log.info("Created owners: {}, {}", owner1.getId(), owner2.getId());

            // 2) Pet for owner1
            Pets pet = new Pets();
            pet.setName("Buddy");
            pet.setType("Dog");
            pet.setAge(3);
            pet.setAvailableForPlaydate(true);
            pet.setOwner(owner1);
            pet = petRepository.save(pet);

            log.info("Created pet id={} for owner1", pet.getId());

            // 3) Petsitter profile for owner2
            Petsitter petsitter = new Petsitter();
            petsitter.setOwner(owner2);
            petsitter.setName(owner2.getName());
            petsitter.setEmail(owner2.getEmail());
            petsitter.setPhoneNumber(owner2.getPhoneNumber());
            petsitter.setBio("Experienced dog walker and petsitter.");
            petsitter.setHourlyRate(new BigDecimal("15.00"));
            petsitter.setYearsExperience(3);
            petsitter.setAvailableDate(LocalDate.now().plusDays(1));
            petsitter.setRating(0.0); // will be updated when reviews come in
            petsitter = petsitterRepository.save(petsitter);

            log.info("Created petsitter id={} for owner2", petsitter.getId());

            // 4) Playdate between pet and petsitter
            Playdate playdate = new Playdate();
            playdate.setPet(pet);
            playdate.setPetsitter(petsitter);
            playdate.setDate(LocalDateTime.now().plusDays(2));
            playdate.setLocation("City Park");
            playdate.setPrice(new BigDecimal("25.00"));
            playdate.setStatus("CONFIRMED");
            playdate.setNotes("First playdate for Buddy.");
            playdate = playdateRepository.save(playdate);

            log.info("Created playdate id={} between pet {} and petsitter {}",
                    playdate.getId(), pet.getId(), petsitter.getId());

            // 5) Review for that playdate
            Review review = new Review();
            review.setPlaydate(playdate);
            review.setRating(5);
            review.setComment("Great experience! Buddy was very happy.");
            review.setDate(LocalDateTime.now().plusDays(3));
            review = reviewRepository.save(review);

            log.info("Created review id={} for playdate {}", review.getId(), playdate.getId());

            log.info("Database initialization finished.");
        };
    }
}
