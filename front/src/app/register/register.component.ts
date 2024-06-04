import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  isVisible : boolean = false;
  filePath: string = "";
  file: File = {} as File;
  // selectedDate: Date = {} as Date;

  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required]),
    name: new FormControl('', [Validators.required]),
    surname: new FormControl('', [Validators.required]),
    selectedDate: new FormControl('')
  })

  constructor(
    private dialog: MatDialog,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
  }

  register(){
    console.log("nemnaja");
    if (this.registerForm.valid) {
      const form = {
        name: this.registerForm.get('name')?.value,
        lastname: this.registerForm.get('surname')?.value,
        email: this.registerForm.get('email')?.value,
        password: this.registerForm.get('password')?.value,
        role: 0,
        dateOfBirth: this.registerForm.get('selectedDate')?.value
      };
      this.authService.register(form).subscribe(
        (success) => {
            console.log("eee");
        },
        (error) => {
          console.error('Login error:', error);
          this.snackBar.open('An error occurred while registering in', 'Close', { duration: 3000 });
        }
      );
    } else {
      this.snackBar.open('Please fill in all required fields', 'Close', { duration: 3000 });
    }
  }
}
