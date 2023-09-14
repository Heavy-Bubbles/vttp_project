package sg.edu.nus.iss.project_backend.services;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.project_backend.exceptions.ApiException;
import sg.edu.nus.iss.project_backend.models.Appointment;
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

        try{
            JsonObject json = Utility.toJsonObject(payload);

            Integer customerId = json.getInt("customer");
            
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
                Services service = servicesService.getById(s);
                Integer duration = service.getDurationInMinutes();
                Long mills = duration * 60000L;
                appointmentEnd += mills;
            }       
            appointment.setAppointmentEnd(appointmentEnd);        
            appointmentRepo.insert(appointment);
            
            for(Integer s : servicesList){
                appointmentDetailsService.insert(appointment.getId(), s);

            }
            return true;

        } catch (Exception ex){
            throw new ApiException();
        }
        
    }
    
    @Transactional
    public Boolean delete(String appointmentId){
        try{
            List<Invoice> invoices = invoiceService.getByAppointmentId(appointmentId);
            if(!invoices.isEmpty()){
                invoiceService.delete(appointmentId);
            }
            appointmentDetailsService.delete(appointmentId);
            appointmentRepo.delete(appointmentId);
            return true;
            
        } catch (Exception ex){
            throw new ApiException();
        }
        
    } 
    
}
