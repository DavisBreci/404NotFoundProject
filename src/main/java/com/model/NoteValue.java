package com.model;

public enum NoteValue {
    SIXTY_FOURTH(new Rational(1, 64)),
    THIRTY_SECOND(new Rational(1, 32)),
    SIXTEENTH(new Rational(1, 16)),
    EIGHTH(new Rational(1, 8)),
    QUARTER(new Rational(1, 4)),
    HALF(new Rational(1, 2)),
    WHOLE(new Rational(1, 1));
    public final Rational duration;
    private NoteValue(Rational duration){
        this.duration = duration;
    }
}