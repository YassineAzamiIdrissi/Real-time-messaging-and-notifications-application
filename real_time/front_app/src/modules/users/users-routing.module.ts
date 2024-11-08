import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LayoutComponent} from "./Layout/layout/layout.component";
import {UsersListComponent} from "./Pages/users-list/users-list.component";
import {authGuard} from "../../AuthGuard/auth.guard";
import {FriendsListComponent} from "./Pages/friends-list/friends-list.component";
import {RequestsListComponent} from "./Pages/requests-list/requests-list.component";
import {ConversationComponent} from "./Pages/conversation/conversation.component";
import {DiscussionsComponent} from "./Pages/discussions/discussions.component";
import {GroupsListComponent} from "./Pages/groups-list/groups-list.component";
import {GroupDetailsComponent} from "./Pages/group-details/group-details.component";
import {GroupChatComponent} from "./Pages/group-chat/group-chat.component";
import {JoinedGroupsListComponent} from "./Pages/joined-groups-list/joined-groups-list.component";

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
        path: "spec-messages/:userId",
        component: ConversationComponent,
        canActivate: [authGuard]
      },
      {
        path: "discussions",
        component: DiscussionsComponent,
        canActivate: [authGuard]
      },
      {
        path: "groups",
        component: GroupsListComponent,
        canActivate: [authGuard]
      },
      {
        path: "details-group/:groupId",
        component: GroupDetailsComponent,
        canActivate: [authGuard]
      },
      {
        path: "group-chat/:grpId",
        component: GroupChatComponent,
        canActivate: [authGuard]
      },
      {
        path: "joined",
        component: JoinedGroupsListComponent,
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
