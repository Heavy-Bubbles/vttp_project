import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from 'src/app/models';
import { CustomerService } from 'src/app/services/customer.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-edit-customer',
  templateUrl: './edit-customer.component.html',
  styleUrls: ['./edit-customer.component.css']
})
export class EditCustomerComponent implements OnInit{

  activatedRoute = inject(ActivatedRoute)
  router = inject(Router)
  fb = inject(FormBuilder)
  customerSvc = inject(CustomerService)

  id!: number
  customer!: Customer
  editForm!: FormGroup

  ngOnInit(): void {
    this.id = parseInt(this.activatedRoute.snapshot.params['id'])
    this.customerSvc.getCustomerById(this.id)
      .then(c => {
        console.log(c)
        this.customer = c
        this.editForm = this.editCustomer()
      })
  }

  private editCustomer(){
    return this.fb.group({
      customer_name: this.fb.control<string>(this.customer.name, [Validators.required]),
      gender: this.fb.control<string>(this.customer.gender, [Validators.required]),
      phone: this.fb.control<string>(this.customer.phone, [Validators.required, Validators.pattern('^[0-9]{8}$')]),
      email: this.fb.control<string>(this.customer.email,[Validators.required, Validators.email])
    })
  }

  process(){
    const customer: Customer = {
      id: this.id,
      name: this.editForm.value['customer_name'],
      gender: this.editForm.value['gender'],
      phone: this.editForm.value['phone'],
      email: this.editForm.value['email']
    }
    this.customerSvc.updateCustomer(customer)
    .then(result => { 
      if(result.message == "failure"){
        alert(result.message)
        return
      }
      this.router.navigate(["/customer-list"])
    })
  }

}
