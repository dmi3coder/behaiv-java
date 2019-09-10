package de.dmi3y.behaiv.node;

import de.dmi3y.behaiv.Behaiv;

public interface ActionableNode {
    void register(Behaiv behaiv);
    void captureResult(Behaiv behaiv);
}
