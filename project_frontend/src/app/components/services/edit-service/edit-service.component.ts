import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Services } from 'src/app/models';
import { ServicesService } from 'src/app/services/services.service';

@Component({
  selector: 'app-edit-service',
  templateUrl: './edit-service.component.html',
  styleUrls: ['./edit-service.component.css']
})
export class EditServiceComponent implements OnInit{

  activatedRoute = inject(ActivatedRoute)
  router = inject(Router)
  fb = inject(FormBuilder)
  servicesSvc = inject(ServicesService)

  id!: number
  service!: Services
  editForm!: FormGroup
  
  ngOnInit(): void {
    this.id = parseInt(this.activatedRoute.snapshot.params['id'])
    this.servicesSvc.getServicesById(this.id)
      .then(s => {
        console.log(s)
        this.service = s
        this.editForm = this.editServices()
      })
  }

  private editServices(){
    return this.fb.group({
      service_name: this.fb.control<string>(this.service.name, [Validators.required]),
      price: this.fb.control<number>(this.service.price, [Validators.required, Validators.min(1)]),
      duration: this.fb.control<number>(this.service.durationInMinutes, [Validators.required, Validators.min(1)]),
    })
  }

  process(){
    const service: Services = {
      id: this.id,
      name: this.editForm.value['service_name'],
      price: this.editForm.value['price'],
      durationInMinutes: this.editForm.value['duration']
    }
    this.servicesSvc.updateServices(service)
    .then(result => { 
      if(result.message == "failure"){
        alert(result.message)
        return
      }
      this.router.navigate(["/services-list"])
    })
  }
}
