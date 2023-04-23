package org.deskify.service;

import org.deskify.model.api.request.CreateUserRequest;
import org.deskify.model.domain.AccountType;
import org.deskify.model.domain.User;
import org.deskify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAccountType(AccountType.USER);
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public List<User> fetchUsers(Long id, String username, String firstName,  String lastName,  String email) {
        if (id != null) {
            return this.userRepository.findAllById(id);
        } else if (username != null) {
            return this.userRepository.findAllByUsername(username);
        } else if (firstName != null) {
            return this.userRepository.findAllByFirstName(firstName);
        } else if (lastName != null) {
            return this.userRepository.findAllByLastName(lastName);
        } else if (email != null) {
            return this.userRepository.findAllByEmail(email);
        } else {
            return this.userRepository.findAll();
        }
    }

    public void updateUserInformation(Long id, String newUsername, String newPassword, String newFirstName, String newLastName, AccountType newAccountType, String newEmail) {
        User user = userRepository.findUserById(id);

        if (newUsername != null) {
            user.setUsername(newUsername);
        }
        if (newPassword != null) {
            user.setPassword(newPassword);
        }
        if (newFirstName != null) {
            user.setFirstName(newFirstName);
        }
        if (newLastName != null) {
            user.setLastName(newLastName);
        }
        if (newAccountType != null) {
            user.setAccountType(newAccountType);
        }
        if (newEmail != null) {
            user.setEmail(newEmail);
        }

        userRepository.save(user);
    }

    public void deleteUserByUsername(Long id) {
        User user = userRepository.findUserById(id);
        userRepository.deleteById(user.getId());
    }
}
