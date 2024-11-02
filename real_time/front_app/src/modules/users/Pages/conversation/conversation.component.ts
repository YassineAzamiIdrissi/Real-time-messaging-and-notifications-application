import {Component} from '@angular/core';
import {Message} from "./Message";

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrl: './conversation.component.scss'
})
export class ConversationComponent {
  userId = 1;
  receiverId = 2;
  msg: string = "";
  resp: string = "";
  conversation: Array<Message> = [];

  sendMessage() {
    const message: Message = {
      sender: this.userId,
      receiver: this.receiverId,
      content: this.msg,
    }
    this.conversation.push(message);
    this.msg = "";
  }

  answerMessage() {
    const message: Message = {
      sender: this.receiverId,
      receiver: this.userId,
      content: this.resp
    }
    this.conversation.push(message);
    this.resp = "";
  }
}
