import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FullListingDTO, ListingDTO, ListingRecsDTO, PropertyDTO, PropertyService, ReturnedListingDTO, ReturnedPropertyDTO } from './../../services/property.service';
import { MatDialog } from '@angular/material/dialog';
import { AddPropertyDialogComponent } from '../add-property-dialog/add-property-dialog.component';
import { AuthService } from 'src/services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PropertyDetailsService } from 'src/services/property-details.service';
import { ViewListingDialogComponent } from '../view-listing-dialog/view-listing-dialog.component';
import { ListingService } from 'src/services/listing.service';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  allProperties: ReturnedListingDTO[] = [];
  properties: ReturnedListingDTO[] = [];
  recListings: ReturnedListingDTO[] = [];
  currentPage = 1;
  enableClick: boolean =true;
  pageSize = 4;
  count = 0;
  travelerDetails: TravelerDetailsDTO = {} as TravelerDetailsDTO;

  loggedUser: any = {};
  role: any = {};
  selectedRating: string = "1";

  constructor(private dialog: MatDialog, private propertyService: PropertyService, 
    private authService: AuthService, private snackBar: MatSnackBar,
    private router: Router,
    private userService: UserService,
    private propertyDetailsService: PropertyDetailsService,
  private listingService: ListingService) { }

  ngOnInit(): void {

    this.loggedUser = this.authService.getUser();
    // this.name = loggedUser? loggedUser.name: "";
    this.role = this.authService.getRole();
    if (this.role == "ROLE_TRAVELER"){
      this.userService.getTravelerDetails().subscribe({
        next: (value: TravelerDetailsDTO) => {
              console.log(value);
              this.travelerDetails = value;
            }, 
            error: (err) => {
              console.log(err);
            }
      });
    }
    this.loadItems();
    
  }

  reset(): void {
    this.loadItems();
  }

  loadItems(): void {
    
    if (this.role == "ROLE_TRAVELER") {
      console.log(this.role);
      console.log(this.authService.getId())
      this.propertyService.getRecsForUser(this.authService.getId()).subscribe({
        next: (value: ListingRecsDTO) => {
              console.log("dobijeno za recs" + JSON.stringify(value, null, 2));
              // this.currentPage = value.pageIndex;
              // this.count = value.count;
              console.log(value.listings[0]);
              this.recListings = value.listings;
              // this.allProperties = value;
            }, 
            error: (err) => {
              console.log(err);
            }
      });

      this.propertyService.getAllListings().subscribe({
        next: (value) => {
              console.log("dobijeno za sve" + JSON.stringify(value, null, 2));
              this.properties = value;
              this.allProperties = value;
            }, 
            error: (err) => {
              console.log(err);
            }
      });
    } else {

      this.propertyService.getListingsForOwner().subscribe({
        next: (value) => {
              console.log(value)
              // this.currentPage = value.pageIndex;
              // this.count = value.count;
              this.properties = value;
              console.log(this.properties[0]);
            }, 
            error: (err) => {
              console.log(err);
            }
      })
    }

  }

  openAddPropertyDialog() {
    const dialogRef = this.dialog.open(AddPropertyDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      this.loadItems();
    });
  }
  
  openListingDetails(listing: any) {
    const dialogRef = this.dialog.open(ViewListingDialogComponent, { data: { listing: listing } });;

    dialogRef.afterClosed().subscribe((result) => {
      // if (this.role == "ROLE_TRAVELER")
      //   this.loadItems();  
    });
  }

  @ViewChild('locationInput', { static: true }) locationInput: ElementRef = {} as ElementRef;

  onEnter(value: string): void {
    console.log('Location value:', value);
    this.listingService.getByLocation(value).subscribe({
      next: (value) => {
            console.log(value)
            // this.currentPage = value.pageIndex;
            // this.count = value.count;
            this.properties = value;
            console.log(this.properties[0]);
          }, 
          error: (err) => {
            console.log(err);
          }
    })
  }

  onRatingChange(rating: string){
    this.listingService.filterByRating(parseInt(this.selectedRating)).subscribe({
      next: (value) => {
        // this.options = ['Novi Sad, Serbia', 'Belgrade, Serbia']
          let data = value;
          let temp: ReturnedListingDTO[] = [];
          console.log(data);
          for (let dto of data){
            for (let curr of this.allProperties){
              if (dto.id == curr.id){
                temp.push(dto);
                break;
              }
            }
          }
          console.log(temp);
          this.properties = temp;
      },
  });
  }


  onMaxPriceEnter(min: string, max: string){
    console.log(min, max);
    this.listingService.filterByMoney(parseFloat(min), parseFloat(max)).subscribe({
      next: (value) => {
        // this.options = ['Novi Sad, Serbia', 'Belgrade, Serbia']
          let data = value;
          let temp: ReturnedListingDTO[] = [];
          console.log(data);
          for (let dto of data){
            for (let curr of this.allProperties){
              if (dto.id == curr.id){
                temp.push(dto);
                break;
              }
            }
          }
          console.log(temp);
          this.properties = temp;
      },
  });
  }

  mapToDiscount(): string {
    if (this.travelerDetails.level == "BRONZE")
      return "5%";
    else if (this.travelerDetails.level == "BRONZE")
      return "10%";
    else 
      return "20%";
  }

  getRoundedRating(listing: any): number {
    return Math.round(listing.rating);
  }
}

export interface TravelerDetailsDTO {
  email: string,
  name: string,
  lastname: string,
  level: string
  }