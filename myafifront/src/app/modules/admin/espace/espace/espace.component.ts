import { Component, OnInit } from '@angular/core';
import { AuthService } from 'app/core/auth/auth.service';
import { AdminService } from '../../user/service/admin.service';

@Component({
  selector: 'app-espace',
  templateUrl: './espace.component.html',
  styleUrls: ['./espace.component.css']
})
export class EspaceComponent implements OnInit {

  etudiant: any;

  constructor(
    private _authService: AuthService,
    private _etudiantService: AdminService
  ) { }

  ngOnInit(): void {
    this.getEtudiant()
  }

  getEtudiant() {
        return this._etudiantService
            .get(this._authService.getUtilisateur().id)
            .subscribe((data) => {
                console.log(
                    '@@@@@@@@@@@@@@@@@@@@@@@@ etudiant one  @@@@@@@@@@@@',
                    data
                );
                this.etudiant = data;
            });
    }



}
