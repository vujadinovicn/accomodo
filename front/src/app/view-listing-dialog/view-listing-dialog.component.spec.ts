import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewListingDialogComponent } from './view-listing-dialog.component';

describe('ViewListingDialogComponent', () => {
  let component: ViewListingDialogComponent;
  let fixture: ComponentFixture<ViewListingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewListingDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewListingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
