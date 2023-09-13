import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Customer } from 'src/app/models';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-add-customer',
  templateUrl: './add-customer.component.html',
  styleUrls: ['./add-customer.component.css']
})
export class AddCustomerComponent implements OnInit{

  customerSvc = inject(CustomerService)
  fb = inject(FormBuilder)
  router = inject(Router)

  customerForm!: FormGroup

  ngOnInit(): void {
    this.customerForm = this.insertCustomer()
  }

  private insertCustomer(){
    return this.fb.group({
      customer_name: this.fb.control<string>('', [Validators.required]),
      gender: this.fb.control<string>('', [Validators.required]),
      phone: this.fb.control<string>('', [Validators.required, Validators.pattern('^[0-9]{8}$')]),
      email: this.fb.control<string>('',[Validators.required, Validators.email])
    })
  }

  process(){
    const customer: Customer = {
      id: 0,
      name: this.customerForm.value['customer_name'],
      gender: this.customerForm.value['gender'],
      phone: this.customerForm.value['phone'],
      email: this.customerForm.value['email']
    }
    this.customerSvc.insertCustomer(customer)
    .then(result => { 
      if(result.message == "failure"){
        alert(result.message)
        return
      }
      this.router.navigate(["/customer-list"])
    })
  }

  
}
