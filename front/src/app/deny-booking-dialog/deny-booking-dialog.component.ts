import { PropertyService, ReasonDTO } from '../../services/property.service';
import { Component, OnInit, Inject, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { BookingService } from 'src/services/booking.service';

@Component({
  selector: 'app-reject-property-dialog',
  templateUrl: './deny-booking-dialog.component.html',
  styleUrls: ['./deny-booking-dialog.component.css']
})
export class DenyBookingComponent implements OnInit {

  rejectionReasonForm = new FormGroup({
    reason: new FormControl('', [Validators.required])
  });


  constructor(public dialogRef: MatDialogRef<DenyBookingComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
    private propertyService: PropertyService,
    private snackBar: MatSnackBar,
    private bookingService: BookingService
  )
    
    { }

  ngOnInit(): void {
  }

  submitRejectionReason() {
    console.log(this.rejectionReasonForm.value.reason!);
    console.log(this.data.bookingId);
    // this.rejectionReasonForm.updateValueAndValidity();
    if (this.rejectionReasonForm.valid) {
      let reason: BookingRejectionNoticeDTO = {
        reason: this.rejectionReasonForm.value.reason!,
        bookingId: this.data.bookingId,
        // date: Date()
      }
      this.bookingService.denyBooking(reason).subscribe({
        next: (value) => {
          console.log(value);
          this.dialogRef.close();
          this.snackBar.open("You have successfully rejected the booking!", "", {
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
  }

}

export interface BookingRejectionNoticeDTO {
  reason: string,
  // date: string,
  bookingId: number
}