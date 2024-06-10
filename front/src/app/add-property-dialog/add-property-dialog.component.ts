import { LatLng, MapService } from './../../services/map.service';
import { AddressDTO, ListingDTO, ListingDestinationDTO, ListingLocationDTO, PropertyService } from './../../services/property.service';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { markFormControlsTouched } from '../validators/formGroupValidators';
import { NgxDropdownConfig } from 'ngx-select-dropdown';
import { Observable, map, startWith } from 'rxjs';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { LocationDTO, LocationService } from 'src/services/location.service';
import { AuthService } from 'src/services/auth.service';

@Component({
  selector: 'app-add-property-dialog',
  templateUrl: './add-property-dialog.component.html',
  styleUrls: ['./add-property-dialog.component.css']
})
export class AddPropertyDialogComponent implements OnInit {

  lat: number = 0;
  lng: number = 0;
  filePath: string = "";
  file: File = {} as File;
  selectedMarkerPosition: LatLng = {} as LatLng;
  loggedUser: any = {};
  role: any = {};

  options: LocationDTO[] = [];
  filteredOptions: Observable<LocationDTO[]> = new Observable();

  addPropertyForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    price: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    address: new FormControl('', [Validators.required]),
    cityAndCountry: new FormControl('', [Validators.required]),
  })


  constructor(
    public dialogRef: MatDialogRef<AddPropertyDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private snackBar: MatSnackBar,
    private propertyService: PropertyService,
    private locationService: LocationService,
    private mapService: MapService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loggedUser = this.authService.getUser();
    this.role = this.authService.getRole();
    // console.log("eee");

    this.locationService.getAll().subscribe({
        next: (value) => {
          // this.options = ['Novi Sad, Serbia', 'Belgrade, Serbia']
            this.options = value;
            this.filteredOptions = this.addPropertyForm.get('cityAndCountry')!.valueChanges.pipe(
                startWith(''),
                map(value => {
                    const location = typeof value === 'string' ? value : (value as unknown as LocationDTO).location;
                    return location ? this.filter(location) : this.options.slice();
                })
            );
        },
        error: (err) => {
            console.log(err);
        },
    });
    
    markFormControlsTouched(this.addPropertyForm);
  }


  decodeSelectedAddress(event: LatLng) {
    this.mapService.decodeCoordinates(event).subscribe({
      next: (value) => {
        console.log(value)
        if (value != undefined) {
          this.addPropertyForm.get('address')?.setValue(value.split(",")[0]);
        }
      },
      error: (err) => {
        console.log(err);
      },
    })
    
  }

  onEnterKeyPressed() {
    if (this.addPropertyForm.value.address && this.addPropertyForm.value.cityAndCountry) {
      // console.log(this.addPropertyForm.value.cityAndCountry)
      // let tokens = (this.addPropertyForm.value.cityAndCountry as unknown as LocationDTO).location.trim().split(", ")
      let tokens = this.addPropertyForm.value.cityAndCountry.trim().split(", ");
      this.mapService.decodeAddress(this.addPropertyForm.value.address, tokens[0], tokens[1]).subscribe({
        next: (value) => {
          this.selectedMarkerPosition = value;
          this.lat = this.selectedMarkerPosition.lat;
          this.lng = this.selectedMarkerPosition.lng;
          console.log(this.lat);
        },
        error: (err) => {
          console.log(err);
        },
      })
      // this.selectedMarkerPosition
    }
  }

  displayLocation(value: LocationDTO): string {
    return value.location;
  }

  filter(value: string): LocationDTO[] {
    console.log(value)
      return this.options.filter(option =>
          option.location.toLowerCase().includes(value.toLowerCase())
      );
  }

  rememberSelection(event: MatAutocompleteSelectedEvent) {
    console.log("izabrano: " + event.option.value.location);
    let tokens = event.option.value.location?.trim().split(", ");
    this.mapService.setCityCoordinates(tokens[0], tokens[1]);
  }

  addProperty() {
    console.log(this.addPropertyForm.value?.cityAndCountry!);
    if (this.selectedMarkerPosition && this.lat != 0 && this.lng != 0) {
      console.log(this.selectedMarkerPosition)
      let location: ListingLocationDTO = {
        // cityId: (this.addPropertyForm.value.cityAndCountry! as unknown as LocationDTO).cityId,
        lat: this.lat,
        lng: this.lng,
        address: this.addPropertyForm.value.address!
      }

      let destination: ListingDestinationDTO = {
        name: this.addPropertyForm.value.cityAndCountry!
      }

      let dto: ListingDTO = {
        id: 0,
        title: this.addPropertyForm.value.name!,
        price: +this.addPropertyForm.value.price!,
        description: this.addPropertyForm.value.description!,
        destination: destination,
        image: this.filePath,
        location: location
      }

      console.log(this.filePath)
      console.log(dto);
      console.log(this.loggedUser);
      this.propertyService.addListing(dto).subscribe({
        next: (value) => {
          this.snackBar.open("Property request sent.", "", {
          duration: 2700, panelClass: ['snack-bar-success']
          });
          console.log("done")
          console.log(value);
        },
        error(err) {
          console.log(err);
        },
      })
    }
  }

  selectionChanged(event: any) {
    console.log("event" + event);
  }

  close() {
    this.dialogRef.close();
  }

  onFileSelect(event: any) {
    event.preventDefault();

    if (event.target.files){
      var reader = new FileReader();
      this.file = event.target.files[0];
      reader.readAsDataURL(this.file);
      reader.onload=(e: any)=>{
        event.preventDefault();
        this.filePath = reader.result as string;
      }
    }
  }
}
