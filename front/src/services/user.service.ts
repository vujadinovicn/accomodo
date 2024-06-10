import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  public getAdminUsers(): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.get<any>(environment.apiHost + "/user/admin", options);
  }

  public block(id: any): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.put<any>(environment.apiHost + "/user/block?email=" +id, options);
  }

  public unblock(id: any): Observable<any> {
    const options: any = {
      responseType: 'json',
    //   withCredentials: true
    };
    return this.http.put<any>(environment.apiHost + "/user/unblock?email=" +id, options);
  }
}