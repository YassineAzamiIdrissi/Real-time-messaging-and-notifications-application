import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {ActivateAccountComponent} from './pages/activate-account/activate-account.component';
import {DemandPasswordComponent} from './pages/demand-password/demand-password.component';
import {CheckRecoveryComponent} from './pages/check-recovery/check-recovery.component';
import {SetNewPasswordComponent} from './pages/set-new-password/set-new-password.component';
import {provideHttpClient, withInterceptors, withInterceptorsFromDi} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {CodeInputModule} from "angular-code-input";
import {tokenInterceptor} from "../services/interceptors/token.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ActivateAccountComponent,
    DemandPasswordComponent,
    CheckRecoveryComponent,
    SetNewPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    CodeInputModule
  ],
  providers: [
    provideHttpClient(withInterceptorsFromDi(),
      withInterceptors([tokenInterceptor]))
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

}
