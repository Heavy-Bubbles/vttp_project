package sg.edu.nus.iss.project_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.project_backend.models.AppointmentDetails;
import sg.edu.nus.iss.project_backend.services.AppointmentDetailsService;

import static sg.edu.nus.iss.project_backend.utils.Constants.APPOINTMENT_DETAILS;

import java.util.List;;

@RestController
@RequestMapping(APPOINTMENT_DETAILS)
public class AppointmentDetailsController {
    
    @Autowired 
    private AppointmentDetailsService appointmentDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<List<AppointmentDetails>> getById(@PathVariable String id) {
        List<AppointmentDetails> apptdets = appointmentDetailsService.getByAppointmentId(id);
        return ResponseEntity.ok(apptdets);
    }
}
