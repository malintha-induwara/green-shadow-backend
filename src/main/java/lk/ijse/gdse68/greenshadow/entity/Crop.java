package lk.ijse.gdse68.greenshadow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Crop {

    @Id
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    private String image;
    private String category;
    private String cropSeason;

    @ManyToOne
    @JoinColumn(name = "fieldCode", referencedColumnName = "fieldCode")
    private Field field;
}

