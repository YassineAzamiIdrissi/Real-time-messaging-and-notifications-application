import {Component, OnInit} from '@angular/core';
import {UserRespDto} from "../../../../services/models/user-resp-dto";
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {MessageDto} from "../../../../services/models/message-dto";
import {TokenService} from "../../../../services/Token/token.service";

@Component({
  selector: 'app-discussions',
  templateUrl: './discussions.component.html',
  styleUrls: ['./discussions.component.scss']
})
export class DiscussionsComponent implements OnInit {
  page: number = 0;
  size: number = 100;
  randImgUrl = "https://picsum.photos/200/300";
  content: Array<UserRespDto> = [];
  id: number = 0;
  currentId: number = 0;
  searchText: any = "";
  lastSentMessageObject: MessageDto = {}
  lastSentMessageObject2: MessageDto = {}

  constructor(private userService: UserService,
              private toasterService: ToastrService,
              private tokenService: TokenService) {
  }

  ngOnInit() {
    const payload = this.tokenService.parseTokenData();
    this.currentId = payload.userId;
    this.fetchUserFriends();
  }

  fetchUserFriends() {
    this.userService.fetchAllThisUserFriends({
      page: this.page,
      size: this.size
    }).subscribe({
      next: (resp) => {
        this.content = resp.content as Array<UserRespDto>;
      },
      error: (err) => {
        this.toasterService.error("Something went wrong " +
          "trying to fetch this user's friends");
        console.error(err);
      }
    });
  }

  switchId(friendId: number | undefined) {
    this.id = friendId as number;
    console.log(this.id);
  }

  updateLastSentMessage(message: MessageDto) {
    console.log("SENT");
    console.log(message);
    this.lastSentMessageObject = message;
  }

  updateLastReceivedMessage(message: MessageDto) {
    console.log("RECEIVED");
    console.log(message);
    this.lastSentMessageObject2 = message;
  }
}
