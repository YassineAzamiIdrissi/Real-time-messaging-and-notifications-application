import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandPasswordComponent } from './demand-password.component';

describe('DemandPasswordComponent', () => {
  let component: DemandPasswordComponent;
  let fixture: ComponentFixture<DemandPasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DemandPasswordComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DemandPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
