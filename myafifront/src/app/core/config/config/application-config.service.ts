import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ApplicationConfigService {
  private endpointPrefix = environment?.backend;

  setEndpointPrefix(endpointPrefix: string): void {
    this.endpointPrefix = endpointPrefix;
  }

  getEndpointFor(api: string): string {
    return `${this.endpointPrefix}${api}`;
  }

  getEndpointForAuth(api: string): string {
    return `${this.endpointPrefix}${api}`;
  }
  
}
