package Trip.Mate.Trip.repo;

import Trip.Mate.Trip.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackRepo extends JpaRepository<Package,Integer> {
    List<Package> findByCityContainingIgnoreCaseOrPackName(String city, String packName);

}
