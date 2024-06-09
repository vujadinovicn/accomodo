import { FullListingDTO, ListingDTO, ListingRecsDTO, PropertyDTO, PropertyService, ReturnedListingDTO, ReturnedPropertyDTO } from './../../services/property.service';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AddPropertyDialogComponent } from '../add-property-dialog/add-property-dialog.component';
import { AuthService } from 'src/services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PropertyDetailsService } from 'src/services/property-details.service';
import { ViewListingDialogComponent } from '../view-listing-dialog/view-listing-dialog.component';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  properties: ReturnedListingDTO[] = [];
  recListings: FullListingDTO[] = [];
  currentPage = 1;
  enableClick: boolean =true;
  pageSize = 4;
  count = 0;

  loggedUser: any = {};
  role: any = {};


  constructor(private dialog: MatDialog, private propertyService: PropertyService, 
    private authService: AuthService, private snackBar: MatSnackBar,
    private router: Router,
    private propertyDetailsService: PropertyDetailsService) { }

  ngOnInit(): void {

    this.loggedUser = this.authService.getUser();
    // this.name = loggedUser? loggedUser.name: "";
    this.role = this.authService.getRole();

    this.loadItems();

    // this.loggedUser = loggedUser;
    console.log("eee");
    // console.log(this.loggedUser.role)
  }

  loadItems(): void {

    // this.propertyService.getPaginatedProperties(this.currentPage, this.pageSize).subscribe({
    //   next: (value) => {
    //     console.log(value)
    //     this.currentPage = value.pageIndex;
    //     this.count = value.count;
    //     this.properties = value.items;
    //   }, 
    //   error: (err) => {
    //     console.log(err);
    //   }
    // });
    
    if (this.role == "ROLE_TRAVELER") {
      console.log(this.role)
      this.propertyService.getRecsForUser(this.loggedUser.id).subscribe({
        next: (value: ListingRecsDTO) => {
              console.log("dobijeno za recs" + JSON.stringify(value, null, 2));
              // this.currentPage = value.pageIndex;
              // this.count = value.count;
              console.log(value.listings[0]);
              this.recListings = value.listings;
            }, 
            error: (err) => {
              console.log(err);
            }
      });

      this.propertyService.getAllListings().subscribe({
        next: (value) => {
              console.log("dobijeno za sve" + JSON.stringify(value, null, 2));
              this.properties = value;
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
  
  openListingDetails(index: any) {
    const dialogRef = this.dialog.open(ViewListingDialogComponent, { data: { listing: this.properties[index] } });;

    dialogRef.afterClosed().subscribe((result) => {
      // this.loadItems();
    });
  }


}
