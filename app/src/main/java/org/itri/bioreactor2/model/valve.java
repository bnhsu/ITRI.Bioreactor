package org.itri.bioreactor2.model;

import org.itri.bioreactor2.controller.BioreactorController;

/**
 * Created by norman on 2017/1/6.
 */

public class valve extends actuator {
    public valve(BioreactorController reactor) {
        super(reactor);
    }

    @Override
    public void increase() {

    }

    @Override
    public void decrease() {

    }
}
