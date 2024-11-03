import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {UserRespDto} from "../../../../services/models/user-resp-dto";
import {Router} from "@angular/router";

@Component({
  selector: 'app-friends-list',
  templateUrl: './friends-list.component.html',
  styleUrl: './friends-list.component.scss'
})
export class FriendsListComponent implements OnInit {
  page: number = 0;
  size: number = 5;
  content: Array<UserRespDto> = [];
  first: boolean = false;
  last: boolean = false;
  number: number = 0;
  totalPages: number = 0;

  constructor(private userService: UserService,
              private toasterService: ToastrService,
              private router: Router) {
  }

  ngOnInit() {
    this.fetchAllUserFriends();
  }

  fetchAllUserFriends() {
    this.userService.fetchAllThisUserFriends({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (resp) => {
        this.content = resp.content as Array<UserRespDto>;
        this.first = resp.first as boolean;
        this.last = resp.last as boolean;
        this.number = resp.number as number;
        this.totalPages = resp.totalPages as number;
      },
      error: (err) => {
        this.toasterService.error("Something went wrong !");
        console.log(err);
      }
    })
  }

  unfriend(userId: number | undefined) {
    const grId = userId as number;
    this.userService.unfriendUser(
      {
        userId: grId
      }
    ).subscribe({
      next: () => {
        this.content = this.content.filter(element => {
          return element.id != userId
        });
        this.toasterService.success("User unfriended");
      },
      error: (err) => {
        this.toasterService.error("Something went wrong...");
        console.log(err);
      }
    })
  }

  goToPreviousPage() {
    this.page--;
    this.fetchAllUserFriends();
  }

  goToNextPage() {
    this.page++;
    this.fetchAllUserFriends();
  }

  goToSpecificPage(pageIndex: number) {
    this.page = pageIndex;
    this.fetchAllUserFriends();
  }

  goToLastPage() {
    this.page = this.totalPages as number - 1;
    this.fetchAllUserFriends();
  }

  goToFirstPage() {
    this.page = 0;
    this.fetchAllUserFriends();
  }

  goToMessages(userId: number | undefined) {
    this.router.navigate(["/users/spec-messages", userId]);
  }
}
