package lk.ijse.gdse68.greenshadow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldDTO<T> {
    private String fieldCode;
    private String fieldName;
    private Point fieldLocation;
    private double extentSize;
    private T fieldImage1;
    private T fieldImage2;
}

