import org.deskify.model.api.request.CreateUserRequest;
import org.deskify.model.domain.AccountType;
import org.deskify.model.domain.dtoUser;
import org.deskify.repository.UserRepository;
import org.deskify.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    List<dtoUser> expectedUsers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void cleanUp() {
        expectedUsers.clear();
    }

    @Test
    void createUserShouldCreateUserClassFromRequest() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("john.doe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        dtoUser user = dtoUser.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .accountType(AccountType.USER)
                .email(request.getEmail())
                .build();

        when(userRepository.save(user)).thenReturn(user);

        dtoUser result = userService.createUser(request);

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getAccountType(), result.getAccountType());
    }

    @Test
    void createUserShouldThrowExceptionIfUsernameIsAlreadyTaken() {
        CreateUserRequest request = new CreateUserRequest("jane_smith", "password123", "Jane", "Smith", "jane.smith@example.com");
        when(userRepository.findAllByUsername(request.getUsername())).thenReturn(Arrays.asList(new dtoUser(1L, "jane_smith", "password123", "Jane", AccountType.USER, "Smith", "jane.smith@example.com")));


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(request);
        });
        assertEquals("Username or email is already taken", exception.getMessage());
    }

    @Test
    void createUserShouldThrowExceptionIfEmailIsAlreadyTaken() {
        CreateUserRequest request = new CreateUserRequest("jane_smith", "password123", "Jane", "Smith", "jane.smith@example.com");
        when(userRepository.findAllByEmail(request.getEmail())).thenReturn(Arrays.asList(new dtoUser(1L, "jane_smith", "password123", "Jane", AccountType.USER, "Smith", "jane.smith@example.com")));


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(request);
        });
        assertEquals("Username or email is already taken", exception.getMessage());
    }

    @Test
    void createUserShouldThrowExceptionIfEmailIsIncorrect() {
        CreateUserRequest request = new CreateUserRequest("jane_smith", "password123", "Jane", "Smith", "invalid-email");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(request);
        });
        assertEquals("Incorrect email", exception.getMessage());
    }

    @Test
    void fetchUsersShouldReturnAllUsers() {
        dtoUser user1 = new dtoUser(2L, "jane_smith", "password123", "Jane", AccountType.ADMIN, "Smith", "jane.smith@example.com");
        dtoUser user2 = new dtoUser(3L, "bob_johnson", "password123", "Bob", AccountType.USER, "Johnson", "bob.johnson@example.com");

        expectedUsers.add(user1);
        expectedUsers.add(user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<dtoUser> result = userService.fetchUsers(null, null, null, null, null);
        assertEquals(expectedUsers.size(), result.size());
        assertEquals(expectedUsers.get(0).getId(), result.get(0).getId());
        assertEquals(expectedUsers.get(1).getId(), result.get(1).getId());
    }

    @Test
    void fetchUsersByFirstNameShouldReturnUsersByFirstName() {
        String firstName = "John";
        expectedUsers.add(new dtoUser(1L, "john123", "password", "John", AccountType.USER, "Doe", "john.doe@example.com"));

        when(userRepository.findAllByFirstName(firstName)).thenReturn(expectedUsers);

        List<dtoUser> actualUsers = userService.fetchUsers(null, null, firstName, null, null);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void fetchUsersByLastNameShouldReturnUsersByLastName() {
        String lastName = "Doe";
        expectedUsers.add(new dtoUser(1L, "john123", "password", "John", AccountType.USER, "Doe", "john.doe@example.com"));

        when(userRepository.findAllByLastName(lastName)).thenReturn(expectedUsers);

        List<dtoUser> actualUsers = userService.fetchUsers(null, null, null, lastName, null);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void fetchUsersByNonExistantLastNameShouldReturnNull() {
        String lastName = "Marston";
        expectedUsers.add(null);

        when(userRepository.findAllByLastName(lastName)).thenReturn(expectedUsers);

        List<dtoUser> actualUsers = userService.fetchUsers(null, null, null, lastName, null);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void fetchUsersByEmailShouldReturnUsersByEmail() {
        String email = "Doe";
        expectedUsers.add(new dtoUser(1L, "john123", "password", "John", AccountType.USER, "Doe", "john.doe@example.com"));

        when(userRepository.findAllByEmail(email)).thenReturn(expectedUsers);

        List<dtoUser> actualUsers = userService.fetchUsers(null, null, null, null, email);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void fetchUsersByNonExistantIdShouldReturnNull() {
        Long id = 1L;
        expectedUsers.add(null);

        when(userRepository.findAllById(id)).thenReturn(expectedUsers);

        List<dtoUser> actualUsers = userService.fetchUsers(id, null, null, null, null);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void fetchUsersByIdShouldReturnUserById() {
        Long id = 3L;
        expectedUsers.add(new dtoUser(1L, "john123", "password", "John", AccountType.USER, "Doe", "john.doe@example.com"));

        when(userRepository.findAllById(id)).thenReturn(expectedUsers);

        List<dtoUser> actualUsers = userService.fetchUsers(id, null, null, null, null);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void deleteUserShoudDeleteUserById() {
        Long id = 1L;
        dtoUser user = dtoUser.builder()
                .id(id)
                .username("john_doe")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .accountType(AccountType.USER)
                .email("john.doe@example.com")
                .build();

        when(userRepository.findUserById(id)).thenReturn(user);

        userService.deleteUserByUsername(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void updateUserShouldUpdateAllFields() {
        Long id = 1L;
        String newUsername = "new_username";
        String newPassword = "new_password";
        String newFirstName = "new_first_name";
        String newLastName = "new_last_name";
        AccountType newAccountType = AccountType.ADMIN;
        String newEmail = "new_email@example.com";

        dtoUser user = dtoUser.builder()
                .id(id).username("username")
                .password("password")
                .firstName("first_name")
                .lastName("last_name")
                .accountType(AccountType.USER)
                .email("email@example.com")
                .build();

        when(userRepository.findUserById(id)).thenReturn(user);

        userService.updateUserInformation(id, newUsername, newPassword, newFirstName, newLastName, newAccountType, newEmail);

        verify(userRepository).findUserById(id);

        assertEquals(newUsername, user.getUsername());
        assertEquals(newPassword, user.getPassword());
        assertEquals(newFirstName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
        assertEquals(newAccountType, user.getAccountType());
        assertEquals(newEmail, user.getEmail());


        verify(userRepository, times(1)).save(user);
    }

    @Test
    void loadUserByUsernameShouldReturnValidUser() {

        String username = "john.doe@example.com";
        String password = "password123";
        dtoUser expectedUser = dtoUser.builder()
                .username(username).id(1L).build();
        Mockito.when(userRepository.findUserByUsernameAndPassword(username, password)).thenReturn(Optional.of(expectedUser));

        Optional<dtoUser> user = userService.loadUserByUsernameAndPassword(username, password);
        assertTrue(user.isPresent());
        assertEquals(user.get().getUsername(), expectedUser.getUsername());
        assertEquals(user.get().getId(), expectedUser.getId());

    }

    @Test()
    void validateUserShouldReturnEmptyUser() {
        String username = "john.fake@example.com";
        String password = "fake13243";
        dtoUser user = dtoUser.builder().username(username).id(999L).build();
        Mockito.when(userRepository.findUserByUsernameAndPassword(username, password)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.loadUserByUsernameAndPassword(username, password);
        });
        assertEquals("User not found with Username: " + username, exception.getMessage());

    }

}