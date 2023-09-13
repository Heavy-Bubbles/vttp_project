import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Customer } from 'src/app/models';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit, OnDestroy{

  customerSvc = inject(CustomerService)
  fb = inject(FormBuilder)

  customers: Customer[] = []
  searchForm!: FormGroup

  custSub$!: Subscription

  ngOnInit(): void {
    this.custSub$ = this.customerSvc.getCustomers().subscribe(
      c => {
        this.customers = c
      }
    )
    this.searchForm = this.searchCustomer()
  }

  private searchCustomer(){
    return this.fb.group({
      search: this.fb.control<string>('', [Validators.required])
    })  
  }

  ngOnDestroy(): void {
    this.custSub$.unsubscribe()
  }

  search(){
    const query = this.searchForm.value['search']
    this.custSub$.unsubscribe()
    this.custSub$ = this.customerSvc.searchCustomers(query).subscribe(
      c => {
        this.customers = c
      }
    )
  }
}
