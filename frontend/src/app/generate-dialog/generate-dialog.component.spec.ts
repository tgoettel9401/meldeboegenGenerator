import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerateDialogComponent } from './generate-dialog.component';

describe('GenerateDialogComponent', () => {
  let component: GenerateDialogComponent;
  let fixture: ComponentFixture<GenerateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenerateDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
