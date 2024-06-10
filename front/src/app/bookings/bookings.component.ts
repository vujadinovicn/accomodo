import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/services/auth.service';
import { BookingService } from 'src/services/booking.service';
import { DenyBookingComponent } from '../deny-booking-dialog/deny-booking-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

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
  enableClick: boolean = false;
  status: string = "PENDING";
  selectedCar: string = "volvo";
  allBookings: ReturnedBookingsDTO[] = [];

  bookings: ReturnedBookingsDTO[] = [];

  ngOnInit(): void {
    this.role = this.authService.getRole();

    if (this.role == "ROLE_OWNER") {
      this.bookingService.getByOwner().subscribe(data => {
        console.log(data);
        this.bookings = data;
        this.sortBookings();
        this.allBookings = data;
        console.log(this.bookings[0].status);
        console.log(this.role);
      });
    }
    else if (this.role == "ROLE_TRAVELER") {
      this.bookingService.getByTraveler().subscribe(data => {
        console.log(data);
        this.bookings = data;
        this.allBookings = data;
        this.sortBookings();
        console.log(this.bookings[0].status);
        console.log(this.role);
      });
    }
  }

  sortBookings(){
    this.bookings.sort((a, b) => {
      const dateA = new Date(a.startDate);
      const dateB = new Date(b.startDate);
    
      return dateB.getTime() - dateA.getTime();
    });
  }

  denyBooking(index: any){
    const dialogRef = this.dialog.open(DenyBookingComponent, { data: { bookingId: this.bookings[index].bookingId } });

    dialogRef.afterClosed().subscribe((result) => {
      // this.rejectionCompleted.emit(true);
    });
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
    this.bookings = this.allBookings.filter(item => item.status == status);
  }

}

export interface ReturnedBookingsDTO{
  bookingId: number,
  travelerId: number,
  ownerId: number,
  travelerName: string,
  ownerName: string,
  listingName: string,
  status: string,
  startDate: Date,
  endDate: Date

}
