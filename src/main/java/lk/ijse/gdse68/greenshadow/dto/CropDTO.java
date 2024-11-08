package lk.ijse.gdse68.greenshadow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CropDTO<T> implements Serializable {
    private String cropCode;
    @NotBlank(message = "Crop Common Name is required")
    private String cropCommonName;
    @NotBlank(message = "Crop Scientific Name is required")
    private String cropScientificName;
    private T image;
    @NotBlank(message = "Category is required")
    private String category;
    @NotBlank(message = "Crop Season is required")
    private String cropSeason;
    private String field;
}

