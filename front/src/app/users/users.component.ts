import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css', '../homepage/homepage.component.css', '../reports/reports.component.css']
})
export class UsersComponent implements OnInit {

  constructor(private userService: UserService, private snackBar: MatSnackBar) { }

  users: any[] = [];
  role: any;

  ngOnInit(): void {
    // this.users.push("Neca vujadinovic");
    this.userService.getAdminUsers().subscribe({
      next: (value) => {
        console.log(value);
        this.users = value;
      },
      error: (err) => {
          console.log(err);
      },
  });
  }

  block(index: number){
    this.userService.block(this.users[index].email).subscribe({
      next: (value) => {
        // this.users = value;
        this.snackBar.open("You have blocked user!", "", {
          duration: 2700, panelClass: ['snack-bar-success']
          });
      },
      error: (err) => {
          console.log(err);
      },
  });
  }
  
  unblock(index: number){
    this.userService.unblock(this.users[index].email).subscribe({
      next: (value) => {
        // this.users = value;
        this.snackBar.open("You have unblocked user!", "", {
          duration: 2700, panelClass: ['snack-bar-success']
          });
      },
      error: (err) => {
          console.log(err);
      },
  });
  }
}
