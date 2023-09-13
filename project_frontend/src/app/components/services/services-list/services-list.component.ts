import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Services } from 'src/app/models';
import { ServicesService } from 'src/app/services/services.service';

@Component({
  selector: 'app-services-list',
  templateUrl: './services-list.component.html',
  styleUrls: ['./services-list.component.css']
})
export class ServicesListComponent implements OnInit, OnDestroy{

  servicesSvc = inject(ServicesService)
  fb = inject(FormBuilder)

  services: Services[] = []
  searchForm!: FormGroup

  servSub$!: Subscription

  ngOnInit(): void {
    this.servSub$ = this.servicesSvc.getServices().subscribe(
      s => {
        this.services = s
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
    this.servSub$.unsubscribe()
  }

  search(){
    const query = this.searchForm.value['search']
    this.servSub$.unsubscribe()
    this.servSub$ = this.servicesSvc.searchServices(query).subscribe(
      s => {
        this.services = s
      }
    )
  }
}
