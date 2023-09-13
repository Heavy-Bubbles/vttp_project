import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Appointment } from 'src/app/models';
import { AppointmentService } from 'src/app/services/appointment.service';
import { InvoiceService } from 'src/app/services/invoice.service';

@Component({
  selector: 'app-appointment-list',
  templateUrl: './appointment-list.component.html',
  styleUrls: ['./appointment-list.component.css']
})
export class AppointmentListComponent implements OnInit, OnDestroy{

  invoiceSvc = inject(InvoiceService)
  appointmentSvc = inject(AppointmentService)
  fb = inject(FormBuilder)

  appointments: Appointment[] = []
  searchForm!: FormGroup

  apptSub$!: Subscription

  ngOnInit(): void {
    this.apptSub$ = this.appointmentSvc.getAppointments().subscribe(
      a => {
        this.appointments = a
      }
    )
    this.searchForm = this.searchAppointment()
  }

  ngOnDestroy(): void {
    this.apptSub$.unsubscribe()
  }

  private searchAppointment(){
    return this.fb.group({
      search: this.fb.control<string>('', [Validators.required])
    })  
  }

  search(){
    const query = this.searchForm.value['search'] + "T00:00:00"
    const date = new Date(query).getTime()
    this.apptSub$.unsubscribe()
    this.apptSub$ = this.appointmentSvc.searchAppointments(date).subscribe(
      a => {
        this.appointments = a
      }
    )
  }

  generateInvoice(appointmentId: string){
    this.invoiceSvc.insertInvoice(appointmentId)
    .then(result => { 
      if(result.message == "failure"){
        alert(result.message)
        return
      }
        alert(result.message)
    }) 
  }

  delete(appointmentId: string){
    this.appointmentSvc.deleteAppointment(appointmentId)
    .then(result => { 
      if(result.message == "failure"){
        alert(result.message)
        return
      }
      this.apptSub$.unsubscribe()
      this.apptSub$ = this.appointmentSvc.getAppointments().subscribe(
        a => {
          this.appointments = a
        }
      )
      alert(result.message)
    }) 
  }

}
