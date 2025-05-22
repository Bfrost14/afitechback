import { CalendarDateFormatter, DateFormatterParams, DateAdapter } from 'angular-calendar';
import { Injectable } from '@angular/core';
import { DatePipe } from '@angular/common';

@Injectable()
export class CustomDateFormatter extends CalendarDateFormatter {
  constructor(private datePipe: DatePipe, dateAdapter: DateAdapter) {
    super(dateAdapter);
  }

  public override weekViewHour({ date, locale }: DateFormatterParams): string {
    return this.datePipe.transform(date, 'HH:mm', undefined, locale) ?? '';
  }

  public override dayViewHour({ date, locale }: DateFormatterParams): string {
    return this.datePipe.transform(date, 'HH:mm', undefined, locale) ?? '';
  }
}
