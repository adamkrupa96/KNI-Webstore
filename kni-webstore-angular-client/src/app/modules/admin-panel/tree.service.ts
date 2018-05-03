import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';


// Serwis do odświeżania drzewa kategorii
@Injectable()
export class TreeService {

  refreshSubject = new Subject<any>();

  refreshTree() {
    this.refreshSubject.next();
  }

  constructor() { }

}
