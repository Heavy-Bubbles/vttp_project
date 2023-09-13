package sg.edu.nus.iss.project_backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    private String id;
    private Long appointmentStart;
    private Long appointmentEnd;
    private Integer customerId;
}
