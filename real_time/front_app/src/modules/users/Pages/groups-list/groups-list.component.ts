import {Component} from '@angular/core';
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-groups-list',
  templateUrl: './groups-list.component.html',
  styleUrl: './groups-list.component.scss'
})
export class GroupsListComponent {
  constructor(private userService: UserService,
              private toasterService: ToastrService) {
  }

  grpName: string = "";

  createGroup() {
    this.userService.createGroup(
      {
        groupName: this.grpName
      }
    ).subscribe({
      next: (resp) => {
        this.toasterService.success
        ("Group " + this.grpName + " created", "Done")
      },
      error: (err) => {
        this.toasterService.error("Something went wrong..check it up !");
        console.log(err);
      }
    });
  }
}
