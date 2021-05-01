package model.grid;

import model.MethodData;
import model.VariableData;
import model.point.ScaledPoint;

import java.util.List;

public interface BoxObservable {
    ScaledPoint getPosition();
    ScaledPoint getAreaOuterBound();

    List<MethodData> getMethodData();
    List<VariableData> getVariableData();
}
