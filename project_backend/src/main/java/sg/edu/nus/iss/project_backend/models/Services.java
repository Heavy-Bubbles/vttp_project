package sg.edu.nus.iss.project_backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Services {

    private Integer id;
    private String name;
    private Integer durationInMinutes;
    private Double price;
}
