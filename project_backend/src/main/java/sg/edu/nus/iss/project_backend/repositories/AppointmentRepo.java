package sg.edu.nus.iss.project_backend.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.project_backend.models.Appointment;

@Repository
public class AppointmentRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_BY_ID = "select * from appointment where id = ?";
    private static final String GET_ALL = "select * from appointment order by appointment_start DESC";
    private static final String INSERT = "insert into appointment (id, appointment_start, appointment_end, customer_id) values (?, ?, ?, ?)";
    private static final String GET_BY_DATE = "select * from appointment where appointment_start between ? and ?";
    private static final String DELETE = "delete from appointment where id = ?";
    
    public Appointment getById(String id){
        return jdbcTemplate.queryForObject(GET_BY_ID, BeanPropertyRowMapper.newInstance(Appointment.class), id);
    }
    
    public List<Appointment> getAll(){
        return jdbcTemplate.query(GET_ALL, BeanPropertyRowMapper.newInstance(Appointment.class));
    }
    
    public Boolean insert(Appointment appointment){
        int result = jdbcTemplate.update(INSERT, appointment.getId(),
            appointment.getAppointmentStart(), appointment.getAppointmentEnd(), appointment.getCustomerId());
        
        return result > 0 ? true : false;
    }

    public List<Appointment> getByDate(Long date){
        Long dayEnd = date + 86400000;
        return jdbcTemplate.query(GET_BY_DATE, BeanPropertyRowMapper.newInstance(Appointment.class), date, dayEnd);
    }

    public Boolean delete(String appointmentId){
        int result = jdbcTemplate.update(DELETE, appointmentId);
        
        return result > 0 ? true : false;
    }
}
