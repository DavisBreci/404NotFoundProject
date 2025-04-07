package com.model;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;

/**
 * Helper class with methods and constants useful for parsing MIDI files
 * @author Christopher Ferguson
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
    public static final byte INSTRUMENT_NAME = (byte)0x04;
    public static final int EOT = 0x2F;
    /**
     * Snaps the elapsed time between two events to a 1/64-based grid
     * @param a the start event
     * @param b the end event
     * @param resolution number of ticks in a quarter note
     * @return the quantized difference
     */
    public static Rational midiQuantize(MidiEvent a, MidiEvent b, int resolution) {
        return Rational.quantize(
                (double)(b.getTick() - a.getTick())/(4 * resolution), 0.01, 64);
    }
    
    /**
     * Snaps the elapsed time between two events to a 1/64-based grid
     * @param a the start event (in ticks)
     * @param b the end event
     * @param resolution number of ticks in a quarter note
     * @return the quantized difference
     */
    public static Rational midiQuantize(long a, MidiEvent b, int resolution) {
        return Rational.quantize(
                (double)(b.getTick() - a)/(4 * resolution), 0.01, 64);
    }
    
    /**
     * Returns whether a the status byte represents a note on event
     * @param bStatus MIDI event status byte
     * @return whether the status byte represents a note on event
     */
    public static boolean isNoteOn(Byte bStatus) {
        int iStatus = Byte.toUnsignedInt(bStatus);
        return iStatus >= NOTE_ON_1 && iStatus <= NOTE_ON_16;
    }
    
    /**
     * Returns whether a the status byte represents a note off event
     * @param bStatus MIDI event status byte
     * @return whether the status byte represents a note off event
     */
    public static boolean isNoteOff(Byte bStatus) {
        int iStatus = Byte.toUnsignedInt(bStatus);
        return iStatus >= NOTE_OFF_1 && iStatus <= NOTE_OFF_16;
    }

    /**
     * Converts a MIDI message into a long. This is typically used for tempo messages.
     * @param b the MIDI message
     * @param offset starting index of the long
     * @param length the long's length
     * @return the long
     */
    public static long getLong(byte [] b, int offset, int length) {
        long l = 0;
        for(int i = offset; i < offset + length; i++) {
            l <<= 8;
            l |= Byte.toUnsignedInt(b[i]);
        }
        
        return l;
    }

    /**
     * Gets the duration of a bar in midi ticks
     * @param resolution number of midi ticks in a quarter note
     * @param timeSignature the time signature of  the measure
     * @return number of midii ticks in the measure
     */
    public static long getBarDuration(int resolution, Rational timeSignature){ // In ticks
        return (resolution / timeSignature.getDenominator()) * 4 * timeSignature.getNumerator();
    }

    /**
     * Converts microseconds per quarter note to beats per minute
     * @param tempo tempo in microseconds per quarter note
     * @return tempo in beats per minute
     */
    public static int mpqToBpm(long tempo){
        return (int)(60000000 / tempo);
    }

    /**
     * Turns an instrument name byte array into a string
     * @param msg the midi message
     * @return the instrument's name
     */
    public static String getInstrumentName(byte [] msg){
        StringBuilder name = new StringBuilder();
        int end = 3 + Byte.toUnsignedInt(msg[2]);
        for(int i = 3; i < end; i++)
            name.append((char)msg[i]);
        return name.toString();
    }

    public static boolean isEOT(MidiEvent m){
        return m.getMessage().getStatus() == MetaMessage.META && Byte.toUnsignedInt(m.getMessage().getMessage()[1]) == EOT;
    }

    public static Rational getTimeSignature(byte [] msg){
        Rational ts = new Rational(4);
        ts.setNumerator(Byte.toUnsignedInt(msg[3]));
        ts.setDenominator(2 << (Byte.toUnsignedInt(msg[4]) - 1)); // Power of 2
        return ts;
    }
}
