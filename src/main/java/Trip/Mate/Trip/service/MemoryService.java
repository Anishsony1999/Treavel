package Trip.Mate.Trip.service;

import Trip.Mate.Trip.model.*;
import Trip.Mate.Trip.model.Package;
import Trip.Mate.Trip.repo.MemoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoryService {

    private final MemoryRepo memoryRepo;

    @Value("${file.memory-img-upload-dir}")
    private String rootLocation;

    public void save(MultipartFile[] images,
                     String title,
                     String description,
                     User user,
                     Package pack,
                     PackBooking packBooking) throws IOException {

        // Construct directory paths
        String relativePath = "memoryImages" + File.separator + user.getFirstName() + File.separator + pack.getPackName();
        Path absolutePath = Path.of(rootLocation, relativePath);

        try {
            // Create directories if they don't exist
            if (!Files.exists(absolutePath)) {
                Files.createDirectories(absolutePath);
            }

            // Save each image and its details in the database
            for (MultipartFile image : images) {
                String filename = image.getOriginalFilename();
                Path destinationPath = absolutePath.resolve(filename);

                // Transfer file to the destination
                image.transferTo(destinationPath);

                // Save memory details in the database
                Memories memory = new Memories();
                memory.setTitle(title);
                memory.setDescription(description);

                memory.setImgUrl("/"+relativePath.replace(File.separator, "/") + "/" + filename);

                memory.setUser(user);
                memory.setPack(pack);
                memory.setBookedPack(packBooking);

                memoryRepo.save(memory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Memories> getMemoriesByPackId(int id) {
        return memoryRepo.findByPackId(id);
    }
}
