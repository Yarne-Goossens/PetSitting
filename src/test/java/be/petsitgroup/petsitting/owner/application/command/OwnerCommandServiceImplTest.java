package be.petsitgroup.petsitting.owner.application.command;

import be.petsitgroup.petsitting.model.Owner;
import be.petsitgroup.petsitting.model.Role;
import be.petsitgroup.petsitting.owner.domain.OwnerRegisteredEvent;
import be.petsitgroup.petsitting.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerCommandServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private OwnerCommandServiceImpl ownerCommandService;

    @Test
    void registerOwner_successful() {
        // arrange
        RegisterOwnerCommand command = new RegisterOwnerCommand(
                "John Doe",
                "john@example.com",
                "secret123",
                "Main street 1",
                "0499123456");

        when(ownerRepository.existsByEmail(command.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(command.getPassword()))
                .thenReturn("ENCODED");

        Owner saved = new Owner();
        saved.setId(1L);
        saved.setName(command.getName());
        saved.setEmail(command.getEmail());
        saved.setPassword("ENCODED");
        saved.setRole(Role.OWNER);

        when(ownerRepository.save(any(Owner.class)))
                .thenReturn(saved);

        // act
        Long ownerId = ownerCommandService.registerOwner(command);

        // assert: correct id returned
        assertThat(ownerId).isEqualTo(1L);

        // assert: owner saved with encoded password and OWNER role
        ArgumentCaptor<Owner> ownerCaptor = ArgumentCaptor.forClass(Owner.class);
        verify(ownerRepository).save(ownerCaptor.capture());
        Owner toSave = ownerCaptor.getValue();

        assertThat(toSave.getEmail()).isEqualTo("john@example.com");
        assertThat(toSave.getPassword()).isEqualTo("ENCODED");
        assertThat(toSave.getRole()).isEqualTo(Role.OWNER);

        // assert: event published
        verify(eventPublisher).publishEvent(any(OwnerRegisteredEvent.class));
    }

    @Test
    void registerOwner_whenEmailExists_throwsException() {
        // arrange
        RegisterOwnerCommand command = new RegisterOwnerCommand(
                "John Doe",
                "john@example.com",
                "secret123",
                "Main street 1",
                "0499123456");

        when(ownerRepository.existsByEmail(command.getEmail()))
                .thenReturn(true);

        // act + assert
        assertThatThrownBy(() -> ownerCommandService.registerOwner(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email already in use");

        verify(ownerRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }
}
