package Trip.Mate.Trip.repo;

import Trip.Mate.Trip.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackRepo extends JpaRepository<Package,Integer> {
    
}
