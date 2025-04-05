import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthService } from 'app/core/auth/auth.service';

@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective {
  private currentRole: string = '';

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private authService: AuthService
  ) {
    this.authService.getUtilisateur().subscribe(user => {
      this.currentRole = user.role;
    });
  }

  @Input() set appHasRole(role: string) {
    if (this.currentRole === role) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
