import {Injectable} from '@angular/core';
import {Pager} from '../models/Pager';

@Injectable()
export class PagerService {

  createPager(totalItems: number, currentPage: number, pageSize: number): Pager {
    // calculate total pages
    let totalPages = Math.ceil(totalItems / pageSize);

    let startPage: number, endPage: number;
    if (totalPages <= 5) {
      // less than 10 total pages so show all
      startPage = 1;
      endPage = totalPages;
    } else {
      // more than 10 total pages so calculate start and end pages
      if (currentPage <= 3) {
        startPage = 1;
        endPage = 5;
      } else if (currentPage + 2 >= totalPages) {
        startPage = totalPages - 4;
        endPage = totalPages;
      } else {
        startPage = currentPage - 3;
        endPage = currentPage + 2;
      }
    }

    // calculate start and end item indexes
    let startIndex = (totalPages - currentPage) * pageSize;
    let endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);

    // create an array of pages to ng-repeat in the pager control
    let pages: number[] = [];
    for (let i = startPage; i < endPage + 1; i++) {
      pages.push(i);
    }

    // return object with all pager properties required by the view
    return new Pager(currentPage, totalPages, startIndex, endIndex, pages);
  }
}
