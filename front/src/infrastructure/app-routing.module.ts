import { FormControl } from '@angular/forms';
import { NgModule, Component } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { LoginComponent } from 'src/app/login/login.component';
import { HomepageComponent } from 'src/app/homepage/homepage.component';
import { RegisterComponent } from 'src/app/register/register.component';
import { ReportsComponent } from 'src/app/reports/reports.component';
import { BookingsComponent } from 'src/app/bookings/bookings.component';
import { UsersComponent } from 'src/app/users/users.component';
import { RegisterOwnerComponent } from 'src/app/register-owner/register-owner.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'home', component: HomepageComponent},
  {path: 'bookings', component: BookingsComponent},
  {path: 'users', component: UsersComponent},
  {path: 'register-owner', component: RegisterOwnerComponent},
  {path: 'reports', component: ReportsComponent},
  {path: '**', component: HomepageComponent},
  {path: '*', component: HomepageComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
