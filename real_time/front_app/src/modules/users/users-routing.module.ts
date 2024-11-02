import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LayoutComponent} from "./Layout/layout/layout.component";
import {UsersListComponent} from "./Pages/users-list/users-list.component";
import {authGuard} from "../../AuthGuard/auth.guard";
import {FriendsListComponent} from "./Pages/friends-list/friends-list.component";
import {RequestsListComponent} from "./Pages/requests-list/requests-list.component";
import {ConversationComponent} from "./Pages/conversation/conversation.component";

const routes: Routes = [
  {
    path: "",
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: "",
        component: UsersListComponent,
        canActivate: [authGuard]
      },
      {
        path: "friends",
        component: FriendsListComponent,
        canActivate: [authGuard]
      },
      {
        path: "requests",
        component: RequestsListComponent,
        canActivate: [authGuard]
      },
      {
        path: "spec-messages",
        component: ConversationComponent,
        canActivate: [authGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule {
}
