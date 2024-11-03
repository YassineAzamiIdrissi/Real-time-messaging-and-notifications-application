/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseMessageDto } from '../../models/page-response-message-dto';

export interface LoadConversation$Params {
  'user-id': number;
  page?: number;
  size?: number;
}

export function loadConversation(http: HttpClient, rootUrl: string, params: LoadConversation$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseMessageDto>> {
  const rb = new RequestBuilder(rootUrl, loadConversation.PATH, 'get');
  if (params) {
    rb.path('user-id', params['user-id'], {});
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseMessageDto>;
    })
  );
}

loadConversation.PATH = '/users/conversations/{user-id}';
