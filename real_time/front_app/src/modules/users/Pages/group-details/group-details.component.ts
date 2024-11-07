import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {GroupRespDto} from "../../../../services/models/group-resp-dto";
import {PageResponseGroupMemberRespDto} from "../../../../services/models/page-response-group-member-resp-dto";
import {TokenService} from "../../../../services/Token/token.service";
import {UserRespDto} from "../../../../services/models/user-resp-dto";

@Component({
  selector: 'app-group-details',
  templateUrl: './group-details.component.html',
  styleUrl: './group-details.component.scss'
})
export class GroupDetailsComponent implements OnInit {
  page: number = 0;
  size: number = 5;
  groupId: number = 0;
  groupRespDto: GroupRespDto = {}
  pageResp: PageResponseGroupMemberRespDto = {}
  isUserAdmin: boolean = false;
  currentUserId: number = 0;
  content?: Array<UserRespDto> = [];
  first?: boolean = false;
  last?: boolean = false;
  totalElements?: number = 0;
  totalPages?: number = 0;

  public constructor(
    private router: Router,
    private userService: UserService,
    private activatedRoute: ActivatedRoute,
    private toasterService: ToastrService,
    private tokenService: TokenService,
  ) {
  }

  ngOnInit() {
    this.groupId = this.activatedRoute.snapshot.params['groupId'];
    this.fetchGroupDetails();
    this.fetchGroupMembers();
    this.fetchUserFriends();
  }

  fetchGroupDetails() {
    this.userService.getSpecGroup({
      'group-id': this.groupId
    }).subscribe({
      next: (resp) => {
        this.groupRespDto = resp;
        const payload = this.tokenService.parseTokenData();
        const fullName = payload.fullName;
        this.currentUserId = payload.userId;
        this.isUserAdmin = (fullName == resp.creator);
      },
      error: (err) => {
        this.toasterService.error("Check console..", "An error occurred");
        console.log(err);
      }
    })
  }

  fetchGroupMembers() {
    this.userService.getGroupMembers({
      groupId: this.groupId,
      page: this.page,
      size: this.size
    }).subscribe(
      {
        next: (resp) => {
          this.pageResp = resp;
        },
        error: (err) => {
          console.log(err);
        }
      }
    )
  }

  fetchUserFriends() {
    this.userService.fetchAllThisUserFriends({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (resp) => {
        this.content = resp.content as Array<UserRespDto>;
        this.first = resp.first as boolean;
        this.last = resp.last as boolean;
        this.totalElements = resp.totalElements as number;
        this.totalPages = resp.totalPages as number;
      },
      error: (err) => {

      }
    });
  }

  addUserToGroup(friendId: number | undefined) {
    this.userService.addGroupMember({
      groupId: this.groupId,
      friendId: friendId as number
    }).subscribe({
      next: (resp) => {
        this.toasterService.success("User added to the group",);
        setTimeout(() => {
          this.router.navigate([0]);
        }, 2000);
      },
      error: (err) => {
        console.log(err);
      }
    })
  }
}
