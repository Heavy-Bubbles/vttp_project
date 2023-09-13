package sg.edu.nus.iss.project_backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import sg.edu.nus.iss.project_backend.models.Services;
import sg.edu.nus.iss.project_backend.services.ServicesService;
import sg.edu.nus.iss.project_backend.utils.Utility;

import static sg.edu.nus.iss.project_backend.utils.Constants.ALL;
import static sg.edu.nus.iss.project_backend.utils.Constants.SERVICES;
import static sg.edu.nus.iss.project_backend.utils.Constants.INSERT;
import static sg.edu.nus.iss.project_backend.utils.Constants.UPDATE;
import static sg.edu.nus.iss.project_backend.utils.Constants.SEARCH;

@RestController
@RequestMapping(SERVICES)
public class ServicesController {

    @Autowired
    private ServicesService servService;

    @GetMapping("/{id}")
    public ResponseEntity<Services> getById(@PathVariable Integer id) {
        Services service = servService.getById(id);
        return ResponseEntity.ok(service);
    }

    @GetMapping(ALL)
    public ResponseEntity<List<Services>> getAll() {
        List<Services> services = servService.getAll();
        return ResponseEntity.ok(services);
    }

    @GetMapping(SEARCH)
        public ResponseEntity<List<Services>> search(@RequestParam(required = true) String name) {
        List<Services> services = servService.search(name);
        return ResponseEntity.ok(services);
    }

    @PostMapping(INSERT) 
    public ResponseEntity<String> insert(@RequestBody String payload){
        Services service = Utility.toService(Utility.toJsonObject(payload));
        System.out.printf(">>>> service: %s\n", service);
        Boolean success = servService.insert(service);

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

    @PutMapping(UPDATE)
    public ResponseEntity<String> update(@RequestBody String payload){
        Services service = Utility.toService(Utility.toJsonObject(payload));
        System.out.printf(">>>> service: %s\n", service);
        Boolean success = servService.update(service);

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
