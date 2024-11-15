package Trip.Mate.Trip.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pack_bookings") // Optional: Specify table name
public class PackBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "package_id", nullable = false)
    private int packageId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    private int userCount;

    @Temporal(TemporalType.DATE)
    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;
}
