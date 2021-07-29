import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DataService} from "../data.service";
import {Team} from "../models/team";
import {TeamPlayersComponent} from "../team-players/team-players.component";

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements AfterViewInit {

  teams?: Team[];
  fileName?: string;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {

  }

  ngAfterViewInit(): void {
    this.getAllTeamsIncludingPlayers()
  }

  getAllTeamsIncludingPlayers(): void {
    this.dataService.getAll().subscribe(
      data => this.teams = data,
      error => console.log(error),
    );
  }

  deleteData(): void {
    this.dataService.deleteAll().subscribe(
      data => {},
      error => console.log(error),
    )
    this.teams = [];
  }

  reloadData() {
    this.getAllTeamsIncludingPlayers();
  }

  onFileSelected(event: Event) {
    const element = event.currentTarget as HTMLInputElement;
    // @ts-ignore
    let file:File = element.files[0];

    if (file) {
      this.fileName = file.name;
      const formData = new FormData();
      formData.append("file", file);
      this.dataService.uploadFile(formData).subscribe(
        data => {},
        error => console.error(error),
        () => this.reloadData()
      );
    }
  }
}
