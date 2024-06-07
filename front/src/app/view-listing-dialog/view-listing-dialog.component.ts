import { PropertyService, ReasonDTO } from '../../services/property.service';
import { Component, OnInit, Inject, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { BookingService } from 'src/services/booking.service';

@Component({
  selector: 'app-view-listing-dialog',
  templateUrl: './view-listing-dialog.component.html',
  styleUrls: ['./view-listing-dialog.component.css', ]
})
// '../homepage/homepage.component.css'
export class ViewListingDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ViewListingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
    private propertyService: PropertyService,
    private snackBar: MatSnackBar,
    private bookingService: BookingService,
    private authService: AuthService
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
  }

  book(isBooking: boolean){
    console.log(this.bookingForm.get('startDate')?.value);

    let dto : MakeBookingDTO = {
      startDate: this.bookingForm.get('startDate')?.value!,
      endDate: this.bookingForm.get('endDate')?.value!,
      status: "",
      isReservation: !isBooking,
      listingId: this.listing.id,
      travelerId: 1 //treba nam traveler id

    }

    this.bookingService.bookListing(dto).subscribe({
      next: (value) => {
        console.log(value);
        this.snackBar.open("You have successfully accepted the booking!", "", {
          duration: 2700, panelClass: ['snack-bar-success']
       });
      },
      error: (err) => {
        console.log(err)
        this.snackBar.open("An error occured while submiting rejection!", "", {
          duration: 2700, panelClass: ['snack-bar-server-error']
       });
      }
    });
  }

  submitRejectionReason() {
  }

  addProperty(){

  }
}

export interface MakeBookingDTO{
  startDate: string,
  endDate: string,
  status: string,
  isReservation: boolean,
  listingId: number,
  travelerId: number
}
