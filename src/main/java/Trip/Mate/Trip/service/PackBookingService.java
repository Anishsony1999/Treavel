package Trip.Mate.Trip.service;

import Trip.Mate.Trip.model.PackBooking;
import Trip.Mate.Trip.repo.PackBookingRepo;
import Trip.Mate.Trip.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PackBookingService {

    private final PackBookingRepo packBooking;
    private final UserRepo userRepo;

    public void savePack(int pId, int count , Date date,String email){
        PackBooking booking = new PackBooking();
        booking.setUserId(Math.toIntExact(userRepo.findByEmail(email).getId()));
        booking.setBookingDate(date);
        booking.setPackageId(pId);
        booking.setUserCount(count);

        packBooking.save(booking);
    }
}
