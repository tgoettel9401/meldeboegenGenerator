import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Team} from "./models/team";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  baseUri = environment.backendBaseUri;

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

  reloadMivisData(): Observable<void> {
    return this.httpClient.get<void>(this.baseUri + '/api/permissions/reloadMivisData');
  }

  getMivisProgress(): Observable<any> {
    return this.httpClient.get<any>(this.baseUri + '/api/permissions/extractionStatus');
  }

  getMivisPlayerSize(): Observable<number> {
    return this.httpClient.get<number>(this.baseUri + '/api/permissions/mivisPlayerSize');
  }

  loadResultCsv() {
    window.open(this.baseUri + '/api/permissions/resultCsv');
  }
}
