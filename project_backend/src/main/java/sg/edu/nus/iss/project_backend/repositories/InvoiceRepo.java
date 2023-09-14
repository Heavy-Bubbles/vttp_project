package sg.edu.nus.iss.project_backend.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.project_backend.models.Invoice;

@Repository
public class InvoiceRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL = "select * from invoice order by invoice_date DESC";
    private static final String INSERT = "insert into invoice (id, appointment_id, amount_due, invoice_date, url) values (? ,?, ?, ?, ?)";
    private static final String GET_AMOUNT_DUE = "select SUM(s.price) AS total_cost from appointment a join appointment_details ad on a.id = ad.appointment_id join services s on ad.service_id = s.id where a.id = ?";
    private static final String GET_BY_DATE = "select * from invoice where invoice_date between ? and ?";
    private static final String GET_BY_A_ID = "select * from invoice where appointment_id = ?";
    private static final String DELETE = "delete from invoice where appointment_id = ?";
    private static final String GET_BY_ID = "select * from invoice where id = ?";

    public List<Invoice> getAll(){
        return jdbcTemplate.query(GET_ALL, BeanPropertyRowMapper.newInstance(Invoice.class));   
    }

    public Boolean insert(Invoice invoice){
        int result = jdbcTemplate.update(INSERT, invoice.getId(), invoice.getAppointmentId(), 
        invoice.getAmountDue(), invoice.getInvoiceDate(), invoice.getUrl());
        
        return result > 0 ? true : false;
    }

    public Double getAmountDue(String appointmentId){
        return jdbcTemplate.queryForObject(GET_AMOUNT_DUE,Double.class, appointmentId);
    }

    public List<Invoice> getByDate(Long date){
        Long dayEnd = date + 86400000;
        return jdbcTemplate.query(GET_BY_DATE, BeanPropertyRowMapper.newInstance(Invoice.class), date, dayEnd);
    }

    public List<Invoice> getByAppointmentId(String id){
        return jdbcTemplate.query(GET_BY_A_ID, BeanPropertyRowMapper.newInstance(Invoice.class), id);
    }

    public Boolean delete(String appointmentId){
        int result = jdbcTemplate.update(DELETE, appointmentId);
        
        return result > 0 ? true : false;
    } 

    public Invoice getById(String id){
        return jdbcTemplate.queryForObject(GET_BY_ID, BeanPropertyRowMapper.newInstance(Invoice.class), id);
    }
}
