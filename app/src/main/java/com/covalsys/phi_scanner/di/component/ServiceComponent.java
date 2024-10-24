package com.covalsys.phi_scanner.di.component;

import com.covalsys.phi_scanner.di.PerService;
import com.covalsys.phi_scanner.di.module.ServiceModule;

import dagger.Component;

/**
 * Created by Prasanth on 09-07-2020.
 */
@PerService
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

   //void inject(LocationService service);
}
