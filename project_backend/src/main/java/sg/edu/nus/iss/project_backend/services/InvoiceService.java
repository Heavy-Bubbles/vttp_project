package sg.edu.nus.iss.project_backend.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.project_backend.exceptions.ApiException;
import sg.edu.nus.iss.project_backend.models.AppointmentDetails;
import sg.edu.nus.iss.project_backend.models.Invoice;
import sg.edu.nus.iss.project_backend.models.Services;
import sg.edu.nus.iss.project_backend.repositories.InvoiceRepo;
import sg.edu.nus.iss.project_backend.repositories.PDFRepo;
import sg.edu.nus.iss.project_backend.utils.PDFGenerator;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired 
    private AppointmentDetailsService appointmentDetailsService;

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private PDFGenerator pdfGenerator;

    @Autowired
    private PDFRepo pdfRepo;

    public List<Invoice> getAll(){
        return invoiceRepo.getAll();  
    }
    public List<Invoice> getByDate(Long date){
        return invoiceRepo.getByDate(date);
    }

    @Transactional
    public Boolean insert(String appointmentId) throws IOException{
        Invoice invoice = new Invoice();
        invoice.setId(UUID.randomUUID().toString().substring(0, 8));
        invoice.setAppointmentId(appointmentId);
        invoice.setInvoiceDate(System.currentTimeMillis());
        Optional<Double> amount = Optional.of(invoiceRepo.getAmountDue(appointmentId));
        if (amount.isEmpty()){
            throw new ApiException();
        }
        invoice.setAmountDue(amount.get());
        List<Services> services = new LinkedList<>();
        List<AppointmentDetails> apptDets = appointmentDetailsService.getByAppointmentId(appointmentId);
        for (AppointmentDetails a : apptDets){
            Integer serviceId = a.getServiceId();
            Services service = servicesService.getById(serviceId);
            services.add(service);
        }
        
        InputStream is = pdfGenerator.generatePDF(invoice, services);
        String url = pdfRepo.uploadPdf(is);
        invoice.setUrl(url);
        return invoiceRepo.insert(invoice);
    }

    public List<Invoice> getByAppointmentId(String id){
        return invoiceRepo.getByAppointmentId(id);
    }

    public Boolean delete(String appointmentId){
        return invoiceRepo.delete(appointmentId);
    } 

    public Invoice getById(String id){
        return invoiceRepo.getById(id);
    }
}
