import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {Team} from "../models/team";
import {Player} from "../models/player";

@Component({
  selector: 'app-team-players',
  templateUrl: './team-players.component.html',
  styleUrls: ['./team-players.component.css']
})
export class TeamPlayersComponent implements AfterViewInit {

  @Input("team") team!: Team;

  displayedColumns: string[] = ['name', 'elo', 'dwz', 'fideTitle', 'birthday', 'gender', 'ageGroup'];
  dataSource!: MatTableDataSource<Player>;

  ngAfterViewInit() {
    this.updateTable();
  }

  updateTable(): void {
    this.dataSource = new MatTableDataSource<Player>(this.team.players);
  }

}
