import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AppointmentFormData, Customer, Services } from 'src/app/models';
import { AppointmentService } from 'src/app/services/appointment.service';
import { CustomerService } from 'src/app/services/customer.service';
import { ServicesService } from 'src/app/services/services.service';

@Component({
  selector: 'app-add-appointment',
  templateUrl: './add-appointment.component.html',
  styleUrls: ['./add-appointment.component.css']
})
export class AddAppointmentComponent implements OnInit, OnDestroy{

  appointmentSvc = inject(AppointmentService)
  servicesSvc = inject(ServicesService)
  customerSvc = inject(CustomerService)
  fb = inject(FormBuilder)
  router = inject(Router)

  appointmentForm!: FormGroup
  servicesForm!: FormGroup
  servicesSelected: Services[] = []
  services: Services[] = []
  customers: Customer[] = []

  servSub$!: Subscription
  custSub$!: Subscription

  ngOnInit(): void {
    this.servSub$ = this.servicesSvc.getServices().subscribe(
      s => {
        this.services = s
      }
    )
    this.custSub$ = this.customerSvc.getCustomers().subscribe(
      c => {
        this.customers = c
      }
    )
    this.servicesForm = this.addServicesForm()
    this.appointmentForm = this.insertAppointment()
  }

  private insertAppointment(){
    return this.fb.group({
      customer: this.fb.control<string>('', [Validators.required, this.customerValidator.bind(this)]),
      appointmentStart: this.fb.control<string>('', [Validators.required]),
    })
  }

  customerValidator(control: AbstractControl): ValidationErrors | null {
    const inputValue = control.value;
    const isMatch = this.customers.some((customer: { id: number; }) => customer.id == inputValue);
    if (isMatch) {
      return null;
    } else {
      return { itemNotFound: true };
    }
  }

  private addServicesForm(){
    return this.fb.group({
      serviceName: this.fb.control<string>('', [Validators.required, this.serviceValidator.bind(this)])
    })
  }

  serviceValidator(control: AbstractControl): ValidationErrors | null {
    const inputValue = control.value;
    const isMatch = this.services.some((service: { name: string; }) => service.name == inputValue);
    if (isMatch) {
      return null;
    } else {
      return { itemNotFound: true };
    }
  }

  ngOnDestroy(): void {
    this.servSub$.unsubscribe()
    this.custSub$.unsubscribe()
  }

  addService(){
    const serviceName = this.servicesForm.value['serviceName']
    let service = this.services.find(s => s.name === serviceName) as Services;
    this.servicesSelected.push(service)
  }

  removeService(s: Services){
    let idx = this.servicesSelected.indexOf(s)
    this.servicesSelected.splice(idx, 1)
  }

  process(){
    let servicesArr: number[] = [];
    for (const s of this.servicesSelected) {
      servicesArr.push(s.id);
    }
    const appointment: AppointmentFormData = {
      customer: parseInt(this.appointmentForm.value['customer']),
      appointmentStart: new Date(this.appointmentForm.value['appointmentStart']).getTime(),
      services: servicesArr
    }
    console.log(appointment)
    this.appointmentSvc.insertAppointment(appointment)
    .then(result => { 
      if(result.message == "failure"){
        alert(result.message)
        return
      }
      this.router.navigate(["/"])
    })    
  }
}
