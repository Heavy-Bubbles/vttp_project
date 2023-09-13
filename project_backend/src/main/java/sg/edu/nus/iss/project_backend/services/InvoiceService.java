package sg.edu.nus.iss.project_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.project_backend.exceptions.ApiException;
import sg.edu.nus.iss.project_backend.models.Invoice;
import sg.edu.nus.iss.project_backend.repositories.InvoiceRepo;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    public List<Invoice> getAll(){
        return invoiceRepo.getAll();  
    }
    public List<Invoice> getByDate(Long date){
        return invoiceRepo.getByDate(date);
    }

    @Transactional
    public Boolean insert(String appointmentId){
        Invoice invoice = new Invoice();
        invoice.setId(0);
        invoice.setAppointmentId(appointmentId);
        invoice.setInvoiceDate(System.currentTimeMillis());
        Optional<Double> amount = Optional.of(invoiceRepo.getAmountDue(appointmentId));
        if (amount.isEmpty()){
            throw new ApiException();
        }
        invoice.setAmountDue(amount.get());
        // write logic to generate and save pdf
        invoice.setUrl("");
        return invoiceRepo.insert(invoice);
    }

    public List<Invoice> getByAppointmentId(String id){
        return invoiceRepo.getByAppointmentId(id);
    }

    public Boolean delete(String appointmentId){
        return invoiceRepo.delete(appointmentId);
    } 
    
}
