import { ListingDTO, PropertyDTO, PropertyService, ReturnedPropertyDTO } from './../../services/property.service';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddPropertyDialogComponent } from '../add-property-dialog/add-property-dialog.component';
import { AuthService } from 'src/services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PropertyDetailsService } from 'src/services/property-details.service';
import { ViewListingDialogComponent } from '../view-listing-dialog/view-listing-dialog.component';
import { ListingService } from 'src/services/listing.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  properties: ListingDTO[] = [];
  allProperties: ListingDTO[] = [];
  currentPage = 1;
  enableClick: boolean =true;
  pageSize = 4;
  count = 0;

  loggedUser: any = {};
  role: any = {};
  selectedRating: string = "1";

  constructor(private dialog: MatDialog, private propertyService: PropertyService, 
    private authService: AuthService, private snackBar: MatSnackBar,
    private router: Router,
    private propertyDetailsService: PropertyDetailsService,
  private listingService: ListingService) { }

  ngOnInit(): void {
    this.loadItems();

    let loggedUser = this.authService.getUser();
    // this.name = loggedUser? loggedUser.name: "";
    this.role = this.authService.getRole();
    // this.loggedUser = loggedUser;
    console.log("eee");
    // console.log(this.loggedUser.role)
  }

  loadItems(): void {

    this.propertyService.getListingsForOwner().subscribe({
      next: (value) => {
            console.log(value)
            // this.currentPage = value.pageIndex;
            // this.count = value.count;
            this.properties = value;
            this.allProperties = value;
            console.log(this.properties[0]);
          }, 
          error: (err) => {
            console.log(err);
          }
    })
  }

  openAddPropertyDialog() {
    const dialogRef = this.dialog.open(AddPropertyDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      this.loadItems();
    });
  }
  
  openListingDetails(index: any) {
    const dialogRef = this.dialog.open(ViewListingDialogComponent, { data: { listing: this.properties[index] } });;

    dialogRef.afterClosed().subscribe((result) => {
      // this.loadItems();
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
          let temp: ListingDTO[] = [];
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
          let temp: ListingDTO[] = [];
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


}
