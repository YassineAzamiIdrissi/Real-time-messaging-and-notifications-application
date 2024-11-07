import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { MenuComponent } from './Components/menu/menu.component';
import { LayoutComponent } from './Layout/layout/layout.component';
import { UsersListComponent } from './Pages/users-list/users-list.component';
import { FriendsListComponent } from './Pages/friends-list/friends-list.component';
import { RequestsListComponent } from './Pages/requests-list/requests-list.component';
import { ConversationComponent } from './Pages/conversation/conversation.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { DiscussionsComponent } from './Pages/discussions/discussions.component';
import { DiscussionCardComponent } from './Components/discussion-card/discussion-card.component';
import { FilterPipe } from './Pages/conversation/filter.pipe';
import { GroupsListComponent } from './Pages/groups-list/groups-list.component';
import { GroupDetailsComponent } from './Pages/group-details/group-details.component';
import { GroupChatComponent } from './Pages/group-chat/group-chat.component';


@NgModule({
  declarations: [
    MenuComponent,
    LayoutComponent,
    UsersListComponent,
    FriendsListComponent,
    RequestsListComponent,
    ConversationComponent,
    DiscussionsComponent,
    DiscussionCardComponent,
    FilterPipe,
    GroupsListComponent,
    GroupDetailsComponent,
    GroupChatComponent
  ],
    imports: [
        CommonModule,
        UsersRoutingModule,
        FormsModule,
        ReactiveFormsModule
    ]
})
export class UsersModule { }
