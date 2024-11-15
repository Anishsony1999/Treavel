package Trip.Mate.Trip.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class PackageDto {
    private String packName;
    private String description;
    private String city;
    private String state;
    private int days;
    private int night;
    private double pricePerPerson;
    private MultipartFile image;
}
