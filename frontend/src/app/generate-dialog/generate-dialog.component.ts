import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {environment} from "../../environments/environment";

export interface DialogData {
  type: string;
  title: string;
}

@Component({
  selector: 'app-generate-dialog',
  templateUrl: './generate-dialog.component.html',
  styleUrls: ['./generate-dialog.component.css']
})
export class GenerateDialogComponent implements OnInit {

  baseUri = environment.backendBaseUri;

  typeControl = new FormControl('primary');
  titleControl = new FormControl('primary');
  purposeControl = new FormControl('primary');

  formGroup = new FormGroup({
    type: this.typeControl,
    title: this.titleControl,
    purpose: this.purposeControl,
  });

  type = 'DVM';
  title = '';
  purpose = '';

  constructor(
    public dialogRef: MatDialogRef<GenerateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  ngOnInit(): void {
    this.titleControl.markAsTouched();
    this.purposeControl.markAsTouched();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOkClick() {

    // Generate bogen if form is valid, otherwise do nothing.
    if (this.formGroup.valid) {
      if (this.type == 'dlm') {
        window.open(this.baseUri + this.purpose == 'anreise' ? '/generateAnreiseBogen' : '/generateRundeBogen');
      } else {
        window.open(this.baseUri + '/generateAnreiseBogenDvm?title=' + this.title);
      }
      this.dialogRef.close();
    }

  }
}
