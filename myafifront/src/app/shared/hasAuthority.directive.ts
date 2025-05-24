// has-authority.directive.ts
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthService } from 'app/core/auth/auth.service';

@Directive({
  selector: '[hasAuthority]'
})
export class HasAuthorityDirective {

  private authorities: string[] = [];

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private _authService: AuthService
  ) {
    const utilisateur = this._authService.getUtilisateur();
    this.authorities = utilisateur?.role || [];
  }

  @Input()
  set hasAuthority(requiredAuthority: string | string[]) {
    const requiredAuthorities = Array.isArray(requiredAuthority)
      ? requiredAuthority
      : [requiredAuthority];

    const hasMatch = requiredAuthorities.some(auth =>
      this.authorities.includes(auth)
    );

    if (hasMatch) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }
}
