package sg.edu.nus.iss.project_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.mail.MessagingException;
import sg.edu.nus.iss.project_backend.models.Appointment;
import sg.edu.nus.iss.project_backend.models.Customer;
import sg.edu.nus.iss.project_backend.models.Invoice;
import sg.edu.nus.iss.project_backend.services.AppointmentService;
import sg.edu.nus.iss.project_backend.services.CustomerService;
import sg.edu.nus.iss.project_backend.services.InvoiceService;
import sg.edu.nus.iss.project_backend.utils.Email;

import static sg.edu.nus.iss.project_backend.utils.Constants.EMAIL;

@RestController
@RequestMapping(EMAIL)
public class EmailController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CustomerService customerService;

    @Autowired 
    private InvoiceService invoiceService;

    @Autowired 
    private Email email;

    @PostMapping("appointment") 
    public ResponseEntity<String> sendAppointment(@RequestBody String appointmentId) throws MessagingException{
        Appointment appointment = appointmentService.getById(appointmentId);
        Customer customer = customerService.getById(appointment.getCustomerId());

        String customerMail = customer.getEmail();
        String subject = "Appointment Reminder";
        String body = email.appointmentNotification(appointment, customer);
        Boolean success = email.sendEmail(customerMail, subject, body);

        if (success){
            return ResponseEntity.ok(
                Json.createObjectBuilder()
                .add("message", "success")
                .build().toString()
            );
        }

        return new ResponseEntity<String>(
            Json.createObjectBuilder()
                .add("message", "failure")
                .build().toString(), 
            HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("invoice") 
    public ResponseEntity<String> sendInvoice(@RequestBody String id) throws MessagingException{
        Invoice invoice = invoiceService.getById(id);
        Appointment appointment = appointmentService.getById(invoice.getAppointmentId());
        Customer customer = customerService.getById(appointment.getCustomerId());

        String customerMail = customer.getEmail();
        String subject = "Invoice";
        String body = email.invoiceNotification(invoice, customer);
        Boolean success = email.sendEmail(customerMail, subject, body);

        if (success){
            return ResponseEntity.ok(
                Json.createObjectBuilder()
                .add("message", "success")
                .build().toString()
            );
        }

        return new ResponseEntity<String>(
            Json.createObjectBuilder()
                .add("message", "failure")
                .build().toString(), 
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
