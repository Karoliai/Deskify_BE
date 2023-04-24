package org.deskify.service;

import org.deskify.model.api.request.CreateUserRequest;
import org.deskify.model.api.request.LoginRequest;
import org.deskify.model.api.response.LoginResponse;
import org.deskify.model.domain.AccountType;
import org.deskify.model.domain.dtoUser;
import org.deskify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.deskify.utils.Validator.isValidEmail;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public dtoUser createUser(CreateUserRequest request) {
        dtoUser user = dtoUser.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .accountType(AccountType.USER)
                .email(request.getEmail())
                .build();

        if (!validateUser(user)) {
            throw new IllegalArgumentException("Username or email is already taken");
        }
        if (!isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Incorrect email");
        }

        return userRepository.save(user);
    }

    public boolean validateUser(dtoUser user) {
        if (!fetchUsers(null, user.getUsername(), null, null, null).isEmpty()) return false;
        return fetchUsers(null, null, null, null, user.getEmail()).isEmpty();
    }

    public List<dtoUser> fetchUsers(Long id, String username, String firstName, String lastName, String email) {
        return (id != null) ? this.userRepository.findAllById(id)
                : (username != null) ? this.userRepository.findAllByUsername(username)
                : (firstName != null) ? this.userRepository.findAllByFirstName(firstName)
                : (lastName != null) ? this.userRepository.findAllByLastName(lastName)
                : (email != null) ? this.userRepository.findAllByEmail(email)
                : this.userRepository.findAll();
    }

    public void updateUserInformation(Long id, String newUsername, String newPassword, String newFirstName, String newLastName, AccountType newAccountType, String newEmail) {
        dtoUser user = userRepository.findUserById(id);
        boolean userUpdated = false;

        if (newUsername != null) {
            user.setUsername(newUsername);
            userUpdated = true;
        }
        if (newPassword != null) {
            user.setPassword(newPassword);
            userUpdated = true;
        }
        if (newFirstName != null) {
            user.setFirstName(newFirstName);
            userUpdated = true;
        }
        if (newLastName != null) {
            user.setLastName(newLastName);
            userUpdated = true;
        }
        if (newAccountType != null) {
            user.setAccountType(newAccountType);
            userUpdated = true;
        }
        if (newEmail != null) {
            user.setEmail(newEmail);
            userUpdated = true;
        }
        if (userUpdated) {
            userRepository.save(user);
        }
    }

    public void deleteUserByUsername(Long id) {
        dtoUser user = userRepository.findUserById(id);
        userRepository.deleteById(user.getId());
    }

    public Optional<dtoUser> loadUserByUsernameAndPassword(String username, String password) {
        Optional<dtoUser> user = userRepository.findUserByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            return user;
        } else {
            throw new RuntimeException("User not found with Username: " + username);
        }
    }

    public LoginResponse validateLogin(LoginRequest request) {
         Optional<dtoUser> user = loadUserByUsernameAndPassword(request.getUsername(),request.getPassword());
         return new LoginResponse(user.get().getId(), user.get().getUsername());
    }
}
