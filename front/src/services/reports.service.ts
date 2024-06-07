import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportsService {

  constructor(private http: HttpClient) { }

  public getTopDestinationsForOwner(): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/reports/top-owner", options);
  }

  public getTopDestinationsForTraveler(): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/reports/top-traveler", options);
  }

  public getSpentForTraveler(): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/reports/spent-traveler", options);
  }

  public getEarnedByOwner(): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/reports/earned-owner", options);
  }

  public getTopTravelers(): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/reports/top-travelers", options);
  }

  public getTopOwners(): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/reports/top-owners", options);
  }
}
