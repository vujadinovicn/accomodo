import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/services/auth.service';
import { BookingService } from 'src/services/booking.service';
import { DenyBookingComponent } from '../deny-booking-dialog/deny-booking-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReviewDialogComponent } from '../review-dialog/review-dialog.component';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.css', '../homepage/homepage.component.css']
})
export class BookingsComponent implements OnInit {

  constructor(private authService: AuthService, private bookingService: BookingService,
    private dialog: MatDialog, private snackBar: MatSnackBar
  ) { }

  role: any = {};
  loggedUserId: number = -1;
  enableClick: boolean = false;
  selectedCar: string = "volvo";
  allBookings: ReturnedBookingsDTO[] = [];

  bookings: ReturnedBookingsDTO[] = [];

  ngOnInit(): void {
    this.role = this.authService.getRole();
    this.loggedUserId = this.authService.getId();
    this.loadBookings();
  }

  sortBookings(){
    this.bookings.sort((a, b) => {
      const dateA = new Date(a.startDate);
      const dateB = new Date(b.startDate);
    
      return dateB.getTime() - dateA.getTime();
    });    
  }

  loadBookings() {
    if (this.role == "ROLE_TRAVELER") {
      this.bookingService.getByTraveler(this.loggedUserId).subscribe(data => {
        console.log(data);
        this.bookings = data;
        this.allBookings = data;
        this.sortBookings();
      });
    } else {
      this.bookingService.getByOwner().subscribe(data => {
        console.log(data);
        this.bookings = data;
        this.allBookings = data;
        this.sortBookings();
      });
    }
  }

  denyBooking(index: any){
    const dialogRef = this.dialog.open(DenyBookingComponent, { data: { bookingId: this.bookings[index].bookingId } });

    dialogRef.afterClosed().subscribe((result) => {
      // this.rejectionCompleted.emit(true);
    });
  }

  canCancel(booking: any): boolean {
    const now = new Date();
    const startDate = new Date(booking.startDate);

    return startDate > now && booking.status == "PENDING";
  }

  acceptBooking(index: any){
    this.bookingService.acceptBooking(this.bookings[index].bookingId).subscribe({
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

  cancelBooking(index: any){
    this.bookingService.cancelBooking(this.bookings[index].bookingId).subscribe({
        next: (value) => {
          console.log(value);
          this.snackBar.open("You have successfully canceled the booking!", "", {
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

  onStatusChange(status: any){
    console.log(status)
    if (status == "ALL")
      this.bookings = this.allBookings
    else
      this.bookings = this.allBookings.filter(item => item.status == status);
  }

  openDialog(index: any): void {
    let booking = this.bookings[index];
    let review: ReviewDTO = {
      listingId: booking.listingId, travelerId: this.loggedUserId,
      rating: 0,
      comment: ''
    };

    const dialogRef = this.dialog.open(ReviewDialogComponent, {
      width: '250px',
      data: review
    });

    dialogRef.afterClosed().subscribe({next: (value) => {
        this.loadBookings();
    },})

  }

}

export interface ReturnedBookingsDTO{
  bookingId: number,
  travelerId: number,
  listingId: number,
  ownerId: number,
  travelerName: string,
  ownerName: string,
  listingName: string,
  status: string,
  startDate: Date,
  endDate: Date,
  reviewable: boolean,
  reviewByTraveler: ReviewDTO,
}

export interface ReviewDTO {
  listingId: number,
  travelerId: number,
  rating: number,
  comment: string,

}