package se.sofiatherese.vhvh.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import se.sofiatherese.vhvh.config.AppConfig;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    private UserRepository mockUserRepository;
    private AppConfig mockAppConfig;
    private UserServiceImpl mockUserServiceImpl;
    private UserModel expectedUserModel;
    private BindingResult mockResult;

    @BeforeEach
    public void setup() {
        mockUserRepository = mock(UserRepository.class);
        mockAppConfig = mock(AppConfig.class);

        // Create a new instance of UserServiceImpl using the mock dependencies
        mockUserServiceImpl = new UserServiceImpl(mockUserRepository, mockAppConfig);

        expectedUserModel = UserModel.builder()
                .username("anna.alm@mail.com")
                .password("12345678")
                .firstname("Anna")
                .lastname("Alm")
                .role(UserRole.USER)
                .build();

        mockResult = mock(BindingResult.class);
    }

    @Test
    public void createUser() throws Exception {
        // Arrange
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockUserRepository.existsByUsername(expectedUserModel.getUsername())).thenReturn(false);
        when(mockUserRepository.existsById(1L)).thenReturn(false); // Assuming user with ID 1 doesn't exist

        // Act
        ResponseEntity<UserModel> response = mockUserServiceImpl.createUser(expectedUserModel, mockResult);

        // Assert
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(expectedUserModel, response.getBody());
    }

}
