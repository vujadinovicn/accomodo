import { AuthService } from 'src/services/auth.service';
import { NavbarService } from './../services/navbar.service';
import { Component, HostListener } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = "Accomodo";
  loggedIn: boolean = false; 
  sideVisible: Boolean = false;
  smallScreen: boolean = window.innerWidth < 900;
  private destroy$: Subject<void> = new Subject<void>();
  
  constructor(private navbarService: NavbarService, private authService: AuthService) {
      this.authService.recieveLoggedIn().subscribe({
        next: (value) => {
          this.loggedIn = value;
          if (!this.loggedIn) {
            this.loggedIn = this.authService.getUserFromStorage() == null? false: true;
          }
          console.log(this.loggedIn)
        },
        error: (err) => {
          console.log("Error getting current logged in information.")
        },
      })
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    this.smallScreen = window.innerWidth < 900;
  }

}
