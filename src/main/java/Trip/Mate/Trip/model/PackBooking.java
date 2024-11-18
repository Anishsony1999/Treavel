package Trip.Mate.Trip.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pack_bookings")
public class PackBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Foreign key to Package entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private Package pack;

    // Foreign key to User entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private double amount;
    private int userCount;

    @Temporal(TemporalType.DATE)
    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

}
