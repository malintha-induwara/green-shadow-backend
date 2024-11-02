package lk.ijse.gdse68.greenshadow;

import lk.ijse.gdse68.greenshadow.entity.Vehicle;
import lk.ijse.gdse68.greenshadow.enums.VehicleStatus;
import lk.ijse.gdse68.greenshadow.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreenShadowApplication implements CommandLineRunner {

    @Autowired
    private VehicleRepository vehicleRepository;


    public static void main(String[] args) {
        SpringApplication.run(GreenShadowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlateNumber("WP-1234");
        vehicle.setVehicleCategory("Car");
        vehicle.setFuelType("Petrol");
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        vehicle.setRemarks("Good Condition");
        vehicle.setStaff(null);

        vehicleRepository.save(vehicle);


    }
}
