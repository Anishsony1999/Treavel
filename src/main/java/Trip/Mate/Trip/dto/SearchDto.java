package Trip.Mate.Trip.dto;

import Trip.Mate.Trip.model.Hotel;
import Trip.Mate.Trip.model.Package;
import lombok.Data;

import java.util.List;

@Data
public class SearchDto {

    private List<Hotel>  hotels;
    private List<Package> packages;
}
