/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { CahierTexteComponent } from './cahier-texte.component';

describe('CahierTexteComponent', () => {
  let component: CahierTexteComponent;
  let fixture: ComponentFixture<CahierTexteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CahierTexteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CahierTexteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
