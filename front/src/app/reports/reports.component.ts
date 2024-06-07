import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from 'src/services/auth.service';
import { ReportsService } from 'src/services/reports.service';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css', '../homepage/homepage.component.css']
})
export class ReportsComponent implements OnInit {

  role: string = "";
  topDestinationsForOwnerKey: any[] = [];
  topDestinationsForOwnerValue: any[] = [];

  topDestinationsForTravelerKey: any[] = [];
  topDestinationsForTravelerValue: any[] = [];

  topOwnersKey: any[] = [];
  topOwnersValue: any[] = [];
  topTravelersKey: any[] = [];
  topTravelersValue: any[] = [];


  earnedByOwner: { [index: string]: any; } = {};
  spentByTraveler: { [index: string]: any; } = {};

  constructor(private authService: AuthService, private reportsService: ReportsService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    let loggedUser = this.authService.getUser();
    // this.name = loggedUser? loggedUser.name: "";
    this.role = this.authService.getRole();
    if(this.role == "ROLE_OWNER"){
      this.getTopDestinationsForOwner();
      this.getEarnedByOwner();
    }
    if(this.role == "ROLE_TRAVELER"){
      this.getTopDestinationsForTraveler();
      this.getSpentForTraveler();
    }

    this.getTopTravelers();
    this.getTopOwners();

    // this.role='ROLE_ADMIN';
  }

  private getTopTravelers(){
    this.reportsService.getTopTravelers().subscribe(data => {
      console.log(data);
      for (let key in data) {
        let value = data[key];
        this.topTravelersKey.push(key);
        this.topTravelersValue.push(value);
        console.log(value);
      }
    });
  }

  private getTopOwners(){
    this.reportsService.getTopOwners().subscribe(data => {
      console.log(data);
      for (let key in data) {
        let value = data[key];
        this.topOwnersKey.push(key);
        this.topOwnersValue.push(value);
        console.log(value);
      }
    });
  }

  private getSpentForTraveler() {
    this.reportsService.getSpentForTraveler().subscribe(data => {
      this.spentByTraveler = data;
    });
  }

  private getEarnedByOwner() {
    this.reportsService.getEarnedByOwner().subscribe(data => {
      this.earnedByOwner = data;
    });
  }

  private getTopDestinationsForOwner() {
    this.reportsService.getTopDestinationsForOwner().subscribe(data => {
      for (let key in data) {
        let value = data[key];
        this.topDestinationsForOwnerKey.push(key);
        this.topDestinationsForOwnerValue.push(value);
        console.log(value);
      }
    });
  }

  private getTopDestinationsForTraveler() {
    this.reportsService.getTopDestinationsForTraveler().subscribe(data => {
      for (let key in data) {
        let value = data[key];
        this.topDestinationsForTravelerKey.push(key);
        this.topDestinationsForTravelerValue.push(value);
        console.log(value);
      }
    });
  }
}
