import {Component, OnInit} from '@angular/core';
import {TokenService} from "../../../../services/Token/token.service";
import {Router} from "@angular/router";
import SockJS from "sockjs-client";
import * as  Stomp from "stompjs";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit {
  username: string = "";
  sockClient: any = null;
  notificationSubscription: any = null;

  constructor(private tokenService: TokenService,
              private router: Router,
              private toasterService: ToastrService) {
  }

  ngOnInit() {
    const payload = this.tokenService.parseTokenData();
    console.log(payload);
    this.username = payload.fullName;
    const userId = payload.userId;
    const token = this.tokenService.token;
    if (token) {
      const ws = new SockJS("http://localhost:8080/api/v1/ws");
      this.sockClient = Stomp.over(ws);
      this.notificationSubscription = this.sockClient.connect(
        {Authorization: `Bearer ${token}`},
        () => {
          this.sockClient.subscribe(`/user/${userId}/notifications`,
            (message: any) => {
              const notif = JSON.parse(message.body);
              switch (notif.type) {
                case 'FRIEND_REQUEST' :
                  this.toasterService.info(notif.content, notif.title);
                  break;
                case 'MESSAGE' :
                  this.toasterService.info(notif.content, notif.title);
                  break;
                case 'FRIEND_REQUEST_ACCEPTED' :
                  this.toasterService.success(notif.content, notif.title);
                  break;
                case 'FRIEND_REQUEST_REFUSED':
                  this.toasterService.warning(notif.content, notif.title);
                  break;
              }
            })
        }
      );
    }
  }

  logout() {
    localStorage.removeItem("token");
    this.router.navigate(["login"]);
  }
}
