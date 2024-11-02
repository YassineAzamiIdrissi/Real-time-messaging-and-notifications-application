import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../../../services/services/user.service";
import {PageResponseUserRespDto} from "../../../../services/models/page-response-user-resp-dto";
import {ToastrService} from "ngx-toastr";
import {UserRespDto} from "../../../../services/models/user-resp-dto";

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit {

  users: PageResponseUserRespDto = {};
  usersContent: Array<UserRespDto> = [];
  page: number = 0;
  size: number = 5;
  first: boolean = false;
  last: boolean = false;
  number: number = 0;
  totalPages: number = 0;

  constructor(private router: Router,
              private userService: UserService,
              private toasterService: ToastrService) {
  }

  ngOnInit() {
    this.fetchAllUsers();
  }

  fetchAllUsers() {
    this.userService.getAllTimeLineUsers({
      page: this.page,
      size: this.size
    }).subscribe(
      {
        next: (resp) => {
          this.usersContent = resp.content as Array<UserRespDto>;
          this.first = resp.first as boolean;
          this.last = resp.last as boolean;
          this.number = resp.number as number;
          this.totalPages = resp.totalPages as number;
        },
        error: (err) => {
          console.log(err);
        }
      }
    )
  }

  addFriend(userId: number | undefined) {
    const grId = userId as number;
    this.userService.addUser({
      "userId": grId
    }).subscribe({
      next: (resp) => {
        this.toasterService.info("Request sent successfully");
        this.usersContent = this.usersContent.filter((element) => {
          return element.id != userId;
        });
      },
      error: (err) => {
        this.toasterService.error("Something went wrong.. try again later");
        console.log(err);
      }
    })
  }

  goToPreviousPage() {
    this.page--;
    this.fetchAllUsers();
  }

  goToNextPage() {
    this.page++;
    this.fetchAllUsers();
  }

  goToSpecificPage(pageIndex: number) {
    this.page = pageIndex;
    this.fetchAllUsers();
  }

  goToLastPage() {
    this.page = this.totalPages as number - 1;
    this.fetchAllUsers();
  }

  goToFirstPage() {
    this.page = 0;
    this.fetchAllUsers();
  }
}
