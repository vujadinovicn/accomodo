import { ListingDTO, PropertyDTO, PropertyService, ReturnedPropertyDTO } from './../../services/property.service';
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
  properties: ListingDTO[] = [];
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
    this.loadItems();

    let loggedUser = this.authService.getUser();
    // this.name = loggedUser? loggedUser.name: "";
    this.role = this.authService.getRole();
    // this.loggedUser = loggedUser;
    console.log("eee");
    // console.log(this.loggedUser.role)
  }

  loadItems(): void {
    console.log("idi u kurac");

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
