/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { acceptRequest } from '../fn/user/accept-request';
import { AcceptRequest$Params } from '../fn/user/accept-request';
import { addGroupMember } from '../fn/user/add-group-member';
import { AddGroupMember$Params } from '../fn/user/add-group-member';
import { addUser } from '../fn/user/add-user';
import { AddUser$Params } from '../fn/user/add-user';
import { createGroup } from '../fn/user/create-group';
import { CreateGroup$Params } from '../fn/user/create-group';
import { deleteConversation } from '../fn/user/delete-conversation';
import { DeleteConversation$Params } from '../fn/user/delete-conversation';
import { deleteUserFromGrp } from '../fn/user/delete-user-from-grp';
import { DeleteUserFromGrp$Params } from '../fn/user/delete-user-from-grp';
import { fetchAllThisUserFriends } from '../fn/user/fetch-all-this-user-friends';
import { FetchAllThisUserFriends$Params } from '../fn/user/fetch-all-this-user-friends';
import { getAllReceivedRequests } from '../fn/user/get-all-received-requests';
import { GetAllReceivedRequests$Params } from '../fn/user/get-all-received-requests';
import { getAllTimeLineUsers } from '../fn/user/get-all-time-line-users';
import { GetAllTimeLineUsers$Params } from '../fn/user/get-all-time-line-users';
import { getGroupMembers } from '../fn/user/get-group-members';
import { GetGroupMembers$Params } from '../fn/user/get-group-members';
import { getLastMessage } from '../fn/user/get-last-message';
import { GetLastMessage$Params } from '../fn/user/get-last-message';
import { getSpecGroup } from '../fn/user/get-spec-group';
import { GetSpecGroup$Params } from '../fn/user/get-spec-group';
import { getSpecificUser } from '../fn/user/get-specific-user';
import { GetSpecificUser$Params } from '../fn/user/get-specific-user';
import { GroupRespDto } from '../models/group-resp-dto';
import { loadConversation } from '../fn/user/load-conversation';
import { LoadConversation$Params } from '../fn/user/load-conversation';
import { loadGroupDiscussion } from '../fn/user/load-group-discussion';
import { LoadGroupDiscussion$Params } from '../fn/user/load-group-discussion';
import { loadJoinedGroups } from '../fn/user/load-joined-groups';
import { LoadJoinedGroups$Params } from '../fn/user/load-joined-groups';
import { MessageDto } from '../models/message-dto';
import { PageResponseFriendRequestRespDto } from '../models/page-response-friend-request-resp-dto';
import { PageResponseGroupMemberRespDto } from '../models/page-response-group-member-resp-dto';
import { PageResponseGroupMessageDto } from '../models/page-response-group-message-dto';
import { PageResponseGroupRespDto } from '../models/page-response-group-resp-dto';
import { PageResponseMessageDto } from '../models/page-response-message-dto';
import { PageResponseUserRespDto } from '../models/page-response-user-resp-dto';
import { readExcludedFriends } from '../fn/user/read-excluded-friends';
import { ReadExcludedFriends$Params } from '../fn/user/read-excluded-friends';
import { realAllConnectedUserGroups } from '../fn/user/real-all-connected-user-groups';
import { RealAllConnectedUserGroups$Params } from '../fn/user/real-all-connected-user-groups';
import { refuseRequest } from '../fn/user/refuse-request';
import { RefuseRequest$Params } from '../fn/user/refuse-request';
import { sendGroupChatMessage } from '../fn/user/send-group-chat-message';
import { SendGroupChatMessage$Params } from '../fn/user/send-group-chat-message';
import { sendMessage } from '../fn/user/send-message';
import { SendMessage$Params } from '../fn/user/send-message';
import { unfriendUser } from '../fn/user/unfriend-user';
import { UnfriendUser$Params } from '../fn/user/unfriend-user';
import { UserRespDto } from '../models/user-resp-dto';

@Injectable({ providedIn: 'root' })
export class UserService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `sendMessage()` */
  static readonly SendMessagePath = '/users/messages/{userId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `sendMessage()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  sendMessage$Response(params: SendMessage$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return sendMessage(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `sendMessage$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  sendMessage(params: SendMessage$Params, context?: HttpContext): Observable<number> {
    return this.sendMessage$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `createGroup()` */
  static readonly CreateGroupPath = '/users/groups';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  createGroup$Response(params: CreateGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return createGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createGroup(params: CreateGroup$Params, context?: HttpContext): Observable<number> {
    return this.createGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `getGroupMembers()` */
  static readonly GetGroupMembersPath = '/users/group/members/{groupId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getGroupMembers()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGroupMembers$Response(params: GetGroupMembers$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseGroupMemberRespDto>> {
    return getGroupMembers(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getGroupMembers$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGroupMembers(params: GetGroupMembers$Params, context?: HttpContext): Observable<PageResponseGroupMemberRespDto> {
    return this.getGroupMembers$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseGroupMemberRespDto>): PageResponseGroupMemberRespDto => r.body)
    );
  }

  /** Path part for operation `addGroupMember()` */
  static readonly AddGroupMemberPath = '/users/group/members/{groupId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addGroupMember()` instead.
   *
   * This method doesn't expect any request body.
   */
  addGroupMember$Response(params: AddGroupMember$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return addGroupMember(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addGroupMember$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  addGroupMember(params: AddGroupMember$Params, context?: HttpContext): Observable<number> {
    return this.addGroupMember$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `sendGroupChatMessage()` */
  static readonly SendGroupChatMessagePath = '/users/group-chat';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `sendGroupChatMessage()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  sendGroupChatMessage$Response(params: SendGroupChatMessage$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return sendGroupChatMessage(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `sendGroupChatMessage$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  sendGroupChatMessage(params: SendGroupChatMessage$Params, context?: HttpContext): Observable<number> {
    return this.sendGroupChatMessage$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `addUser()` */
  static readonly AddUserPath = '/users/add/{userId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  addUser$Response(params: AddUser$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return addUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  addUser(params: AddUser$Params, context?: HttpContext): Observable<number> {
    return this.addUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `refuseRequest()` */
  static readonly RefuseRequestPath = '/users/refuse-req/{req-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `refuseRequest()` instead.
   *
   * This method doesn't expect any request body.
   */
  refuseRequest$Response(params: RefuseRequest$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return refuseRequest(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `refuseRequest$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  refuseRequest(params: RefuseRequest$Params, context?: HttpContext): Observable<number> {
    return this.refuseRequest$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `acceptRequest()` */
  static readonly AcceptRequestPath = '/users/accept-req/{req-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `acceptRequest()` instead.
   *
   * This method doesn't expect any request body.
   */
  acceptRequest$Response(params: AcceptRequest$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return acceptRequest(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `acceptRequest$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  acceptRequest(params: AcceptRequest$Params, context?: HttpContext): Observable<number> {
    return this.acceptRequest$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

  /** Path part for operation `getAllTimeLineUsers()` */
  static readonly GetAllTimeLineUsersPath = '/users';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllTimeLineUsers()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllTimeLineUsers$Response(params?: GetAllTimeLineUsers$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseUserRespDto>> {
    return getAllTimeLineUsers(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllTimeLineUsers$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllTimeLineUsers(params?: GetAllTimeLineUsers$Params, context?: HttpContext): Observable<PageResponseUserRespDto> {
    return this.getAllTimeLineUsers$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseUserRespDto>): PageResponseUserRespDto => r.body)
    );
  }

  /** Path part for operation `getSpecificUser()` */
  static readonly GetSpecificUserPath = '/users/{user-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSpecificUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSpecificUser$Response(params: GetSpecificUser$Params, context?: HttpContext): Observable<StrictHttpResponse<UserRespDto>> {
    return getSpecificUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getSpecificUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSpecificUser(params: GetSpecificUser$Params, context?: HttpContext): Observable<UserRespDto> {
    return this.getSpecificUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<UserRespDto>): UserRespDto => r.body)
    );
  }

  /** Path part for operation `getAllReceivedRequests()` */
  static readonly GetAllReceivedRequestsPath = '/users/reqs';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllReceivedRequests()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllReceivedRequests$Response(params?: GetAllReceivedRequests$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFriendRequestRespDto>> {
    return getAllReceivedRequests(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllReceivedRequests$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllReceivedRequests(params?: GetAllReceivedRequests$Params, context?: HttpContext): Observable<PageResponseFriendRequestRespDto> {
    return this.getAllReceivedRequests$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFriendRequestRespDto>): PageResponseFriendRequestRespDto => r.body)
    );
  }

  /** Path part for operation `getLastMessage()` */
  static readonly GetLastMessagePath = '/users/last-message/{user-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getLastMessage()` instead.
   *
   * This method doesn't expect any request body.
   */
  getLastMessage$Response(params: GetLastMessage$Params, context?: HttpContext): Observable<StrictHttpResponse<MessageDto>> {
    return getLastMessage(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getLastMessage$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getLastMessage(params: GetLastMessage$Params, context?: HttpContext): Observable<MessageDto> {
    return this.getLastMessage$Response(params, context).pipe(
      map((r: StrictHttpResponse<MessageDto>): MessageDto => r.body)
    );
  }

  /** Path part for operation `getSpecGroup()` */
  static readonly GetSpecGroupPath = '/users/groups/{group-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSpecGroup()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSpecGroup$Response(params: GetSpecGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupRespDto>> {
    return getSpecGroup(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getSpecGroup$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSpecGroup(params: GetSpecGroup$Params, context?: HttpContext): Observable<GroupRespDto> {
    return this.getSpecGroup$Response(params, context).pipe(
      map((r: StrictHttpResponse<GroupRespDto>): GroupRespDto => r.body)
    );
  }

  /** Path part for operation `loadJoinedGroups()` */
  static readonly LoadJoinedGroupsPath = '/users/groups/joined';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `loadJoinedGroups()` instead.
   *
   * This method doesn't expect any request body.
   */
  loadJoinedGroups$Response(params?: LoadJoinedGroups$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseGroupRespDto>> {
    return loadJoinedGroups(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `loadJoinedGroups$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  loadJoinedGroups(params?: LoadJoinedGroups$Params, context?: HttpContext): Observable<PageResponseGroupRespDto> {
    return this.loadJoinedGroups$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseGroupRespDto>): PageResponseGroupRespDto => r.body)
    );
  }

  /** Path part for operation `realAllConnectedUserGroups()` */
  static readonly RealAllConnectedUserGroupsPath = '/users/groups/creator';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `realAllConnectedUserGroups()` instead.
   *
   * This method doesn't expect any request body.
   */
  realAllConnectedUserGroups$Response(params?: RealAllConnectedUserGroups$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseGroupRespDto>> {
    return realAllConnectedUserGroups(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `realAllConnectedUserGroups$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  realAllConnectedUserGroups(params?: RealAllConnectedUserGroups$Params, context?: HttpContext): Observable<PageResponseGroupRespDto> {
    return this.realAllConnectedUserGroups$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseGroupRespDto>): PageResponseGroupRespDto => r.body)
    );
  }

  /** Path part for operation `fetchAllThisUserFriends()` */
  static readonly FetchAllThisUserFriendsPath = '/users/friends';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `fetchAllThisUserFriends()` instead.
   *
   * This method doesn't expect any request body.
   */
  fetchAllThisUserFriends$Response(params?: FetchAllThisUserFriends$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseUserRespDto>> {
    return fetchAllThisUserFriends(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `fetchAllThisUserFriends$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  fetchAllThisUserFriends(params?: FetchAllThisUserFriends$Params, context?: HttpContext): Observable<PageResponseUserRespDto> {
    return this.fetchAllThisUserFriends$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseUserRespDto>): PageResponseUserRespDto => r.body)
    );
  }

  /** Path part for operation `readExcludedFriends()` */
  static readonly ReadExcludedFriendsPath = '/users/friends/excluded-grp/{grpId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `readExcludedFriends()` instead.
   *
   * This method doesn't expect any request body.
   */
  readExcludedFriends$Response(params: ReadExcludedFriends$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseUserRespDto>> {
    return readExcludedFriends(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `readExcludedFriends$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  readExcludedFriends(params: ReadExcludedFriends$Params, context?: HttpContext): Observable<PageResponseUserRespDto> {
    return this.readExcludedFriends$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseUserRespDto>): PageResponseUserRespDto => r.body)
    );
  }

  /** Path part for operation `loadConversation()` */
  static readonly LoadConversationPath = '/users/conversations/{user-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `loadConversation()` instead.
   *
   * This method doesn't expect any request body.
   */
  loadConversation$Response(params: LoadConversation$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseMessageDto>> {
    return loadConversation(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `loadConversation$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  loadConversation(params: LoadConversation$Params, context?: HttpContext): Observable<PageResponseMessageDto> {
    return this.loadConversation$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseMessageDto>): PageResponseMessageDto => r.body)
    );
  }

  /** Path part for operation `deleteConversation()` */
  static readonly DeleteConversationPath = '/users/conversations/{user-id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteConversation()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteConversation$Response(params: DeleteConversation$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return deleteConversation(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteConversation$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteConversation(params: DeleteConversation$Params, context?: HttpContext): Observable<{
}> {
    return this.deleteConversation$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `loadGroupDiscussion()` */
  static readonly LoadGroupDiscussionPath = '/users/conversation/group/{groupId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `loadGroupDiscussion()` instead.
   *
   * This method doesn't expect any request body.
   */
  loadGroupDiscussion$Response(params: LoadGroupDiscussion$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseGroupMessageDto>> {
    return loadGroupDiscussion(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `loadGroupDiscussion$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  loadGroupDiscussion(params: LoadGroupDiscussion$Params, context?: HttpContext): Observable<PageResponseGroupMessageDto> {
    return this.loadGroupDiscussion$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseGroupMessageDto>): PageResponseGroupMessageDto => r.body)
    );
  }

  /** Path part for operation `unfriendUser()` */
  static readonly UnfriendUserPath = '/users/unfriend/{userId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `unfriendUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  unfriendUser$Response(params: UnfriendUser$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return unfriendUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `unfriendUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  unfriendUser(params: UnfriendUser$Params, context?: HttpContext): Observable<{
}> {
    return this.unfriendUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `deleteUserFromGrp()` */
  static readonly DeleteUserFromGrpPath = '/users/groups/remove/{userId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteUserFromGrp()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteUserFromGrp$Response(params: DeleteUserFromGrp$Params, context?: HttpContext): Observable<StrictHttpResponse<number>> {
    return deleteUserFromGrp(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteUserFromGrp$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteUserFromGrp(params: DeleteUserFromGrp$Params, context?: HttpContext): Observable<number> {
    return this.deleteUserFromGrp$Response(params, context).pipe(
      map((r: StrictHttpResponse<number>): number => r.body)
    );
  }

}
