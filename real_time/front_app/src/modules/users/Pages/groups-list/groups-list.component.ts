import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {GroupRespDto} from "../../../../services/models/group-resp-dto";
import {Router} from "@angular/router";

@Component({
  selector: 'app-groups-list',
  templateUrl: './groups-list.component.html',
  styleUrl: './groups-list.component.scss'
})
export class GroupsListComponent implements OnInit {
  page: number = 0;
  size: number = 5;

  content: Array<GroupRespDto> = [];
  first: boolean = false;
  last: boolean = false;
  totalElements?: number = 0;
  totalPages: number = 0;

  grpName: string = "";

  constructor(private userService: UserService,
              private toasterService: ToastrService,
              private router: Router) {
  }

  ngOnInit() {
    this.readThisUserGroups();
  }

  createGroup() {
    this.userService.createGroup(
      {
        groupName: this.grpName
      }
    ).subscribe({
      next: (resp) => {
        this.toasterService.success
        ("Group " + this.grpName + " created", "Done");
        const newGroup: GroupRespDto = {
          members: 1,
          name: this.grpName
        }
        this.content.push(newGroup);
      },
      error: (err) => {
        this.toasterService.error("Something went wrong..check it up !");
        console.log(err);
      }
    });
  }

  readThisUserGroups() {
    this.userService.realAllConnectedUserGroups(
      {
        page: this.page,
        size: this.size
      }
    ).subscribe(
      {
        next: (resp) => {
          this.content = resp.content as Array<GroupRespDto>;
          this.first = resp.first as boolean;
          this.last = resp.last as boolean;
          this.totalPages = resp.totalPages as number;
          this.totalElements = resp.totalElements as number;
        },
        error: (err) => {
          console.log(err);
          this.toasterService.error("Something went wrong !");
        }
      }
    )
  }

  navigateToDetails(groId: number | undefined) {
    this.router.navigate(["/users/details-group"]);
  }
}
