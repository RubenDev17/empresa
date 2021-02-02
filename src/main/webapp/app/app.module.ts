import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { EmpresaSharedModule } from 'app/shared/shared.module';
import { EmpresaCoreModule } from 'app/core/core.module';
import { EmpresaAppRoutingModule } from './app-routing.module';
import { EmpresaHomeModule } from './home/home.module';
import { EmpresaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    EmpresaSharedModule,
    EmpresaCoreModule,
    EmpresaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    EmpresaEntityModule,
    EmpresaAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class EmpresaAppModule {}
