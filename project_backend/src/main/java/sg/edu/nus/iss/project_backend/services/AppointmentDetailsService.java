package sg.edu.nus.iss.project_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.project_backend.models.AppointmentDetails;
import sg.edu.nus.iss.project_backend.repositories.AppointmentDetailsRepo;

@Service
public class AppointmentDetailsService {

    @Autowired
    private AppointmentDetailsRepo appointmentDetailsRepo;
    
    public List<AppointmentDetails> getByAppointmentId(String id){
        return appointmentDetailsRepo.getByAppointmentId(id);
    }

    public Boolean insert(String appointmentId, Integer serviceId){
        return appointmentDetailsRepo.insert(appointmentId, serviceId);
    }

    public Boolean delete(String appointmentId){
        return appointmentDetailsRepo.delete(appointmentId);
    } 

}
