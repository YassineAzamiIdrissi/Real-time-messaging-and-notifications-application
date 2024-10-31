import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckRecoveryComponent } from './check-recovery.component';

describe('CheckRecoveryComponent', () => {
  let component: CheckRecoveryComponent;
  let fixture: ComponentFixture<CheckRecoveryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CheckRecoveryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CheckRecoveryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
