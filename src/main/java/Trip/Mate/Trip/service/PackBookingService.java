package Trip.Mate.Trip.service;

import Trip.Mate.Trip.model.Hotel;
import Trip.Mate.Trip.model.PackBooking;
import Trip.Mate.Trip.repo.PackBookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class PackBookingService {

    private final PackBookingRepo packBooking;


    public void savePack(PackBooking packBookings) {
        packBooking.save(packBookings);
    }

    public List<PackBooking> allBooking(){
        return packBooking.findAll();
    }

    public List<PackBooking> bookingByUserId(int id){
        return packBooking.findByUserId(id);
    }

    public PackBooking bookingById(int id){
        return packBooking.findById(id).orElseThrow( ()-> new RuntimeException("Booking not found"));
    }

}
