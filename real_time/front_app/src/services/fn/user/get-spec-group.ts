/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { GroupRespDto } from '../../models/group-resp-dto';

export interface GetSpecGroup$Params {
  'group-id': number;
}

export function getSpecGroup(http: HttpClient, rootUrl: string, params: GetSpecGroup$Params, context?: HttpContext): Observable<StrictHttpResponse<GroupRespDto>> {
  const rb = new RequestBuilder(rootUrl, getSpecGroup.PATH, 'get');
  if (params) {
    rb.path('group-id', params['group-id'], {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<GroupRespDto>;
    })
  );
}

getSpecGroup.PATH = '/users/groups/{group-id}';
