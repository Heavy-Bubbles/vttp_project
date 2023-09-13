import { NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ServiceWorkerModule } from '@angular/service-worker';
import { CustomerListComponent } from './components/customer/customer-list/customer-list.component';
import { RouterModule, Routes } from '@angular/router';
import { ServicesListComponent } from './components/services/services-list/services-list.component';
import { EditCustomerComponent } from './components/customer/edit-customer/edit-customer.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CustomerService } from './services/customer.service';
import { AddCustomerComponent } from './components/customer/add-customer/add-customer.component';
import { ServicesService } from './services/services.service';
import { AddServiceComponent } from './components/services/add-service/add-service.component';
import { EditServiceComponent } from './components/services/edit-service/edit-service.component';
import { AppointmentListComponent } from './components/appointment/appointment-list/appointment-list.component';
import { AddAppointmentComponent } from './components/appointment/add-appointment/add-appointment.component';
import { AppointmentService } from './services/appointment.service';
import { AppointmentDetailsComponent } from './components/appointment/appointment-details/appointment-details.component';
import { InvoiceListComponent } from './components/invoice/invoice-list/invoice-list.component';

const appRoutes: Routes = [

  { path: '', component: AppointmentListComponent },
  { path: 'add-appointment', component: AddAppointmentComponent },
  { path: 'appointment-details/:id', component: AppointmentDetailsComponent },
  { path: 'customer-list', component: CustomerListComponent },
  { path: 'add-customer', component: AddCustomerComponent },
  { path: 'edit-customer/:id', component: EditCustomerComponent },
  { path: 'services-list', component: ServicesListComponent },
  { path: 'add-services', component: AddServiceComponent },
  { path: 'edit-services/:id', component: EditServiceComponent },
  { path: 'invoice-list', component: InvoiceListComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    CustomerListComponent,
    ServicesListComponent,
    EditCustomerComponent,
    AddCustomerComponent,
    AddServiceComponent,
    EditServiceComponent,
    AppointmentListComponent,
    AddAppointmentComponent,

    AppointmentDetailsComponent,
    InvoiceListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true }),
    ReactiveFormsModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [CustomerService,
              ServicesService,
              AppointmentService],
  bootstrap: [AppComponent]
})
export class AppModule { }
