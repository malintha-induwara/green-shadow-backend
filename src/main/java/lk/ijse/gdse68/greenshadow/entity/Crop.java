package lk.ijse.gdse68.greenshadow.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lk.ijse.gdse68.greenshadow.annotation.CustomGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Crop {

    @Id
    @CustomGenerator(prefix ="CROP-" )
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

