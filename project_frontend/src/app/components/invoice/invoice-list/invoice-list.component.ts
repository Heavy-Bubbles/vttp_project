import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Invoice } from 'src/app/models';
import { EmailService } from 'src/app/services/email.service';
import { InvoiceService } from 'src/app/services/invoice.service';

@Component({
  selector: 'app-invoice-list',
  templateUrl: './invoice-list.component.html',
  styleUrls: ['./invoice-list.component.css']
})
export class InvoiceListComponent implements OnInit, OnDestroy{

  invoiceSvc = inject(InvoiceService)
  fb = inject(FormBuilder)
  emailSvc = inject(EmailService)

  invoices: Invoice[] = []
  searchForm!: FormGroup

  invSub$!: Subscription

  ngOnInit(): void {
    this.invSub$ = this.invoiceSvc.getInvoices().subscribe(
      i => {
        this.invoices = i
      }
    )
    this.searchForm = this.searchInvoices()
  }

  ngOnDestroy(): void {
    this.invSub$.unsubscribe()
  }

  private searchInvoices(){
    return this.fb.group({
      search: this.fb.control<string>('', [Validators.required])
    })  
  }

  search(){
    const query = this.searchForm.value['search'] + "T00:00:00"
    const date = new Date(query).getTime()
    this.invSub$.unsubscribe()
    this.invSub$ = this.invoiceSvc.searchInvoices(date).subscribe(
      i => {
        this.invoices = i
      }
    )
  }

  sendInvoice(id: string){
    this.emailSvc.sendInvoice(id)
    .then(result => { 
      if(result.message == "failure"){
        alert(result.message)
        return
      }
      alert(result.message)
    })  
  }
}
