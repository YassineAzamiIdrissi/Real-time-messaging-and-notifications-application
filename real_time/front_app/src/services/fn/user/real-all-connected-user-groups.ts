/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseGroupRespDto } from '../../models/page-response-group-resp-dto';

export interface RealAllConnectedUserGroups$Params {
  page?: number;
  size?: number;
}

export function realAllConnectedUserGroups(http: HttpClient, rootUrl: string, params?: RealAllConnectedUserGroups$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseGroupRespDto>> {
  const rb = new RequestBuilder(rootUrl, realAllConnectedUserGroups.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseGroupRespDto>;
    })
  );
}

realAllConnectedUserGroups.PATH = '/users/groups/creator';
