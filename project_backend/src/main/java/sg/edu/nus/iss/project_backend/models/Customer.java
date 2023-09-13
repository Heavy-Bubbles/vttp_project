package sg.edu.nus.iss.project_backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    
    private Integer id;
    private String name;
    private String gender;
    private String phone;
    private String email;
}
