import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, firstValueFrom, map } from 'rxjs';
import { Customer } from '../models';

const getCustomersUrl = '/api/customer/all'
const searchCustomersUrl = '/api/customer/search'
const insertCustomerUrl = '/api/customer/insert'
const updateCustomerUrl = '/api/customer/update'

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  http = inject(HttpClient)

  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(getCustomersUrl)
    .pipe(
      map( customers => {
        return customers.map(c => {
          return {
            id: c.id,
            name: c.name,
            gender: c.gender,
            phone: c.phone,
            email: c.email
          } as Customer
        })
      })
    )
  }

  searchCustomers(query: string): Observable<Customer[]> {
    const params = new HttpParams().set("name", query)
    return this.http.get<Customer[]>(searchCustomersUrl, { params })
    .pipe(
      map( customers => {
        return customers.map(c => {
          return {
            id: c.id,
            name: c.name,
            gender: c.gender,
            phone: c.phone,
            email: c.email
          } as Customer
        })
      })
    )
  }

  getCustomerById(id: number): Promise<Customer>{
    let url = `/api/customer/${id}`;
    return firstValueFrom(
      this.http.get<Customer>(url)
    )
  }

  insertCustomer(customer: Customer){
    return firstValueFrom(
      this.http.post<any>(insertCustomerUrl, customer)
    )
  }

  updateCustomer(customer: Customer){
    return firstValueFrom(
      this.http.put<any>(updateCustomerUrl, customer)
    )
  }
}
