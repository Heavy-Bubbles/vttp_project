package sg.edu.nus.iss.project_backend.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.project_backend.exceptions.ApiException;
import sg.edu.nus.iss.project_backend.models.Appointment;
import sg.edu.nus.iss.project_backend.models.Customer;
import sg.edu.nus.iss.project_backend.models.Invoice;
import sg.edu.nus.iss.project_backend.models.Services;
import sg.edu.nus.iss.project_backend.repositories.AppointmentRepo;
import sg.edu.nus.iss.project_backend.utils.Utility;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepo appointmentRepo;
    
    @Autowired
    private AppointmentDetailsService appointmentDetailsService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired 
    private ServicesService servicesService;
    
    @Autowired
    private InvoiceService invoiceService;
    
    public Appointment getById(String id){
        return appointmentRepo.getById(id);
    }
    
    public List<Appointment> getAll(){
        return appointmentRepo.getAll();
    }
    
    public List<Appointment> getByDate(Long date){
        return appointmentRepo.getByDate(date);
    }
    
    @Transactional
    public Boolean insert(String payload){
        JsonObject json = Utility.toJsonObject(payload);
        
        Integer customerId = json.getInt("customer");
        Optional<Customer> customer = Optional.of(customerService.getById(customerId));
        if(customer.isEmpty()){
            throw new ApiException();
        }
        
        Appointment appointment = new Appointment();
        appointment.setId(UUID.randomUUID().toString().substring(0, 8));
        appointment.setAppointmentStart(json.getJsonNumber("appointmentStart").longValue());
        appointment.setCustomerId(customerId);
        
        List<Integer> servicesList = new LinkedList<>();
        JsonArray array = json.getJsonArray("services");
        for(int i = 0; i < array.size(); i++){
            servicesList.add(array.getInt(i));
        }
        Long appointmentEnd = appointment.getAppointmentStart();
        for(Integer s: servicesList){
            Optional<Services> service = Optional.of(servicesService.getById(s));
            if(service.isEmpty()){
                throw new ApiException();
            }
            Services svc = service.get();
            Integer duration = svc.getDurationInMinutes();
            Long mills = duration * 60000L;
            appointmentEnd += mills;
        }       
        appointment.setAppointmentEnd(appointmentEnd);        
        Boolean success = appointmentRepo.insert(appointment);
        if(!success){
            throw new ApiException();
        }
        
        for(Integer s : servicesList){
            Boolean ok = appointmentDetailsService.insert(appointment.getId(), s);
            if (!ok){
                throw new ApiException();
            }
        }
        return true;
    }
    
    @Transactional
    public Boolean delete(String appointmentId){
        
        List<Invoice> invoices = invoiceService.getByAppointmentId(appointmentId);
        
        if (!invoices.isEmpty()){       
            Boolean deleted = invoiceService.delete(appointmentId);
            if(!deleted){
                throw new ApiException();
            }  
        }

        Boolean deleted = appointmentDetailsService.delete(appointmentId);
        if(!deleted){
            throw new ApiException();
        }

        Boolean ok = appointmentRepo.delete(appointmentId);
        if(!ok){
            throw new ApiException();
        }
        
        return true;
    } 
    
}
