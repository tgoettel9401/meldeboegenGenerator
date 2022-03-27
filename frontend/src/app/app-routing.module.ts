import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {TeamsComponent} from "./team/teams.component";
import {PermissionsComponent} from "./permissions/permissions.component";

const routes: Routes = [
  { path: '', component: TeamsComponent },
  { path: 'teams', component: TeamsComponent },
  { path: 'permissions', component: PermissionsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
