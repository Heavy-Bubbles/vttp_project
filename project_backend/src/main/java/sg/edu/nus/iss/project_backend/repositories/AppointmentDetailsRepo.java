package sg.edu.nus.iss.project_backend.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.project_backend.models.AppointmentDetails;


@Repository
public class AppointmentDetailsRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT = "insert into appointment_details (service_id, appointment_id) values (?, ?)";
    private static final String GET_BY_A_ID = "select * from appointment_details where appointment_id = ?";
    private static final String DELETE = "delete from appointment_details where appointment_id = ?";

    public List<AppointmentDetails> getByAppointmentId(String id){
        return jdbcTemplate.query(GET_BY_A_ID, BeanPropertyRowMapper.newInstance(AppointmentDetails.class), id);
    }

    public Boolean insert(String appointmentId, Integer serviceId){
        int result = jdbcTemplate.update(INSERT, serviceId,
        appointmentId);
        
        return result > 0 ? true : false;
    }

    public Boolean delete(String appointmentId){
        int result = jdbcTemplate.update(DELETE, appointmentId);
        
        return result > 0 ? true : false;
    } 
}
