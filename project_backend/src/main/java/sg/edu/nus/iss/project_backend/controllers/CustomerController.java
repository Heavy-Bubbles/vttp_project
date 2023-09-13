package sg.edu.nus.iss.project_backend.controllers;

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
import sg.edu.nus.iss.project_backend.models.Customer;
import sg.edu.nus.iss.project_backend.services.CustomerService;
import sg.edu.nus.iss.project_backend.utils.Utility;

import static sg.edu.nus.iss.project_backend.utils.Constants.ALL;
import static sg.edu.nus.iss.project_backend.utils.Constants.CUSTOMER;
import static sg.edu.nus.iss.project_backend.utils.Constants.INSERT;
import static sg.edu.nus.iss.project_backend.utils.Constants.UPDATE;
import static sg.edu.nus.iss.project_backend.utils.Constants.SEARCH;

import java.util.List;

@RestController
@RequestMapping(CUSTOMER)
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Integer id) {
        Customer customer = customerService.getById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping(ALL)
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customers = customerService.getAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping(SEARCH)
        public ResponseEntity<List<Customer>> search(@RequestParam(required = true) String name) {
        List<Customer> customers = customerService.search(name);
        return ResponseEntity.ok(customers);
    }

    @PostMapping(INSERT) 
    public ResponseEntity<String> insert(@RequestBody String payload){
        Customer customer = Utility.toCustomer(Utility.toJsonObject(payload));
        System.out.printf(">>>> customer: %s\n", customer);
        Boolean success = customerService.insert(customer);

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
        Customer customer = Utility.toCustomer(Utility.toJsonObject(payload));
        System.out.printf(">>>> customer: %s\n", customer);
        Boolean success = customerService.update(customer);

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
