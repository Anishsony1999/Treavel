package Trip.Mate.Trip.controller;

import Trip.Mate.Trip.dto.SearchDto;
import Trip.Mate.Trip.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchDto> search(
            @RequestParam(required = false) String place,
            @RequestParam(required = false) BigDecimal minRoomCost,
            @RequestParam(required = false) BigDecimal maxRoomCost,
            @RequestParam(required = false) BigDecimal maxFoodCost,
            @RequestParam(required = false, defaultValue = "0") int minDays,
            @RequestParam(required = false, defaultValue = "365") int maxDays,
            @RequestParam(required = false) Double maxBudget) {

        System.out.println("searching");
        SearchDto result = searchService.search(place, minRoomCost, maxRoomCost, maxFoodCost, minDays, maxDays, maxBudget);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
