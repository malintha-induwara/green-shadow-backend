package lk.ijse.gdse68.greenshadow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldDTO<T>  implements Serializable {
    private String fieldCode;
    @NotBlank(message = "Field Name is required")
    private String fieldName;
    @NotNull(message = "Field Location is required")
    private Point fieldLocation;
    @Positive(message = "Extent size must be a positive number")
    private double extentSize;
    private T fieldImage1;
    private T fieldImage2;
    private List<String> crops;
    private List<String> staff;
}

