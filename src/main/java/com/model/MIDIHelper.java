package com.model;

import javax.sound.midi.MidiEvent;

/**
 * Helper class with methods and constants useful for parsing MIDI files
 */
public final class MIDIHelper {
    public static final int MIDI_NOTE_RANGE = 129;
    public static final int VALID_NOTE_VALUES = NoteValue.values().length;
    public static final int WHOLE_NOTE_NUMERATOR = (int)Math.pow(2, VALID_NOTE_VALUES - 1);
    public static final int NOTE_ON_1 = 144;
    public static final int NOTE_ON_16 = 159;
    public static final int NOTE_OFF_1 = 128;
    public static final int NOTE_OFF_16 = 143;
    public static final byte META = (byte)255;
    public static final byte TIME_SIGNATURE = (byte)88;
    public static final byte CHAN_VOL_MSB = 0x07;
    public static final byte CHAN_VOL_LSB = 0x27;
    public static final int MAX_VOL = 16383;
    public static final int TEMPO = 0x51;

    public static Rational midiQuantize(MidiEvent a, MidiEvent b, int resolution) {
        return Rational.quantize(
                (double)(b.getTick() - a.getTick())/(4 * resolution), VALID_NOTE_VALUES, true);
    }
    
    public static Rational midiQuantize(long a, MidiEvent b, int resolution) {
        return Rational.quantize(
                (double)(b.getTick() - a)/(4 * resolution), VALID_NOTE_VALUES, true);
    }
    
    public static boolean isNoteOn(Byte bStatus) {
        int iStatus = Byte.toUnsignedInt(bStatus);
        return iStatus >= NOTE_ON_1 && iStatus <= NOTE_ON_16;
    }
    
    public static boolean isNoteOff(Byte bStatus) {
        int iStatus = Byte.toUnsignedInt(bStatus);
        return iStatus >= NOTE_OFF_1 && iStatus <= NOTE_OFF_16;
    }

    public static long getLong(byte [] b, int offset, int length) {
        long l = 0;
        for(int i = offset; i < offset + length; i++) {
            l <<= 8;
            l |= Byte.toUnsignedInt(b[i]);
        }
        
        return l;
    }

    public static long getBarDuration(int resolution, Rational timeSignature){ // In ticks
        return (resolution / timeSignature.getDenominator()) * 4 * timeSignature.getNumerator();
    }

    public static int mpqToBpm(long tempo){
        return (int)(60000000 / tempo);
    }
    
}