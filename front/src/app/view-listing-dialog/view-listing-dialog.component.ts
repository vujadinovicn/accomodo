import { AddDiscountDTO, PropertyService, ReasonDTO, ReturnedReviewDTO } from '../../services/property.service';
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
  reviews: ReturnedReviewDTO[] = [];

  bookingForm = new FormGroup({
    endDate: new FormControl('', [Validators.required]),
    startDate: new FormControl('', [Validators.required]),
  })
  discountForm = new FormGroup({
    validTo: new FormControl('', [Validators.required]),
    amount: new FormControl(0, [Validators.required]),
  })

  ngOnInit(): void {
    this.listing = this.data.listing;
    this.role = this.authService.getRole();
    // this.listingService.getById(this.listing.id).subscribe({});
    this.propertyService.getReviewsForListing(this.listing.id).subscribe({
      next: (value: ReturnedReviewDTO[]) => {
          this.reviews = value;
      },
      error: (err) => {
        this.snackBar.open(err.error, "", {
          duration: 2700, panelClass: ['snack-bar-server-error']
       });
      },
    });
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
      travelerId: this.authService.getId(),
      pricePerNight: this.listing.price - this.listing.discount.amount
       //treba nam traveler id
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

  deleteDiscount() {
    this.propertyService.deleteDiscount(this.listing.discount.id).subscribe({
      next: (value) => {
        this.snackBar.open(value.message, "", {
          duration: 2700, panelClass: ['snack-bar-server-error']
       });
       this.listing.discount = null;
      },
      error: (err) => {
        this.snackBar.open(err.error, "", {
          duration: 2700, panelClass: ['snack-bar-server-error']
       });
      },
    })
  }

  addDiscount() {
    if (this.discountForm.valid) {
      if (this.listing.discount) {
        this.snackBar.open("Please delete the current discount before adding a new one.", "", {
          duration: 2700, panelClass: ['snack-bar-server-error']
       });
       return;
      }
      console.log(this.discountForm.value);
      let dto: AddDiscountDTO = {
        listingId: this.listing.id,
        ownerId: this.authService.getId(),
        amount: this.discountForm.value.amount!,
        validTo: this.discountForm.value.validTo!
      }

      console.log(dto);
      this.propertyService.addDiscount(dto).subscribe({
        next: (value) => {
            console.log("success" + value);
            this.snackBar.open("Discount added successfully!", "", {
              duration: 2700, panelClass: ['snack-bar-server-error']
           });
           this.listing.discount = value;
        },
        error: (err) => {
          console.log(err)
          this.snackBar.open(err.error, "", {
            duration: 2700, panelClass: ['snack-bar-server-error']
         });
        },
      });
    }
  }

  getRoundedRating(): number {
    return Math.round(this.listing.rating);
  }
}



export interface MakeBookingDTO{
  startDate: any,
  endDate: any,
  status: number,
  reservation: boolean,
  listingId: number,
  travelerId: number,
  pricePerNight: number
}
