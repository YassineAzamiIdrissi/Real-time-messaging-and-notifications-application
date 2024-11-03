import {Component, OnInit} from '@angular/core';
import SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import {TokenService} from "../../../../services/Token/token.service";
import {Message} from "./Message";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.scss'
})
export class ConversationComponent implements OnInit {
  sockClient: any = null;
  messagesSubscription: any = null;

  connectedUserId: number = 0;
  receiverId: number = 0;

  messageContent: string = "";

  sentMessages: Array<Message> = [];

  constructor(private tokenService: TokenService,
              private activatedRoute: ActivatedRoute,
              private userService: UserService,
              private toasterService: ToastrService) {
  }

  ngOnInit(): void {
    const token = this.tokenService.token;
    const payload = this.tokenService.parseTokenData();
    this.receiverId = this.activatedRoute.snapshot.params['userId'];
    const userId = payload.userId;
    this.connectedUserId = userId;
    if (token) {
      const ws = new SockJS("http://localhost:8080/api/v1/ws");
      this.sockClient = Stomp.over(ws);
      this.sockClient.connect({
        Authorization: `Bearer ${token}`
      }, () => {
        this.messagesSubscription = this.sockClient.subscribe
        (`/user/${userId}/messages`,
          (message: any) => {
            const extractedPayload = JSON.parse(message.body);
            this.sentMessages.push(extractedPayload);
          })
      })
    }
  }

  sendMessage() {
    const messageObject: Message = {
      senderId: this.connectedUserId,
      receiverId: this.receiverId,
      content: this.messageContent
    }
    this.userService.sendMessage({
      userId: this.receiverId,
      body: messageObject
    }).subscribe({
      next: (resp) => {
        this.sentMessages.push(messageObject);
        this.messageContent = "";
        "Message " + messageObject.content + " is sent to user with id " +
        this.receiverId;
      },
      error: (err) => {
        this.toasterService.error("Something went wrong !");
        console.log(err);
      }
    })
  }

  loadUserById() {
    
  }
}
