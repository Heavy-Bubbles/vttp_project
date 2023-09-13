import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Appointment, AppointmentDetails, Customer, Services } from 'src/app/models';
import { AppointmentService } from 'src/app/services/appointment.service';
import { CustomerService } from 'src/app/services/customer.service';
import { ServicesService } from 'src/app/services/services.service';

@Component({
  selector: 'app-appointment-details',
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.css']
})
export class AppointmentDetailsComponent implements OnInit, OnDestroy{
  
  appointmentSvc = inject(AppointmentService)
  customerSvc = inject(CustomerService)
  servicesSvc = inject(ServicesService)
  activatedRoute = inject(ActivatedRoute)

  services: string[] = []
  service!: Services
  appointmentDetails: AppointmentDetails[] = []
  appointment!: Appointment
  customer!: Customer
  id!: string

  apptDetsSub$!: Subscription

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id']
    this.appointmentSvc.getAppointmentById(this.id)
      .then(a => {
        this.appointment = a
        this.customerSvc.getCustomerById(this.appointment.customerId)
          .then(c => {
            this.customer = c
          })
      })  
      this.apptDetsSub$ = this.appointmentSvc.getAppointmentDetails(this.id).subscribe(
        a => {
          this.appointmentDetails = a
          for (const a of this.appointmentDetails) {
            this.servicesSvc.getServicesById(a.serviceId)
              .then(s => {
                this.service = s
                this.services.push(this.service.name)
              })  
          }
        }
      )
  }

  ngOnDestroy(): void {
    this.apptDetsSub$.unsubscribe()
  }
}
