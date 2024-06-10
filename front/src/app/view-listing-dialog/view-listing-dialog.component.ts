import { PropertyService, ReasonDTO } from '../../services/property.service';
import { Component, OnInit, Inject, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { BookingService } from 'src/services/booking.service';
import { ListingService } from 'src/services/listing.service';

@Component({
  selector: 'app-view-listing-dialog',
  templateUrl: './view-listing-dialog.component.html',
  styleUrls: ['./view-listing-dialog.component.css' ]
})
// '../homepage/homepage.component.css'
export class ViewListingDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ViewListingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
    private propertyService: PropertyService,
    private snackBar: MatSnackBar,
    private bookingService: BookingService,
    private authService: AuthService,
    private listingService: ListingService
  ) { }

  enableClick: boolean = false;
  listing: any;
  role: any;

  bookingForm = new FormGroup({
    endDate: new FormControl('', [Validators.required]),
    startDate: new FormControl('', [Validators.required]),
  })

  ngOnInit(): void {
    this.listing = this.data.listing;
    this.role = this.authService.getRole();
    this.listingService.getById(this.listing.id).subscribe({});
  }

  parseDateString(dateString: string): Date {
    return new Date(dateString);
  }

  book(isBooking: boolean){
    let dto : MakeBookingDTO = {
      startDate: this.parseDateString(this.bookingForm.get('startDate')?.value!),
      endDate: this.parseDateString(this.bookingForm.get('endDate')?.value!),
      status: 0,
      reservation: !isBooking,
      listingId: this.listing.id,
      travelerId: 1 //treba nam traveler id
    }


    this.bookingService.bookListing(dto).subscribe({
      next: (value) => {
        console.log(value);
        this.snackBar.open("You have successfully made the booking!", "", {
          duration: 2700, panelClass: ['snack-bar-success']
       });
      },
      error: (err) => {
        console.log(err)
        this.snackBar.open("An error occured while booking!", "", {
          duration: 2700, panelClass: ['snack-bar-server-error']
       });
      }
    });
  }

  deleteListing(){
    this.listingService.delete(this.listing.id).subscribe({
      next: (value) => {
        console.log(value);
        this.snackBar.open("You have successfully deleted listing!", "", {
          duration: 2700, panelClass: ['snack-bar-success']
       });
      },
      error: (err) => {
        console.log(err)
        this.snackBar.open("An error occured while submiting deletion!", "", {
          duration: 2700, panelClass: ['snack-bar-server-error']
       });
      }
    });
  }
}

export interface MakeBookingDTO{
  startDate: any,
  endDate: any,
  status: number,
  reservation: boolean,
  listingId: number,
  travelerId: number
}
