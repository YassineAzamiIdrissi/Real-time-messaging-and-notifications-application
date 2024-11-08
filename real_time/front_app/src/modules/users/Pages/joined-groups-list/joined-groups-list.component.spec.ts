import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JoinedGroupsListComponent } from './joined-groups-list.component';

describe('JoinedGroupsListComponent', () => {
  let component: JoinedGroupsListComponent;
  let fixture: ComponentFixture<JoinedGroupsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JoinedGroupsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JoinedGroupsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
