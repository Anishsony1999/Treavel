package Trip.Mate.Trip.service;

import Trip.Mate.Trip.model.User;
import Trip.Mate.Trip.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public List<User> allDetails() {
        return userRepo.findAll();
    }

    public void userRegister(User user) {
        userRepo.save(user);
    }

    public User verifyUser(String email, String pass) {
        return userRepo.findByEmailAndPassword(email, pass);
    }

    public Optional<User> getUserById(int id) {
        return userRepo.findById(id);
    }

    public User updateUser(int id, User updatedUser) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());


        return userRepo.save(existingUser);
    }

    public void deleteUser(int id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        userRepo.deleteById(id);
    }
}
