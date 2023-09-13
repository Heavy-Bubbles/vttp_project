import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, firstValueFrom, map } from 'rxjs';
import { Appointment, AppointmentDetails, AppointmentFormData } from '../models';

const getAppointmentsUrl = '/api/appointment/all'
const searchAppointmentsUrl = '/api/appointment/search'
const insertAppointmentUrl = '/api/appointment/insert'
const deleteAppointmentUrl = '/api/appointment/delete'

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  http = inject(HttpClient)

  getAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(getAppointmentsUrl)
    .pipe(
      map( appointments => {
        return appointments.map(a => {
          return {
            id: a.id,
            appointmentStart: a.appointmentStart,
            appointmentEnd: a.appointmentEnd,
            customerId: a.customerId
          } as Appointment
        })
      })
    )
  }

  searchAppointments(query: number): Observable<Appointment[]> {
    const params = new HttpParams().set("date", query)
    return this.http.get<Appointment[]>(searchAppointmentsUrl, { params })
    .pipe(
      map( appointments => {
        return appointments.map(a => {
          return {
            id: a.id,
            appointmentStart: a.appointmentStart,
            appointmentEnd: a.appointmentEnd,
            customerId: a.customerId
          } as Appointment
        })
      })
    )
  }

  getAppointmentDetails(id: string): Observable<AppointmentDetails[]> {
    const url = `/api/appointment-details/${id}`
    return this.http.get<AppointmentDetails[]>(url)
    .pipe(
      map( apptDets => {
        return apptDets.map(a => {
          return {
            appointmentId: a.appointmentId,
            serviceId: a.serviceId
          } as AppointmentDetails
        })
      })
    )
  }

  getAppointmentById(id: string): Promise<Appointment>{
    let url = `/api/appointment/${id}`
    return firstValueFrom(
      this.http.get<Appointment>(url)
    )
  }

  insertAppointment(appointment: AppointmentFormData){
    return firstValueFrom(
      this.http.post<any>(insertAppointmentUrl, appointment)
    )
  }

  deleteAppointment(appointmentId: string){
    return firstValueFrom(
      this.http.post<any>(deleteAppointmentUrl, appointmentId)
    )
  }
}
