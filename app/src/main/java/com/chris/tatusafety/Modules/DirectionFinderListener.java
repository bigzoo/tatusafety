package com.chris.tatusafety.Modules;

import java.util.List;

/**
 * Created by vivian on 02/07/17.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
