import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-path-navigation',
  templateUrl: './path-navigation.component.html',
  styleUrls: ['./path-navigation.component.css']
})
export class PathNavigationComponent implements OnInit {
  private routeParams: string[] = [];
  public paths: NavPath[] = [];

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.getParamsFromRoute();
    this.createPathsFromRouteParams();
  }

  getParamsFromRoute() {
    this.route.pathFromRoot.forEach(path => {
      path.snapshot.url.forEach(elemRoute => {
        this.routeParams.push(elemRoute.path);
      });
    });
  }

  createPathsFromRouteParams() {
    this.routeParams.forEach((routeParam, index) => {
      let path = '';
      for (let i = 0; i <= index; i++) {
        path += this.routeParams[i] + '/';
      }

      const navPath = this.createNavPathElem(routeParam, path);
      this.paths.push(navPath);
    });
  }

  createNavPathElem(routeParam: string, path: string): NavPath {
    const navPath: NavPath = {
      name: routeParam,
      value: path
    };
    return navPath;
  }
}

export interface NavPath {
  name: string;
  value: string;
}
