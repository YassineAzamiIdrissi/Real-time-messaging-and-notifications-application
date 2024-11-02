import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {FriendRequestRespDto} from "../../../../services/models/friend-request-resp-dto";

@Component({
  selector: 'app-requests-list',
  templateUrl: './requests-list.component.html',
  styleUrl: './requests-list.component.scss'
})
export class RequestsListComponent implements OnInit {
  page: number = 0;
  size: number = 5;
  content: Array<FriendRequestRespDto> = [];
  first: boolean = false;
  last: boolean = false;
  totalPages: number = 0;
  totalElements: number = 0;

  constructor(private userService: UserService,
              private toasterService: ToastrService
  ) {
  }

  ngOnInit() {
    this.fetchUserRequest();
  }

  fetchUserRequest() {
    this.userService.getAllReceivedRequests(
      {
        page: this.page,
        size: this.size
      }).subscribe(
      {
        next: (resp) => {
          this.content = resp.content as Array<FriendRequestRespDto>;
          this.first = resp.first as boolean;
          this.last = resp.last as boolean;
          this.size = resp.size as number;
          this.totalElements = resp.totalElements as number;
          this.totalPages = resp.totalPages as number;
        },
        error: (err) => {
          this.toasterService.error("Something went wrong..check console !");
          console.log(err);
        }
      }
    )
    ;

  }

  goToPreviousPage() {
    this.page--;
    this.fetchUserRequest();
  }

  goToNextPage() {
    this.page++;
    this.fetchUserRequest();
  }

  goToSpecificPage(pageIndex: number) {
    this.page = pageIndex;
    this.fetchUserRequest();
  }

  goToLastPage() {
    this.page = this.totalPages as number - 1;
    this.fetchUserRequest();
  }

  goToFirstPage() {
    this.page = 0;
    this.fetchUserRequest();
  }

  acceptFriendRequest(request: FriendRequestRespDto) {
    const grId = request.id as number;
    this.userService.acceptRequest({
      "req-id": grId
    }).subscribe({
      next: (resp) => {
        this.toasterService.success("Request accepted", "Done");
        request.answered = true;
        request.accepted = true;
      },
      error: (err) => {
        this.toasterService.success
        ("Ooops something went wrong.. check console !");
        console.log(err);
      }
    })
  }

  refuseFriendRequest(request: FriendRequestRespDto) {
    const grId = request.id as number;
    this.userService.refuseRequest({
      'req-id': grId
    }).subscribe({
      next: (resp) => {
        this.toasterService.success("Request refused", "Done");
        request.answered = true;
        request.accepted = false;
      },
      error: (err) => {
        this.toasterService.error("Something went wrong...");
        console.log(err);
      }
    })
  }
}
