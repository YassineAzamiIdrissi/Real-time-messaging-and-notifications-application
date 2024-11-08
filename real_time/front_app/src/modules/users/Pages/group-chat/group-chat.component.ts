import {Component, OnInit} from '@angular/core';
import SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import {TokenService} from "../../../../services/Token/token.service";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {GroupMessageDto} from "../../../../services/models/group-message-dto";
import {GroupRespDto} from "../../../../services/models/group-resp-dto";

@Component({
  selector: 'app-group-chat',
  templateUrl: './group-chat.component.html',
  styleUrl: './group-chat.component.scss'
})
export class GroupChatComponent implements OnInit {
  sockClient: any = null;
  messagesSubscription: any = null;
  currentId: number = 0;
  grpId: number = 0;
  writtenMessage: GroupMessageDto = {}
  page: number = 0;
  size: number = 55;
  content?: Array<GroupMessageDto> = [];
  first?: boolean = false;
  last?: boolean = false;
  totalElements?: number = 0;
  totalPages?: number = 0;
  senderName: string = "";

  groupDetails: GroupRespDto = {};

  constructor(private tokenService: TokenService,
              private activatedRoute: ActivatedRoute,
              private userService: UserService,
              private toasterService: ToastrService,
  ) {
  }

  ngOnInit() {
    const token = this.tokenService.token;
    const payload = this.tokenService.parseTokenData();
    this.grpId = this.activatedRoute.snapshot.params['grpId'];
    this.senderName = payload.fullName;
    this.loadGroupDetails();
    this.loadAllGroupMessages();
    if (token) {
      const ws = new SockJS("http://localhost:8080/api/v1/ws");
      this.currentId = payload.userId;
      this.sockClient = Stomp.over(ws);
      this.sockClient.connect({
        Authorization: `Bearer ${token}`
      }, () => {
        this.messagesSubscription = this.sockClient.subscribe
        (`/user/${this.grpId}/grpChat`,
          (message: any) => {
            const extractedPayload: GroupMessageDto = JSON.parse(message.body);
            this.content?.push(extractedPayload);
          })
      })
    }

  }

  publishMessage() {
    this.writtenMessage.groupId = this.grpId;
    this.writtenMessage.senderId = this.currentId;
    this.writtenMessage.senderName = this.senderName;
    // this.writtenMessage.sentAt = new Date().getTime().toString();
    // const d = new Date();
    // this.writtenMessage.sentAt = d as string;
    this.userService.sendGroupChatMessage(
      {
        body: this.writtenMessage
      }
    ).subscribe({
      next: (resp) => {
        this.writtenMessage.content = "";
      },
      error: (err) => {
        this.toasterService.error("Something went wrong");
        console.log(err);
      }
    });
  }

  loadAllGroupMessages() {
    this.userService.loadGroupDiscussion({
      page: this.page,
      size: this.size,
      groupId: this.grpId
    }).subscribe({
      next: (resp) => {
        this.content = resp.content as Array<GroupMessageDto>;
        this.totalElements = resp.totalElements as number;
        this.totalPages = resp.totalPages as number;
        this.first = resp.first as boolean;
        this.last = resp.last as boolean;
      },
      error: (err) => {
        this.toasterService.error("Something went wrong");
        console.log(err);
      }
    })
  }

  loadGroupDetails() {
    this.userService.getSpecGroup({
      'group-id': this.grpId
    }).subscribe({
      next: (resp) => {
        this.groupDetails = resp;
      },
      error: (err) => {
        this.toasterService.error
        ("Something went wrong ", "Group details");
        console.log(err);
      }
    });
  }

}
