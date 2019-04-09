package stattracker;

import lombok.Getter;

public enum Place {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    @Getter
    private int placeNum;

    Place(int placeNum) {
        this.placeNum = placeNum;
    }
}
