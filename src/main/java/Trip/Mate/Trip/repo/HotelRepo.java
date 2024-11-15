package Trip.Mate.Trip.repo;

import Trip.Mate.Trip.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<Hotel,Integer> {
}
