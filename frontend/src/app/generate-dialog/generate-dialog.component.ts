import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl} from "@angular/forms";

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

  baseUri = "https://dsj-meldebogen-generator.herokuapp.com";
  //baseUri = "//localhost:8080";

  typeControl = new FormControl('primary');
  titleControl = new FormControl('primary');

  type = 'DVM';
  title = '';

  constructor(
    public dialogRef: MatDialogRef<GenerateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOkClick() {
    console.log('Type is ' + this.type + ' and title is ' + this.title);
    if (this.type == 'dlm') {
      window.open(this.baseUri + '/generateAnreiseBogen');
    } else {
      window.open(this.baseUri + '/generateAnreiseBogenDvm?title=' + this.title);
    }
  }
}
