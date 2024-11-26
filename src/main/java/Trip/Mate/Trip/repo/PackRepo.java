package Trip.Mate.Trip.repo;

import Trip.Mate.Trip.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackRepo extends JpaRepository<Package,Long> {
    @Query("SELECT p FROM Package p " +
            "WHERE (LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%')) " +
            "       OR LOWER(p.packName) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND p.days BETWEEN :start AND :end")
    List<Package> findByCityOrPackNameAndDaysInRange(
            @Param("city") String city,
            @Param("name") String name,
            @Param("start") int start,
            @Param("end") int end);

    List<Package> findTop3ByOrderByIdAsc();

}
