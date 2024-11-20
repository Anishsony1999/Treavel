package Trip.Mate.Trip.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Memories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PackBooking bookedPack;

    @ManyToOne(fetch = FetchType.LAZY)
    private Package pack;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String imgUrl;

    private String description;

    private String title;

}
