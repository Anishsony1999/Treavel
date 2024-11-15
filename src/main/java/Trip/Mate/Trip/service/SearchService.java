package Trip.Mate.Trip.service;

import Trip.Mate.Trip.dto.SearchDto;
import Trip.Mate.Trip.model.Hotel;
import Trip.Mate.Trip.model.Package;
import Trip.Mate.Trip.repo.HotelRepo;
import Trip.Mate.Trip.repo.PackRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final HotelRepo hotelRepo;
    private final PackRepo packageRepo;

    public SearchDto search(String place, BigDecimal minRoomCost, BigDecimal maxRoomCost, BigDecimal maxFoodCost,
                            int minDays, int maxDays, Double maxBudget) {

        // Default values for optional parameters
        if (minRoomCost == null) minRoomCost = BigDecimal.ZERO;
        if (maxRoomCost == null) maxRoomCost = BigDecimal.valueOf(Double.MAX_VALUE);
        if (maxFoodCost == null) maxFoodCost = BigDecimal.valueOf(Double.MAX_VALUE);

        if (maxBudget == null) maxBudget = Double.MAX_VALUE;

        // Search hotels
        List<Hotel> hotels = hotelRepo.findByCityContainingIgnoreCaseOrHotelNameContainingIgnoreCaseAndRoomCostBetweenAndFoodCostLessThanEqual(
                place, place, minRoomCost, maxRoomCost, maxFoodCost
        );

        // Search packages
        List<Package> packages = packageRepo.findByCityContainingIgnoreCaseOrPackNameContainingIgnoreCaseAndDaysBetweenAndPricePerPersonLessThanEqual(
                place, place, minDays, maxDays, maxBudget
        );

        // Combine results
        SearchDto result = new SearchDto();
        result.setHotels(hotels);
        result.setPackages(packages);

        return result;
    }
}
