import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { MenuComponent } from './Components/menu/menu.component';
import { LayoutComponent } from './Layout/layout/layout.component';
import { UsersListComponent } from './Pages/users-list/users-list.component';
import { FriendsListComponent } from './Pages/friends-list/friends-list.component';
import { RequestsListComponent } from './Pages/requests-list/requests-list.component';


@NgModule({
  declarations: [
    MenuComponent,
    LayoutComponent,
    UsersListComponent,
    FriendsListComponent,
    RequestsListComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule
  ]
})
export class UsersModule { }
