package Trip.Mate.Trip.service;

import Trip.Mate.Trip.dto.HotelDto;
import Trip.Mate.Trip.model.Hotel;
import Trip.Mate.Trip.repo.HotelRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final HotelRepo hotelRepo;

    public void addHotel(HotelDto hotelDto) throws IOException {
        MultipartFile file = hotelDto.getImage();
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + File.separator + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelDto.getHotelName());
        hotel.setCity(hotelDto.getCity());
        hotel.setState(hotelDto.getState());
        hotel.setFoodCost(BigDecimal.valueOf(hotelDto.getFoodCost()));
        hotel.setAcRoomCost(BigDecimal.valueOf(hotelDto.getAcRoomCost()));
        hotel.setRoomCost(BigDecimal.valueOf(hotelDto.getRoomCost()));
        hotel.setImgUrl("/hotelImages/" + fileName);

        hotelRepo.save(hotel);
    }

    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    public Hotel getHotelById(int id) {
        return hotelRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel with ID " + id + " not found"));
    }

    public Hotel updateHotel(int id, HotelDto hotelDto) throws IOException {
        Hotel existingHotel = hotelRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel with ID " + id + " not found"));

        existingHotel.setHotelName(hotelDto.getHotelName());
        existingHotel.setCity(hotelDto.getCity());
        existingHotel.setState(hotelDto.getState());
        existingHotel.setFoodCost(BigDecimal.valueOf(hotelDto.getFoodCost()));
        existingHotel.setAcRoomCost(BigDecimal.valueOf(hotelDto.getAcRoomCost()));
        existingHotel.setRoomCost(BigDecimal.valueOf(hotelDto.getRoomCost()));

        if (hotelDto.getImage() != null && !hotelDto.getImage().isEmpty()) {
            MultipartFile file = hotelDto.getImage();
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + File.separator + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            existingHotel.setImgUrl("/hotelImages/" + fileName);
        }

        return hotelRepo.save(existingHotel);
    }

    public void deleteHotel(int id) {
        if (!hotelRepo.existsById(id)) {
            throw new RuntimeException("Hotel with ID " + id + " not found");
        }
        hotelRepo.deleteById(id);
    }

    public List<Hotel> searchHotels(String place, BigDecimal minRoomCost, BigDecimal maxRoomCost, BigDecimal maxFoodCost) {
        if (minRoomCost == null) minRoomCost = BigDecimal.ZERO;
        if (maxRoomCost == null) maxRoomCost = BigDecimal.valueOf(Double.MAX_VALUE);
        if (maxFoodCost == null) maxFoodCost = BigDecimal.valueOf(Double.MAX_VALUE);

        return hotelRepo.findByCityContainingIgnoreCaseOrHotelNameContainingIgnoreCaseAndRoomCostBetweenAndFoodCostLessThanEqual(
                place, place, minRoomCost, maxRoomCost, maxFoodCost
        );
    }
}
