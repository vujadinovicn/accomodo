import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ListingService {

  constructor(private http: HttpClient) { }

  public delete(id: number): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.delete<any>(environment.apiHost + "/listing?id=" + id, options);
  }

  public getByLocation(location: string): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/listing/backward?location=" + location, options);
  }

  public filterByRating(rating: number): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/listing/filter-rating?rating=" + rating, options);
  }

  public filterByMoney(min: number, max: number): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/listing/filter-price?min=" + min + "&max=" + max, options);
  }

  public getById(id: any): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    let dto: any = {
      listingId: id,
      userId: 1
    }
    console.log(dto);
    return this.http.get<any>(environment.apiHost + "/listing?listingId="+id + "&userId=1", options);
  }
}