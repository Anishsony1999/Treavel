package Trip.Mate.Trip.controller;

import Trip.Mate.Trip.dto.HotelDto;
import Trip.Mate.Trip.model.Hotel;
import Trip.Mate.Trip.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    // Add new hotel
    @PostMapping
    public ResponseEntity<String> addHotel(@ModelAttribute HotelDto hotelDto) {
        try {
            hotelService.addHotel(hotelDto);
            return new ResponseEntity<>("Hotel added successfully", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all hotels
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    // Get hotel by ID
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable int id) {
        Optional<Hotel> hotel = Optional.ofNullable(hotelService.getHotelById(id));
        return hotel.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update hotel by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateHotel(@PathVariable int id, @ModelAttribute HotelDto hotelDto) {
        try {
            hotelService.updateHotel(id, hotelDto);
            return new ResponseEntity<>("Hotel updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Hotel not found", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete hotel by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable int id) {
        try {
            hotelService.deleteHotel(id);
            return new ResponseEntity<>("Hotel deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Hotel not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam(required = false) String place,
            @RequestParam(required = false) BigDecimal minRoomCost,
            @RequestParam(required = false) BigDecimal maxRoomCost,
            @RequestParam(required = false) BigDecimal maxFoodCost) {

        List<Hotel> filteredHotels = hotelService.searchHotels(place, minRoomCost, maxRoomCost, maxFoodCost);
        return new ResponseEntity<>(filteredHotels, HttpStatus.OK);
    }
}
