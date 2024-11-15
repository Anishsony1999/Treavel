package Trip.Mate.Trip.repo;

import Trip.Mate.Trip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmailAndPassword(String email,String pass);
}
