package com.covalsys.phi_scanner.utils.rx;

import io.reactivex.Scheduler;
/**
 * Created by CBS on 09-07-2020.
 */
public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
