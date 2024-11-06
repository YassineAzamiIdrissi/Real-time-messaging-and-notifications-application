import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import {TokenService} from "../../../../services/Token/token.service";
import {Message} from "./Message";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../../../../services/services/user.service";
import {ToastrService} from "ngx-toastr";
import {UserRespDto} from "../../../../services/models/user-resp-dto";
import {PageResponseMessageDto} from "../../../../services/models/page-response-message-dto";
import {MessageDto} from "../../../../services/models/message-dto";
import moment from "moment";


@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.scss'
})
export class ConversationComponent implements OnInit, OnChanges {
  sockClient: any = null;
  messagesSubscription: any = null;
  isDeletable: boolean = false;
  connectedUserId: number = 0;
  @Input()
  toId: number = 0;
  receiverId: number = 0;
  receiver: UserRespDto = {}
  messageContent: string = "";
  fetchedMessages: PageResponseMessageDto = {}
  sentMessages: Array<Message> = [];
  receivedMessageObject: MessageDto = {};
  @Output()
  messageEmitter: EventEmitter<MessageDto> = new EventEmitter();
  @Output()
  receivedMessageEmitter: EventEmitter<MessageDto> = new EventEmitter();

  constructor(private tokenService: TokenService,
              private activatedRoute: ActivatedRoute,
              private userService: UserService,
              private toasterService: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
    const token = this.tokenService.token;
    const payload = this.tokenService.parseTokenData();
    if (this.toId == 0) {
      this.receiverId = this.activatedRoute.snapshot.params['userId'];
    } else {
      this.receiverId = this.toId;
    }
    this.loadUserById();
    this.isConversationDeletable();
    this.loadDiscussion();
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
            const extractedPayload: MessageDto = JSON.parse(message.body);
            this.sentMessages.push(extractedPayload);
            if (this.receivedMessageObject.receiverId as number >= 1) {
              console.log("Depicting....");
              console.log(extractedPayload);
              this.receivedMessageEmitter.emit(extractedPayload);
            }
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
    this.receivedMessageObject = messageObject;
    this.userService.sendMessage({
      userId: this.receiverId,
      body: messageObject
    }).subscribe({
      next: (resp) => {
        this.sentMessages.push(messageObject);
        this.messageContent = "";
        this.isDeletable = true;
        this.messageEmitter.emit(messageObject);
      },
      error: (err) => {
        this.toasterService.error("Something went wrong !");
        console.log(err);
      }
    })
  }

  loadUserById() {
    this.userService.getSpecificUser({
      'user-id': this.receiverId
    }).subscribe({
      next: (resp) => {
        this.receiver = resp;
      },
      error: (err) => {
        this.toasterService.error("Can't fetch this user !!!");
        console.log(err);
      }
    })
  }

  loadDiscussion() {
    this.userService.loadConversation({
      'user-id': this.receiverId,
      page: 0,
      size: 100
    }).subscribe({
      next: (resp) => {
        this.fetchedMessages = resp;
        if (this.fetchedMessages.content) {
          this.sentMessages = this.fetchedMessages.content as Array<MessageDto>;
        }
      },
      error: (error) => {

      }
    })
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['toId'] && !changes['toId'].isFirstChange()) {
      this.receiverId = this.toId;
      this.loadUserById();
      this.loadDiscussion();
    }
  }

  isConversationDeletable() {
    this.userService.getLastMessage(
      {
        'user-id': this.toId
      }
    ).subscribe({
      next: (resp) => {
        const witness = resp.senderId as number;
        if (witness != 0) {
          this.isDeletable = true;
        }
      },
      error: () => {
        this.toasterService.error("Something went wrong 5786750358");
      }
    })
  }

  deleteDiscussion() {
    this.userService.deleteConversation({
      'user-id': this.toId
    }).subscribe({
      next: (resp) => {
        this.router.navigate([0]);
      },
      error: (err) => {
        this.toasterService.error("Something went again wrong =D ... " +
          "708765746355678");
        console.log(err);
      }
    })
  }

  protected readonly moment = moment;
}
