import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, firstValueFrom, map } from 'rxjs';
import { Services } from '../models';

const getServicesUrl = '/api/services/all'
const searchServicesUrl = '/api/services/search'
const insertServicesUrl = '/api/services/insert'
const updateServicesUrl = '/api/services/update'

@Injectable({
  providedIn: 'root'
})
export class ServicesService {

  http = inject(HttpClient)

  getServices(): Observable<Services[]> {
    return this.http.get<Services[]>(getServicesUrl)
    .pipe(
      map( services => {
        return services.map( s => {
          return {
            id: s.id,
            name: s.name,
            price: s.price,
            durationInMinutes: s.durationInMinutes
          } as Services
        })
      })
    )
  }

  searchServices(query: string): Observable<Services[]> {
    const params = new HttpParams().set("name", query)
    return this.http.get<Services[]>(searchServicesUrl, { params })
    .pipe(
      map( services => {
        return services.map(s => {
          return {
            id: s.id,
            name: s.name,
            price: s.price,
            durationInMinutes: s.durationInMinutes
          } as Services
        })
      })
    )
  }

  getServicesById(id: number): Promise<Services>{
    let url = `/api/services/${id}`;
    return firstValueFrom(
      this.http.get<Services>(url)
    )
  }

  insertServices(Services: Services){
    return firstValueFrom(
      this.http.post<any>(insertServicesUrl, Services)
    )
  }

  updateServices(Services: Services){
    return firstValueFrom(
      this.http.put<any>(updateServicesUrl, Services)
    )
  }
}
