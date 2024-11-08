import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {GroupRespDto} from "../../../../services/models/group-resp-dto";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-joined-groups-list',
  templateUrl: './joined-groups-list.component.html',
  styleUrl: './joined-groups-list.component.scss'
})
export class JoinedGroupsListComponent implements OnInit {
  page: number = 0;
  size: number = 5;

  content?: Array<GroupRespDto>;
  first?: boolean;
  last?: boolean;
  totalElements?: number;
  totalPages?: number;

  constructor(private userService: UserService,
              private toasterService: ToastrService,
              private router: Router) {
  }

  ngOnInit() {
    this.loadJoinedGroups();
  }

  loadJoinedGroups() {
    this.userService.loadJoinedGroups(
      {
        page: this.page,
        size: this.size
      }).subscribe(
      {
        next: (resp) => {
          this.content = resp.content as Array<GroupRespDto>;
          this.first = resp.first as boolean;
          this.last = resp.last as boolean;
          this.totalElements = resp.totalElements as number;
          this.totalPages = resp.totalPages as number;
        },
        error: (err) => {
          this.toasterService.error("Some sort of error ...");
          console.log(err);
        }
      }
    );
  }

  goToGroupMessages(grpId: number | undefined) {
    this.router.navigate(["/users/group-chat", grpId]);
  }

  moveToDetails(grpId: number | undefined) {
    this.router.navigate(["/users/details-group", grpId]);
  }
}
