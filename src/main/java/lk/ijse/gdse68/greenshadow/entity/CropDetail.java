package lk.ijse.gdse68.greenshadow.entity;

import jakarta.persistence.*;
import lk.ijse.gdse68.greenshadow.util.CustomGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CropDetail {

    @Id
    @CustomGenerator(prefix ="CROP-DETAIL-" )
    private String logCode;

    @CreationTimestamp
    private LocalDate logDate;
    private String logDetail;
    private String observedImage;


    @ManyToMany
    @JoinTable(
            name = "log_field",
            joinColumns = @JoinColumn(name = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "fieldCode")
    )
    private List<Field> fields;

    @ManyToMany
    @JoinTable(
            name = "log_crop",
            joinColumns = @JoinColumn(name = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "cropCode")
    )
    private List<Crop> crops;


    @ManyToMany
    @JoinTable(
            name = "log_staff",
            joinColumns = @JoinColumn(name = "logCode"),
            inverseJoinColumns = @JoinColumn(name = "staffId")
    )
    private List<Staff> staff;

}

