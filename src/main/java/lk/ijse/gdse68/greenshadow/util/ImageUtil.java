package lk.ijse.gdse68.greenshadow.util;

import lk.ijse.gdse68.greenshadow.enums.ImageType;
import lk.ijse.gdse68.greenshadow.exception.InvalidImageTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class ImageUtil {

    public static Path IMAGE_DIRECTORY = Paths.get(System.getProperty("user.home"), "Desktop", "LocalS3Bucket").toAbsolutePath().normalize();


    public ImageUtil() {
        if (!Files.exists(IMAGE_DIRECTORY)) {
            try {
                Files.createDirectories(IMAGE_DIRECTORY);
                log.info("Directory Created");
            } catch (IOException e) {
                log.error("Failed to Create directory {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public String getImage(String imageId) {
        try {
            Optional<Path> resource = searchImage(imageId);
            if (resource.isPresent()) {
                return Base64.getEncoder().encodeToString(Files.readAllBytes(resource.get()));
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveImage(ImageType imageType, MultipartFile file) {
        // Check if the file is empty
        if (file.isEmpty()) {
            return null;
        }

        //Check whether the file types are valid
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith("jpg") &&
                !Objects.requireNonNull(file.getOriginalFilename()).endsWith("png") &&
                !Objects.requireNonNull(file.getOriginalFilename()).endsWith("jpeg")
        ) {
            throw new InvalidImageTypeException("Invalid file type. Only JPG and PNG files are allowed.");
        }
        //Random UUID
        String fileName = imageType.toString() +"-" + UUID.randomUUID();
        try {
            Files.copy(file.getInputStream(), IMAGE_DIRECTORY.resolve(fileName + "." + Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1]));
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String updateImage(String imageId,ImageType imageType ,MultipartFile file) {
        try {
            Optional<Path> resource = searchImage(imageId);
            if (resource.isPresent()) {
                Files.delete(resource.get());
            }
            return saveImage(imageType,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Optional<Path> searchImage(String imageId) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(IMAGE_DIRECTORY, imageId + ".*")) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    return Optional.of(entry);
                }
            }
        } catch (IOException e) {
            log.error("Error searching for file: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void deleteImage(String itemId) {
        try {
            Optional<Path> resource = searchImage(itemId);
            if (resource.isPresent()) {
                Files.delete(resource.get());
            }
        } catch (IOException e) {
            log.error("Error Deleting file: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

