package smartchords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wilson on 5/15/15.
 */
public class MusicCore {
    public static String[][] notes = {
            {"A"},
            {"A#","Bb"},
            {"B"},
            {"C"},
            {"C#","Db"},
            {"D"},
            {"D#","Eb"},
            {"E"},
            {"F"},
            {"F#","Gb"},
            {"G"},
            {"G#","Ab"}
    };
    public static String[] white_keys = {"A","B","C","D","E","F","G"};

    public static String[][] scales = new String[][]{
            {"A", "B", "C#", "D", "E", "F#"},
            {"Bb", "C", "D", "Eb", "F", "G"},
            {"B", "C#", "D#", "E", "F#", "G#"},
            {"C", "D", "E", "F", "G", "A"},
            {"Db", "Eb", "F", "Gb", "Ab", "Bb"},
            {"D", "E", "F#", "G", "A", "B"},
            {"Eb", "F", "G", "Ab", "Bb", "C"},
            {"E", "F#", "G#", "A", "B", "C#"},
            {"F", "G", "A", "Bb", "C", "D"},
            {"Gb", "Ab", "Bb", "B", "E", "F#"},
            {"G", "A", "B", "C", "D", "E"},
            {"Ab", "Bb", "C", "Db", "Eb", "F"}
    };

    public static boolean[] major = new boolean[]{true, false, false, true, true, false};

    final static int NOT_FOUND = -1;
    final static int SPECIAL_ACCIDENTAL = -2;

    private static <T> boolean contains(T[] array, T object)
    {
        for(T item : array)
            if(item.equals(object))
                return true;
        return false;
    }

    public static int getNoteIndex(String note)
    {
        for(int i = 0;i < notes.length;i++)
        {
            String [] currNote = notes[i];
            if(note.length() == currNote.length)
            {
                for(String innerNote : currNote)
                    if(note.equals(innerNote))
                        return i;
            }
        }
        if(note.length() == 2)
        {
            String white_key = note.charAt(0)+"";
            char acc = note.charAt(1);
            if(contains(white_keys,white_key) && acc == 'b' || acc == '#')
                return SPECIAL_ACCIDENTAL;
        }
        return NOT_FOUND;
    }

    public static String [] getNote(int index)
    {
        return notes[(index+12) % 12];
    }


}
