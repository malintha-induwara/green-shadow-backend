package lk.ijse.gdse68.greenshadow.dto;

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
    private String cropCommonName;
    private String cropScientificName;
    private T image;
    private String category;
    private String cropSeason;
    private String field;
}

