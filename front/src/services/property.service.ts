import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  constructor(private http: HttpClient) { }

  addProperty(dto: any) {
    console.log(dto);
    return this.http.post<any>(environment.apiHost + "/property", dto, {withCredentials: true});
  }

  addListing(dto: any): Observable<any> {
    console.log(dto);
    return this.http.post<any>(environment.apiHost + "/listing", JSON.stringify(dto),  {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    });
  }

  getListingsForOwner() {
    return this.http.get<any>(environment.apiHost + "/listing/owner", {withCredentials: true});
  }

  getDetails(id: string) {
    return this.http.get<any>(environment.apiHost + "/property/details/" + id, {withCredentials: true});
  }

  getPaginatedProperties(page: number, size: number, ) {
    return this.http.get<any>(environment.apiHost + "/property/paginated?page=" + page +"&size=" + size, {withCredentials: true});
  }

  acceptPropertyRequest(id: number) {
    return this.http.put<any>(environment.apiHost + "/property/accept/" + id, {}, {withCredentials: true});
  }

  denyPropertyRequest(id: number, reason: ReasonDTO) {
    return this.http.put<any>(environment.apiHost + "/property/deny/" + id, reason, {withCredentials: true});
  }

  getRecsForUser(id: number) {
    return this.http.get<any>(environment.apiHost + "/listing/recommendations?id=" + id);
  }

  getAllListings() {
    return this.http.get<any>(environment.apiHost + "/listing/all");
  }

  getReviewsForListing(id: number): Observable<any> {
    return this.http.get<any>(environment.apiHost + "/listing/review?id=" + id);
  }

  addDiscount(dto: AddDiscountDTO): Observable<any> {
    return this.http.post<any>(environment.apiHost + "/listing/discount", dto,  {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    });
  }

  deleteDiscount(id: number): Observable<any> {
    return this.http.delete<any>(environment.apiHost + "/listing/discount?id=" + id);
  }

  
}

export interface ListingRecsDTO {
  listings: ReturnedListingDTO[]
}

export interface ListingDTO {
  id: number,
  title: string,
  price: number,
  description: string,
  image: string,
  location: ListingLocationDTO,
  destination: ListingDestinationDTO
}

export interface ReturnedListingDTO {
  id: number,
  title: string,
  price: number,
  description: string,
  image: string,
  location: ListingLocationDTO,
  destination: ListingDestinationDTO,
  rating: number,
  discount: ReturnedDiscountDTO

}

export interface FullListingDTO {
  id: number,
  title: string,
  price: number,
  description: string,
  image: string,
  location: FullListingLocationDTO
  rating: number

}


export interface ReturnedPropertyDTO {
  id: number,
  name: string,
  area: number,
  numOfFloors: number,
  image: string,
  status: string,
  address: ReturendAddressDTO,
  owner?: UserDTO
}

export interface AddressDTO {
  cityId: number,
  lat: number,
  lng: number, 
  name: string,
  location: ListingLocationDTO,
  destination: ListingDestinationDTO
}

export interface PropertyDTO{

}
export interface ListingLocationDTO {
  lat: number,
  lng: number, 
  address: string
}

export interface FullListingLocationDTO {
  lat: number,
  lng: number, 
  address: string
  destination: ListingDestinationDTO
}

export interface ListingDestinationDTO {
  name: string
}

export interface ReturendAddressDTO {
  city: string,
  country: string,
  lat: number,
  lng: number, 
  name: string,
  id: number
}

export interface PageResultDTO {
  count: number,
  pageIndex: number,
  pageSize: number,
  items: any[]
}

export interface ReturnedListingDTO{
  id: number,
  title: string,
  price: number,
  description: string,
  image: string,
  location: ListingLocationDTO,
  destination: ListingDestinationDTO
  owner?: ReturnedOwnerDTO
}

export interface ReturnedOwnerDTO{
  name: string,
  lastname: string,
  email: string, 
  id: number,
}

export interface UserDTO {
  name: string,
  surname: string,
  email: string, 
  id: number,
  role: string,
  isActivated: boolean
}

export interface ReasonDTO {
  reason: string
}

export interface ReturnedReviewDTO {
  id: number,
  rating: number,
  comment: string,
  date: string,
  travelerFullName: string,
  travelerId: number,
  listingId: number
}

export interface AddDiscountDTO {
  listingId: number,
  ownerId: number,
  amount: number,
  validTo: string
}

export interface ReturnedDiscountDTO {
  id: number,
  amount: number,
  validTo: string
}