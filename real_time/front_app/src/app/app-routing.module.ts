import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {ActivateAccountComponent} from "./pages/activate-account/activate-account.component";
import {DemandPasswordComponent} from "./pages/demand-password/demand-password.component";
import {CheckRecoveryComponent} from "./pages/check-recovery/check-recovery.component";
import {SetNewPasswordComponent} from "./pages/set-new-password/set-new-password.component";

const routes: Routes = [
  {
    path: "",
    redirectTo: "login",
    pathMatch: "full"
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path: "activate-account",
    component: ActivateAccountComponent
  },
  {
    path: "demand-recovery",
    component: DemandPasswordComponent
  },
  {
    path: "check-code/:email",
    component: CheckRecoveryComponent,
  },
  {
    path: "set-new-password",
    component: SetNewPasswordComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
