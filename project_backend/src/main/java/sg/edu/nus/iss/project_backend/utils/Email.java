package sg.edu.nus.iss.project_backend.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import sg.edu.nus.iss.project_backend.models.Appointment;
import sg.edu.nus.iss.project_backend.models.Customer;
import sg.edu.nus.iss.project_backend.models.Invoice;


@Service
public class Email {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public Boolean sendEmail(String customerMail, String subject, String body) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(customerMail);
        message.setText(body);
        message.setSubject(subject);
        this.mailSender.send(message);
        return true;
    }

    public String appointmentNotification(Appointment appointment, Customer customer){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        long timestamp = appointment.getAppointmentStart();
        Date dateStart = new Date(timestamp);
        String start = dateFormat.format(dateStart);
        long timestamp2 = appointment.getAppointmentEnd();
        Date dateEnd = new Date(timestamp2);
        String end = dateFormat.format(dateEnd);
        return String.format("""
                Dear %s,

                You have an upcoming appointment from %s to %s.

                Regards,
                Sensuous Beauty
                """, customer.getName(), start, end);
    }

    public String invoiceNotification(Invoice invoice, Customer customer){
        String url = invoice.getUrl();
                return String.format("""
                Dear %s,

                We hope you enjoyed our services. 
                Below is the link to the invoice:
                %s

                Regards,
                Sensuous Beauty
                """, customer.getName(), url);
    }
}