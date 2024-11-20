package Trip.Mate.Trip.repo;

import Trip.Mate.Trip.model.Memories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepo extends JpaRepository<Memories,Integer> {
    List<Memories> findByPackId(int id);
}
