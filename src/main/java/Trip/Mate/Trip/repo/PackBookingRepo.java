package Trip.Mate.Trip.repo;

import Trip.Mate.Trip.model.PackBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackBookingRepo extends JpaRepository<PackBooking,Integer> {
    List<PackBooking> findByUserId(int id);
}
