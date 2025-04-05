import { formatNumber } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

    @Pipe({
      name: 'numberConverter'
    })
    export class NumberConverterPipe implements PipeTransform {

      transform(nStr?:  any, locale?: string): string {
          if (nStr === '') {
              return '';
          }
          let x, x1, x2, rgx;
          nStr += '';
          x = nStr.split('.');
          x1 = x[0];
          x2 = x[1];
          rgx = /(\d+)(\d{3})/;
          while (rgx.test(x1)) {
              x1 = x1.replace(rgx, '$1' + "  " + '$2');
          }
         // console.log("decimal =============================== {}", x1 + (x2 ? `.${x2}` : ''))
          return x1 + (x2 ? `.${x2}` : '');
      }

    }
