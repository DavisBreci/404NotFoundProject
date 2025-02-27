package com.model;

import java.util.List;

import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.Token;
import org.jfugue.pattern.Token.TokenType;
import org.jfugue.player.Player;
import org.jfugue.theory.Note;

public class MusicPlayer {
    // Pattern motif = new Pattern("V0 I[Harp]");
    public static void main(String[] args) {
        
        
        Pattern treasureChest = new Pattern();
        Pattern melody1 = new Pattern("V0 G4s G#4s A4s A#4s V1 G2q");
        Pattern melody2 = new Pattern("V0 G4s G#4s A4s A#4s V1 G3q");
        Pattern melody3 = new Pattern("D#q3+C4q+A4q+A5q* E3q+C#4q+A#4q+A#5q* F3q+D4q+B4q+B5q* F#3www+D#4www+C4www+C5www");
    
        int tempo = 84;
        Pattern temp = new Pattern();
        Player player = new Player();
        for(int i = 0; i < 4; i++){
            temp = new Pattern();
            temp.add(transpose(melody1, i));
            tempo += 20;
            temp.add(transpose(melody2, i));
            temp.setTempo(tempo);
            treasureChest.add(temp);
            
        }   

        for(int i = 4; i < 8; i++){
            temp = new Pattern();
            treasureChest.add(transpose(melody1, i));
            tempo += 20;
            temp.setTempo(tempo);
           
        }   

        treasureChest.add("Rh");
        melody3.setTempo(180);
        treasureChest.add(melody3);

        player.play(treasureChest);
    }

    
    public static Pattern transpose(Pattern p, int steps){
        List<Token> tokens = p.getTokens();
        StringBuilder transposed = new StringBuilder("");
        for(Token t : tokens){
            if(t.getType() == TokenType.NOTE){
                Note n = new Note(t.toString());
                if (n.isRest())
                    transposed.append(t.toString());
                int noteNumber = n.getValue() + steps;
                Note nTransposed = new Note(noteNumber, n.getDuration());
                // System.out.println(nTransposed.toString());
                transposed.append(nTransposed.toString());
            } else {
                transposed.append(t.toString());
            }
            transposed.append(" ");
        }
        return new Pattern(transposed.toString());
    }
    
    public static int noteIndex(char note){
        return (note - 'C');
    }

}

