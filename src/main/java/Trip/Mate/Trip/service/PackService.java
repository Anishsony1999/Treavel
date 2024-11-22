package Trip.Mate.Trip.service;


import Trip.Mate.Trip.dto.PackageDto;
import Trip.Mate.Trip.model.Package;
import Trip.Mate.Trip.repo.PackRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PackService {

    @Value("${file.img-upload-dir}")
    private String uploadDir;

    @Value("${currency.api.url}")
    private String url;

    @Value("${currency.api.key}")
    private String key;

    private final PackRepo packRepo;

    public void addPackImage(PackageDto packageDto) throws IOException {
        MultipartFile file = packageDto.getImage();
        String fileName =  file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + File.separator + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        Package pack = new Package();
        pack.setPackName(packageDto.getPackName());
        pack.setCity(packageDto.getCity());
        pack.setState(packageDto.getState());
        pack.setCountry(packageDto.getCountry());
        pack.setDays(packageDto.getDays());
        pack.setDescription(packageDto.getDescription());
        pack.setNight(packageDto.getNight());
        pack.setPricePerPerson(BigDecimal.valueOf(packageDto.getPricePerPerson()));
        pack.setImageUrl("/packageImages/" + fileName);

        packRepo.save(pack);
    }

    public List<Package> allPack(){
        return packRepo.findAll();
    }

    public Package packById(int id){
        return packRepo.findById(id).orElseThrow(()-> new RuntimeException("Pack Not Fount"));
    }

    @Transactional
    public void updatePackage(int id, PackageDto packageDto) throws IOException {

        Package existingPackage = packRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Package with ID " + id + " not found"));

        existingPackage.setPackName(packageDto.getPackName());
        existingPackage.setCity(packageDto.getCity());
        existingPackage.setState(packageDto.getState());
        existingPackage.setDays(packageDto.getDays());
        existingPackage.setNight(packageDto.getNight());
        existingPackage.setDescription(packageDto.getDescription());
        existingPackage.setPricePerPerson(BigDecimal.valueOf(packageDto.getPricePerPerson()));

        if (packageDto.getImage() != null && !packageDto.getImage().isEmpty()) {
            MultipartFile file = packageDto.getImage();
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + File.separator + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            existingPackage.setImageUrl("/packageImages/" + fileName);
        }

        packRepo.save(existingPackage);
    }

    public void deleteById(int id){
        packRepo.deleteById(id);
    }

    public List<Package> searchPackages(String place,Integer date) {
        return packRepo.findByPackNameContainingIgnoreCaseAndDaysGreaterThanEqual(place,date);
    }

    public BigDecimal getCurrency(String country,BigDecimal amount) throws IOException {

        // Want to replace the Country. now ,I just give it a static value = 'USD'
        country = "USD";
        String apiUrl = url + key + "/latest/INR";
        RestTemplate restTemplate = new RestTemplate();
        try {

            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

            if (response != null && response.get("conversion_rates") != null) {

                Map<String, Double> rates = (Map<String, Double>) response.get("conversion_rates");

                Double rate = rates.get(country.toUpperCase());

                if (rate != null) {
                    return amount.multiply(BigDecimal.valueOf(rate));
                } else {
                    throw new IllegalArgumentException("Currency conversion rate not found for: " + country);
                }
            } else {
                throw new RuntimeException("Invalid response from exchange rate API");
            }
        } catch (Exception ex) {
            // Handle and log exceptions (optional: replace with a logger)
            throw new RuntimeException("Error fetching currency conversion rates: " + ex.getMessage(), ex);
        }
    }
}
