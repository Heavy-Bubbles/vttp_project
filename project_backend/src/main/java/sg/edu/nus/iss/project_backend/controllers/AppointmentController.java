package sg.edu.nus.iss.project_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import sg.edu.nus.iss.project_backend.models.Appointment;
import sg.edu.nus.iss.project_backend.services.AppointmentService;

import static sg.edu.nus.iss.project_backend.utils.Constants.APPOINTMENT;
import static sg.edu.nus.iss.project_backend.utils.Constants.ALL;
import static sg.edu.nus.iss.project_backend.utils.Constants.INSERT;
import static sg.edu.nus.iss.project_backend.utils.Constants.SEARCH;
import static sg.edu.nus.iss.project_backend.utils.Constants.DELETE;

import java.util.List;

@RestController
@RequestMapping(APPOINTMENT)
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable String id) {
        Appointment appointment = appointmentService.getById(id);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping(ALL)
    public ResponseEntity<List<Appointment>> getAll() {
        List<Appointment> appointments = appointmentService.getAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping(SEARCH)
    public ResponseEntity<List<Appointment>> search(@RequestParam(required = true) Long date) {
        List<Appointment> appointments = appointmentService.getByDate(date);
        return ResponseEntity.ok(appointments);
    }

    @PostMapping(INSERT) 
    public ResponseEntity<String> insert(@RequestBody String payload){
        Boolean success = appointmentService.insert(payload);
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

    @PostMapping(DELETE) 
    public ResponseEntity<String> delete(@RequestBody String appointmentId){
        Boolean success = appointmentService.delete(appointmentId);
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
