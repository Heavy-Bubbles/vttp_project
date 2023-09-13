import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, firstValueFrom, map } from 'rxjs';
import { Invoice } from '../models';

const insertInvoiceUrl = '/api/invoice/insert'
const getInvoicesUrl = '/api/invoice/all'
const searchInvoicesUrl = '/api/invoice/search'

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  http = inject(HttpClient)

  getInvoices(): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(getInvoicesUrl)
    .pipe(
      map( invoices => {
        return invoices.map(i => {
          return {
            id: i.id,
            appointmentId: i.appointmentId,
            amountDue: i.amountDue,
            invoiceDate: i.invoiceDate,
            url: i.url
          } as Invoice
        })
      })
    )
  }

  searchInvoices(query: number): Observable<Invoice[]> {
    const params = new HttpParams().set("date", query)
    return this.http.get<Invoice[]>(searchInvoicesUrl, { params })
    .pipe(
      map( invoices => {
        return invoices.map(i => {
          return {
            id: i.id,
            appointmentId: i.appointmentId,
            amountDue: i.amountDue,
            invoiceDate: i.invoiceDate,
            url: i.url
          } as Invoice
        })
      })
    )
  }

  insertInvoice(appointmentId: string){
    return firstValueFrom(
      this.http.post<any>(insertInvoiceUrl, appointmentId)
    )
  }
}
