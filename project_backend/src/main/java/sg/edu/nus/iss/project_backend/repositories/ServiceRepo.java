package sg.edu.nus.iss.project_backend.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.project_backend.models.Services;

@Repository
public class ServiceRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final String GET_BY_ID = "select * from services where id = ?";
    private static final String GET_ALL = "select * from services";
    private static final String INSERT = "insert into services (name, price, duration_in_minutes) values (?, ?, ?)";
    private static final String UPDATE = "update services set name = ?, price = ?, duration_in_minutes = ? where id = ?";
    private static final String SEARCH = "select * from services where name like ?";
    
    public Services getById(Integer id){
        return jdbcTemplate.queryForObject(GET_BY_ID, BeanPropertyRowMapper.newInstance(Services.class), id);
    }
    
    public List<Services> getAll(){
        return jdbcTemplate.query(GET_ALL, BeanPropertyRowMapper.newInstance(Services.class));
    }
    
    public Boolean insert(Services service){
        int result = jdbcTemplate.update(INSERT, service.getName(), 
        service.getPrice(), service.getDurationInMinutes());
        
        return result > 0 ? true : false;
    }
    
    public Boolean update(Services service){
        int result = jdbcTemplate.update(UPDATE, service.getName(),
        service.getPrice(), service.getDurationInMinutes(), service.getId());
        
        return result > 0 ? true : false;
    }
    
    public List<Services> search(String name){
        String query = "%" + name + "%";
        return jdbcTemplate.query(SEARCH, BeanPropertyRowMapper.newInstance(Services.class), query);
    }
}
