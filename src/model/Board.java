package model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static model.GameStatusEnum.*;

public class Board {
    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        if (spaces.stream().flatMap(Collection::stream).noneMatch(space -> !space.isFixed() && nonNull(space.getActual()))) {
            return NON_STARTED;
        }
        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(space -> isNull(space.getActual())) ? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors() {
        if(getStatus() == NON_STARTED) {
            return false;
        }

        return spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(space -> nonNull(space.getActual()) && space.getActual().equals(space.getExpected()));
    }

    public boolean changeValue(final int col, final int row, final int value) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row) {
        var space = spaces.get(col).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(lista -> lista.forEach(space -> space.clearSpace()));
    }

    public boolean gameIsFinished() {
        return !hasErrors() && getStatus() == COMPLETE;
    }

}
