import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { firstValueFrom } from 'rxjs';

const sendAppointmentUrl = '/api/email/appointment'
const sendInvoiceUrl = '/api/email/invoice'

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  http = inject(HttpClient)

  sendAppointment(appointmentId: string){
    return firstValueFrom(
      this.http.post<any>(sendAppointmentUrl, appointmentId)
    )
  }

  sendInvoice(id: string){
    return firstValueFrom(
      this.http.post<any>(sendInvoiceUrl, id)
    )
  }
}
