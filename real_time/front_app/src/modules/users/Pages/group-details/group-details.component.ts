import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {GroupRespDto} from "../../../../services/models/group-resp-dto";
import {PageResponseGroupMemberRespDto} from "../../../../services/models/page-response-group-member-resp-dto";
import {TokenService} from "../../../../services/Token/token.service";

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
  }

  fetchGroupDetails() {
    this.userService.getSpecGroup({
      'group-id': this.groupId
    }).subscribe({
      next: (resp) => {
        this.groupRespDto = resp;
        const payload = this.tokenService.parseTokenData();
        const fullName = payload.fullName;
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
}
