package fr.jadde.service.util.validator;

public enum ComparableType {
    EQUALS(0),
    GREATER(1),
    LESS(-1);


    private final int valueStatus;

    ComparableType(final int valueStatus) {
        this.valueStatus = valueStatus;
    }

    public int getValueStatus() {
        return this.valueStatus;
    }
}
