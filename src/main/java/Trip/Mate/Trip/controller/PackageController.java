package Trip.Mate.Trip.controller;

import Trip.Mate.Trip.dto.PackageDto;
import Trip.Mate.Trip.model.Package;
import Trip.Mate.Trip.service.PackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackService packService;

    // Add new package
    @PostMapping
    public ResponseEntity<String> addPackage(@ModelAttribute PackageDto packageDto) {
        try {
            packService.addPackImage(packageDto);
            return new ResponseEntity<>("Package added successfully", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all packages
    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {
        List<Package> packages = packService.allPack();
        return new ResponseEntity<>(packages, HttpStatus.OK);
    }

    // Get package by ID
    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable int id) {
        Optional<Package> pack = Optional.ofNullable(packService.packById(id));
        return pack.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Package>> searchPackages(@RequestParam(required = false) String place,@RequestParam(required = false) String name) {
        List<Package> filteredPackages = packService.searchPackages(place,name);
        return new ResponseEntity<>(filteredPackages, HttpStatus.OK);
    }

    // Update package by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePackage(@PathVariable int id, @ModelAttribute PackageDto packageDto) {
        try {
            packService.updatePackage(id, packageDto);
            return new ResponseEntity<>("Package updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Package not found", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete package by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePackage(@PathVariable int id) {
        try {
            packService.deleteById(id);
            return new ResponseEntity<>("Package deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Package not found", HttpStatus.NOT_FOUND);
        }
    }
}
