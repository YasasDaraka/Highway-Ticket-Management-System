package lk.ijse.gdse.userserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    private String nic;
    private String name;
    private String contactNo;

}
