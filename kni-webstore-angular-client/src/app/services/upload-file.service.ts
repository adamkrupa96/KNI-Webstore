import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from './authentication.service';

@Injectable()
export class UploadFileService {

  restURL = 'http://localhost:8080/api/images';

  private headers = new HttpHeaders({
    'Authorization': 'Bearer ' + this.authService.getToken()
  });

  constructor(private http: HttpClient, private authService: AuthenticationService) { }

  pushFileToStorage(restURL: string, file: File, entityID: number): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);
    const params = new HttpParams()
        .set('entityId', `${entityID}`);

    const req = new HttpRequest('POST', restURL, formdata, {
      headers: this.headers,
      reportProgress: true,
      responseType: 'text',
      params: params
    });

    return this.http.request(req);
  }

  getFiles(): Observable<any> {
    return this.http.get(this.restURL);
  }
}
