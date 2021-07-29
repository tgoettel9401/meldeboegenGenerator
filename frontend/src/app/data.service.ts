import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Team} from "./models/team";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  baseUri = 'https://dsj-meldebogen-generator.herokuapp.com';
  //baseUri = '//localhost:8080';

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Team[]> {
    return this.httpClient.get<Team[]>(this.baseUri + '/api/getTeams');
  }

  deleteAll(): Observable<void> {
    return this.httpClient.get<void>(this.baseUri + '/api/deleteData');
  }

  uploadFile(formData: FormData) {
    return this.httpClient.post(this.baseUri + '/importPlayersAndTeams', formData);
  }
}
