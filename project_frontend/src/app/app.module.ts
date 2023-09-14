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
import { InvoiceService } from './services/invoice.service';
import { EmailService } from './services/email.service';
import { SocialLoginModule, SocialAuthServiceConfig } from '@abacritt/angularx-social-login';
import { GoogleLoginProvider, GoogleSigninButtonModule } from '@abacritt/angularx-social-login';
import { UserComponent } from './components/user/user/user.component';

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
  { path: 'invoice-list', component: InvoiceListComponent },
  { path: 'user', component: UserComponent }
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
    InvoiceListComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true }),
    ReactiveFormsModule,
    SocialLoginModule,
    GoogleSigninButtonModule,
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: !isDevMode(),
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [CustomerService,
              ServicesService,
              AppointmentService,
              InvoiceService,
              EmailService,
              {
                provide: 'SocialAuthServiceConfig',
                useValue: {
                  autoLogin: false,
                  providers: [
                    {
                      id: GoogleLoginProvider.PROVIDER_ID,
                      provider: new GoogleLoginProvider(
                        '856660676844-qr3sb6e1p79okrrdo9bnfujh6ocjq5ps.apps.googleusercontent.com'
                      )
                    }
                  ],
                  onError: (err) => {
                    console.error(err);
                  }
                } as SocialAuthServiceConfig,
              }],
  bootstrap: [AppComponent]
})
export class AppModule { }
