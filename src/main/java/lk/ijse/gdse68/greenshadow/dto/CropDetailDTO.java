package lk.ijse.gdse68.greenshadow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CropDetailDTO<T>{
    private String logCode;
    private LocalDate logDate;
    @NotBlank(message = "Log Detail is required")
    private String logDetail;
    private T observedImage;
    private List<String> fieldCodes;
    private List<String> cropCodes;
    private List<String> staffIds;
}

