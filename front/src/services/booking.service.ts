import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private http: HttpClient) { }

  public getByOwner(): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/booking/owner", options);
  }


  public getByTraveler(id: number): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/booking/traveler?id=" + id, options);
  }

  public denyBooking(dto: any): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.put<any>(environment.apiHost + "/booking/deny", dto, options);
  }

  public acceptBooking(bookingId: any): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.put<any>(environment.apiHost + "/booking/accept?id="+bookingId, options);
  }

  public cancelBooking(bookingId: any): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.put<any>(environment.apiHost + "/booking/cancel?bookingId="+bookingId, options);
  }

  public bookListing(dto: any): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    console.log(dto);
    // return null;
    return this.http.post<any>(environment.apiHost + "/booking", dto, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'})
      });
  }

  public reviewListing(dto: any): Observable<any> {
    const options: any = {
      // withCredentials: true
    };
    return this.http.post<any>(environment.apiHost + "/listing/review", dto, options);
  }

}