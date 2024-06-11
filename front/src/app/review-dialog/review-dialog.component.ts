import { BookingService } from 'src/services/booking.service';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ReviewDTO } from '../bookings/bookings.component';

@Component({
  selector: 'app-review-dialog',
  templateUrl: './review-dialog.component.html',
  styleUrls: ['./review-dialog.component.css']
})
export class ReviewDialogComponent implements OnInit {

  c: string = ''

  constructor(
    public dialogRef: MatDialogRef<ReviewDialogComponent>,
    public snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: ReviewDTO,
    public bookingService: BookingService) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSaveClick(): void {
    if (this.data.comment == '' || this.data.rating == 0) {
      this.snackBar.open("Leave a comment or a rating.", "", {
        duration: 2000,
     });
     return;
    }

    this.bookingService.reviewListing(this.data).subscribe({
      next: (value) => {
          console.log("TUUUUUUUUUUUUUUUUU")
          this.snackBar.open("Your review is saved.", "", {
            duration: 2000,
         });
          this.dialogRef.close();
      },
      error: (err) => {
        console.log(err)
        this.snackBar.open(err.error, "", {
          duration: 2000,
       });
      },
    })
    
  }

}
