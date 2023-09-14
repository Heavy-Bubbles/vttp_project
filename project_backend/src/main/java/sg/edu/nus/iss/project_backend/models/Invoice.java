package sg.edu.nus.iss.project_backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    private String id;
    private String appointmentId;
    private Double amountDue;
    private Long invoiceDate;
    private String url;
}
