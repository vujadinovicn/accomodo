import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/services/auth.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isVisible : boolean = false;
  submited: boolean = false;

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  })

  constructor(
    private dialog: MatDialog,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
  }

  login(){
    this.submited = true;

    const credentials = {
      email: this.loginForm.value.email,
      password: this.loginForm.value.password
    };

    if (this.loginForm.valid) {
      this.authService.login(credentials).subscribe({
        next: (result) => {
          this.processLogin(result);
        },
         error: (error) => {
          console.log(error);
          console.log("tu")
          console.log(error.error)
          if (error.error == "You should renew your password!"){
            this.snackBar.open("Your password expired. You should renew your password!", "", {
              duration: 2700, panelClass: ['snack-bar-server-error']
           });
           this.router.navigate(['password-rotation', this.loginForm.value.email]);
           return;
          }
          else if (error.error == "Captcha invalid! Are you a robot?"){
            this.snackBar.open("Your password expired. You should renew your password!", "", {
              duration: 2700, panelClass: ['snack-bar-server-error']
           });
           this.router.navigate(['password-rotation', this.loginForm.value.email]);
           return;
          }

          else if (error.error == "This account have not been activated yet!"){
            this.snackBar.open("This account have not been activated yet!", "", {
              duration: 2700, panelClass: ['snack-bar-server-error']
           });
           return;
          }

          else 
            this.snackBar.open("Bad credentials. Please try again!", "", {
              duration: 2700, panelClass: ['snack-bar-server-error']
          });
        },
      });
    }
  }

  processLogin(result: any) {
    localStorage.setItem('user', JSON.stringify(result.accessToken));
          // localStorage.setItem('refreshToken', JSON.stringify(result.refreshToken));
          this.authService.setUser();
          let user = this.authService.getUser();
          console.log(this.authService.getUser());
          this.authService.setLoggedIn(true);
          if (this.authService.getRole() == "ROLE_ADMIN")
            this.router.navigate(['users']);
          else
            this.router.navigate(['home']);
  }

  activate(){
    // const dialogRef = this.dialog.open(CodeDialogComponent);
  }

}
