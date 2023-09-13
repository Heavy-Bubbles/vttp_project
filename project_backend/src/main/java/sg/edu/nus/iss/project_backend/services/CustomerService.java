package sg.edu.nus.iss.project_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.project_backend.models.Customer;
import sg.edu.nus.iss.project_backend.repositories.CustomerRepo;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;
    
    public Customer getById(Integer id){
        return customerRepo.getById(id);
    }

    public List<Customer> getAll(){
        return customerRepo.getAll();
    }

    public Boolean insert(Customer customer){
        return customerRepo.insert(customer);
    }

    public Boolean update(Customer customer){
        return customerRepo.update(customer);
    }

    public List<Customer> search(String name){
        return customerRepo.search(name);
    }
    
}
