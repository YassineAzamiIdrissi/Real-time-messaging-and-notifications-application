import {Component, Input, OnInit} from '@angular/core';
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {TokenService} from "../../../../services/Token/token.service";
import {MessageDto} from "../../../../services/models/message-dto";

@Component({
  selector: 'app-discussion-card',
  templateUrl: './discussion-card.component.html',
  styleUrl: './discussion-card.component.scss'
})
export class DiscussionCardComponent implements OnInit {

  @Input()
  lastMessage2: MessageDto | null = {};
  @Input()
  userId: number | undefined = 0;
  @Input()
  image: string = "";

  @Input()
  username: string = "";
  @Input()
  userPicture: string = "";

  currentId: number = 0;

  constructor(private userService: UserService,
              private toasterService: ToastrService,
              private tokenService: TokenService
  ) {
  }

  @Input()
  lastMessage: MessageDto | null = {};
  lastMessageDate: string = "";
  lastMessageText: string = "";

  ngOnInit() {
    const payLoad = this.tokenService.parseTokenData();
    this.currentId = payLoad.userId;
    this.fetchDiscussionLastMessage();
  }

  fetchDiscussionLastMessage() {
    if (this.userId as number >= 1) {
      this.userService.getLastMessage({
        "user-id": this.userId as number
      }).subscribe({
        next: (resp) => {
          if (resp.receiverId == 0) {
            this.lastMessageText = "Start a discussion now !";
          } else {
            const lsMsg = resp.content as string;
            this.lastMessageText = lsMsg.length > 21 ? lsMsg.substring(0, 18) + "..." : lsMsg;
            this.lastMessageDate = resp.sentAt?.substring(0, 10) as string;
          }

        },
        error: (err) => {
          this.toasterService.error("Error 102974288");
          console.log(err);
        }
      })
    }
  }
}
