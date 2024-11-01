import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../../../../services/services/user.service";
import {PageResponseUserRespDto} from "../../../../services/models/page-response-user-resp-dto";

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrl: './users-list.component.scss'
})
export class UsersListComponent implements OnInit {

  users: PageResponseUserRespDto = {};
  page: number = 0;
  size: number = 5;

  constructor(private router: Router,
              private userService: UserService) {
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
          this.users = resp;
        },
        error: (err) => {
          console.log(err);
        }
      }
    )
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
    this.page = this.users.totalPages as number - 1;
    this.fetchAllUsers();
  }

  goToFirstPage() {
    this.page = 0;
    this.fetchAllUsers();
  }
}
