import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {GroupRespDto} from "../../../../services/models/group-resp-dto";
import {PageResponseGroupMemberRespDto} from "../../../../services/models/page-response-group-member-resp-dto";
import {TokenService} from "../../../../services/Token/token.service";
import {UserRespDto} from "../../../../services/models/user-resp-dto";
import {GroupMemberRespDto} from "../../../../services/models/group-member-resp-dto";

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
    this.userService.readExcludedFriends({
      grpId: this.groupId,
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
        this.toasterService.error("Check console", "Fetching Friends failed");
        console.log(err);
      }
    });
  }

  addUserToGroup(friend: UserRespDto | undefined) {
    const grpMember: GroupMemberRespDto = {}
    grpMember.member = friend?.firstname + " " + friend?.lastname;
    grpMember.status = "MEMBER";
    grpMember.joinedAt = new Date().toDateString();
    grpMember.admin = false;
    this.userService.addGroupMember({
      groupId: this.groupId,
      friendId: friend?.id as number
    }).subscribe({
      next: (resp) => {
        this.toasterService.success("User added to the group");
        if (this.pageResp.content) {
          this.pageResp.content.unshift(grpMember);
        }
        if (friend) {
          friend.belongsToGroup = true;
        }
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

  kickMember(memberId: number | undefined) {
    this.userService.deleteUserFromGrp(
      {
        userId: memberId as number,
        grpId: this.groupId
      }
    ).subscribe({
      next: (resp) => {
        if (this.pageResp.content) {
          this.pageResp.content = this.pageResp.content?.filter(
            (member) => member.id != memberId
          );
        }

      },
      error: (err) => {
        this.toasterService.error("Unable to kick member");
        console.log(err);
      }
    })
  }
}
