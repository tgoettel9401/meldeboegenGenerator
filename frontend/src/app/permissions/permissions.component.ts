import { Component, OnInit } from '@angular/core';
import {DataService} from "../data.service";
import {interval} from "rxjs";

@Component({
  selector: 'app-permissions',
  templateUrl: './permissions.component.html',
  styleUrls: ['./permissions.component.css']
})
export class PermissionsComponent implements OnInit {

  isRunning = false;
  totalProcesses = 17;
  loadedProcesses = 0;
  numberOfPlayers = 0;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.updatePlayerNumber();
  }

  reloadMivisData() {
    this.isRunning = true;
    let subsription = interval(1000).subscribe(
      () => this.dataService.getMivisProgress().subscribe(
        data => this.loadedProcesses = data[0].size == null ? 0 : data[0].size,
      )
    )
    this.dataService.reloadMivisData().subscribe(
      () => this.isRunning = false,
      error => {
        console.log(error);
        subsription.unsubscribe();
      },
      () => {
        this.updatePlayerNumber();
        subsription.unsubscribe();
      }
    );
  }

  getPercentage() {
    return this.loadedProcesses / this.totalProcesses;
  }

  updatePlayerNumber() {
    this.dataService.getMivisPlayerSize().subscribe(
      data => this.numberOfPlayers = data,
    )
  }

  loadResultCsv() {
    this.dataService.loadResultCsv();
  }

}
