package sg.edu.nus.iss.project_backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDetails {

    private Integer serviceId;
    private String appointmentId;
    
}
