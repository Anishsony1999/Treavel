package Trip.Mate.Trip.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HotelDto {
    private String hotelName;
    private String city;
    private String state;
    private double foodCost;
    private double acRoomCost;
    private double roomCost;
    private MultipartFile image;
}
