package sg.edu.nus.iss.project_backend.controllers;

import static sg.edu.nus.iss.project_backend.utils.Constants.ALL;
import static sg.edu.nus.iss.project_backend.utils.Constants.INVOICE;
import static sg.edu.nus.iss.project_backend.utils.Constants.INSERT;
import static sg.edu.nus.iss.project_backend.utils.Constants.SEARCH;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import sg.edu.nus.iss.project_backend.models.Invoice;
import sg.edu.nus.iss.project_backend.services.InvoiceService;

@RestController
@RequestMapping(INVOICE)
public class InvoiceController {
    
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping(ALL)
    public ResponseEntity<List<Invoice>> getAll() {
        List<Invoice> invoices = invoiceService.getAll();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping(SEARCH)
    public ResponseEntity<List<Invoice>> search(@RequestParam(required = true) Long date) {
        List<Invoice> invoices = invoiceService.getByDate(date);
        return ResponseEntity.ok(invoices);
    }

    @PostMapping(INSERT) 
    public ResponseEntity<String> insert(@RequestBody String appointmentId){
        Boolean success = invoiceService.insert(appointmentId);

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
