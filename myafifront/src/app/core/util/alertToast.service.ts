import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
@Injectable({
    providedIn: 'root'
})
export class AlertToastService {
    constructor(private toast: ToastrService) { }
    toastSuccess(description: string, action: string) {
        this.toast.success(description, action, {
            positionClass: 'toast-top-center',
            progressBar: true,
        });
    }
    toastDanger(description: string, action:string) {
        this.toast.remove;
        this.toast.error(description, action, {
            positionClass: 'toast-top-center',
            progressBar: true,
        });
    }
    toastWarning(description: string, action:string) {
        this.toast.remove;
        this.toast.warning(description, action, {
            positionClass: 'toast-top-center',
            progressBar: true,
        });
    }
}
