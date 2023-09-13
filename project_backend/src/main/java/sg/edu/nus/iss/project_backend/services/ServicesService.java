package sg.edu.nus.iss.project_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.project_backend.models.Services;
import sg.edu.nus.iss.project_backend.repositories.ServiceRepo;

@Service
public class ServicesService {

    @Autowired
    private ServiceRepo serviceRepo;
    
    public Services getById(Integer id){
        return serviceRepo.getById(id);
    }

    public List<Services> getAll(){
        return serviceRepo.getAll();
    }

    public Boolean insert(Services service){
        return serviceRepo.insert(service);
    }

    public Boolean update(Services service){
        return serviceRepo.update(service);
    }
    
    public List<Services> search(String name){
        return serviceRepo.search(name);
    }
}
