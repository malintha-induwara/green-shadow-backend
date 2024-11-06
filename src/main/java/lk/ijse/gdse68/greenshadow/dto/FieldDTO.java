package lk.ijse.gdse68.greenshadow.dto;

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
    private String fieldName;
    private Point fieldLocation;
    private double extentSize;
    private T fieldImage1;
    private T fieldImage2;
    private List<String> crops;
    private List<String> staff;
}

