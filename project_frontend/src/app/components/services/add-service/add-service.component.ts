import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Services } from 'src/app/models';
import { ServicesService } from 'src/app/services/services.service';

@Component({
  selector: 'app-add-service',
  templateUrl: './add-service.component.html',
  styleUrls: ['./add-service.component.css']
})
export class AddServiceComponent implements OnInit{

  servicesSvc = inject(ServicesService)
  fb = inject(FormBuilder)
  router = inject(Router)

  servicesForm!: FormGroup

  ngOnInit(): void {
    this.servicesForm = this.insertServices()
  }

  private insertServices(){
    return this.fb.group({
      service_name: this.fb.control<string>('', [Validators.required]),
      price: this.fb.control<number>(0, [Validators.required, Validators.min(1)]),
      duration: this.fb.control<number>(0, [Validators.required, Validators.min(1)]),
    })
  }

  process(){
    const service: Services = {
      id: 0,
      name: this.servicesForm.value['service_name'],
      price: this.servicesForm.value['price'],
      durationInMinutes: this.servicesForm.value['duration'],
    }
    console.log(service)
    this.servicesSvc.insertServices(service)
    .then(result => { 
      if(result.message == "failure"){
        alert(result.message)
        return
      }
      this.router.navigate(["/services-list"])
    })
  }  
}
