import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatToolbarModule} from "@angular/material/toolbar";
import { ToolbarComponent } from './toolbar/toolbar.component';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import { TeamsComponent } from './team/teams.component';
import {MatDividerModule} from "@angular/material/divider";
import { TeamPlayersComponent } from './team-players/team-players.component';
import {MatTableModule} from "@angular/material/table";
import {HttpClientModule} from "@angular/common/http";
import {MatDialogModule} from "@angular/material/dialog";
import { GenerateDialogComponent } from './generate-dialog/generate-dialog.component';
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatMenuModule} from '@angular/material/menu';
import {RouterModule} from "@angular/router";
import { PermissionsComponent } from './permissions/permissions.component';
import {AppRoutingModule} from "./app-routing.module";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    TeamsComponent,
    TeamPlayersComponent,
    GenerateDialogComponent,
    PermissionsComponent,
  ],
    imports: [
        HttpClientModule,
        BrowserModule,
        BrowserAnimationsModule,
        MatFormFieldModule,
        MatSidenavModule,
        MatOptionModule,
        MatSelectModule,
        MatToolbarModule,
        MatIconModule,
        MatButtonModule,
        MatCardModule,
        MatDividerModule,
        MatTableModule,
        MatDialogModule,
        MatInputModule,
        MatSelectModule,
        ReactiveFormsModule,
        FormsModule,
        MatMenuModule,
        RouterModule,
        AppRoutingModule,
        MatProgressBarModule,
        MatProgressSpinnerModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
