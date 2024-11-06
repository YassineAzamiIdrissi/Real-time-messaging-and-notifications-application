import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(value: any[], searchText: string): any {
    if (!value || !searchText) return value;

    const lowerCaseSearchText = searchText.toLowerCase();
    return value.filter(item => {
      const fullName = (item.firstname + ' ' + item.lastname).toLowerCase();
      return fullName.includes(lowerCaseSearchText);
    });
  }


}
