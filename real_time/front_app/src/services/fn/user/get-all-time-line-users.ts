/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseUserRespDto } from '../../models/page-response-user-resp-dto';

export interface GetAllTimeLineUsers$Params {
  page?: number;
  size?: number;
}

export function getAllTimeLineUsers(http: HttpClient, rootUrl: string, params?: GetAllTimeLineUsers$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseUserRespDto>> {
  const rb = new RequestBuilder(rootUrl, getAllTimeLineUsers.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseUserRespDto>;
    })
  );
}

getAllTimeLineUsers.PATH = '/users';
