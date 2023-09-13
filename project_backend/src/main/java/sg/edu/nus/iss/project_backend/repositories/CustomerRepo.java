package sg.edu.nus.iss.project_backend.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.project_backend.models.Customer;

@Repository
public class CustomerRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final String GET_BY_ID = "select * from customer where id = ?";
    private static final String GET_ALL = "select * from customer";
    private static final String INSERT = "insert into customer (name, gender, phone, email) values (?, ?, ?, ?)";
    private static final String UPDATE = "update customer set name = ?, gender = ?, phone = ?, email = ? where id = ?";
    private static final String SEARCH = "select * from customer where name like ?";
    
    public Customer getById(Integer id){
        return jdbcTemplate.queryForObject(GET_BY_ID, BeanPropertyRowMapper.newInstance(Customer.class), id);
    }
    
    public List<Customer> getAll(){
        return jdbcTemplate.query(GET_ALL, BeanPropertyRowMapper.newInstance(Customer.class));
    }
    
    public Boolean insert(Customer customer){
        int result = jdbcTemplate.update(INSERT, customer.getName(),
        customer.getGender(), customer.getPhone(), customer.getEmail());
        
        return result > 0 ? true : false;
    }
    
    public Boolean update(Customer customer){
        int result = jdbcTemplate.update(UPDATE, customer.getName(),
        customer.getGender(), customer.getPhone(), customer.getEmail(), customer.getId());
        
        return result > 0 ? true : false;
    }
    
    public List<Customer> search(String name){
        String query = "%" + name + "%";
        return jdbcTemplate.query(SEARCH, BeanPropertyRowMapper.newInstance(Customer.class), query);
    }
}
