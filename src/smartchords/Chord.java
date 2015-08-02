package smartchords;

/**
 * Created by wilson on 5/9/15.
 */
public class Chord {
    public String note;
    public String type;

    public Chord(String _note, String _type) {
        this.note = _note;
        this.type = _type;
    }

    public static Chord parseInput(String input)
    {
        input = input.replaceAll("\\s","");

        String inputUpper = input.toUpperCase();

        char letter = inputUpper.charAt(0);
        //System.out.println((int)letter+"|");
        String note = letter+"";
        String type;
        if(letter < 65 || letter > 71)
            return null;
        if(input.length() == 1)
            return new Chord(letter+"","maj");
        else if(input.charAt(1) == '#' || inputUpper.contains("SHARP"))
        {
            note = letter+"#";
        }
        else if(input.charAt(1) == 'b' || inputUpper.contains("FLAT"))
        {
            note = letter+"b";
        }
        type = input.substring(note.length());
        String type_lower = type.toLowerCase();

        if(type_lower.contains("maj7") || type.contains("M7") || type_lower.contains("major7"))
            return new Chord(note,"maj7");
        else if(type_lower.contains("min7") || type.contains("m7") || type_lower.contains("minor7"))
            return new Chord(note,"min7");
        else if (type_lower.contains("maj") || type.length() == 0)
            return new Chord(note,"maj");
        else if(type_lower.contains("min"))
            return new Chord(note,"min");
        else if(type.equals("M"))
            return new Chord(note,"maj");
        else if(type.equals("m"))
            return new Chord(note,"min");
        return null;


    }
    @Override
    public String toString()
    {
        return note+type;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Chord))
            return false;

        Chord rhs = (Chord) obj;
        return (this.noteEquals(rhs.note) && this.type == rhs.type);
    }

    public boolean noteEquals(String otherNote)
    {
        String[] allNoteNames = getAllNoteNames();
        for(String name : allNoteNames)
            if(name.equals(otherNote))
                return true;
        return false;
    }

    public boolean isMajor()
    {
        for(String t : major_types) {
            if(type == t)
                return true;
        }
        return false;
    }

    public static String[] major_types = new String[]{"maj", "maj7"};
    public static String[] minor_types = new String[]{"min", "min7"};

    public String [] getAllNoteNames()
    {
        if(note.length() == 2)
        {
            int index = MusicCore.getNoteIndex(note);
            char acc = note.charAt(1);
            if(index == -2) //Cb, E#, etc.
            {

                int whiteNoteIndex = MusicCore.getNoteIndex(note.substring(0, 1));
                int altNoteIndex = whiteNoteIndex + ((acc == '#') ? 1 : -1);
                return new String[] {note,MusicCore.getNote(altNoteIndex)[0]};
            }
            else if(index != -1)
            {
                String [] thisNote = MusicCore.getNote(index);
                return thisNote;
            }
            else
            {
                return new String []{};
            }

        }
        else
            return new String [] {note};
    }

    public String getSimpleNoteEquivalent()
    {
        /*
        For some things (namely chord diagrams) we use chords as keys.
        We need to use a common form of notes (flat or white)
         */
        String[] noteNames = getAllNoteNames();
        for(String note : noteNames) {
            if (note.length() == 1)
                return note;
        }
        for(String note : noteNames)
            if(note.length() == 2)
                if (note.charAt(1) == 'b')
                    return note;
        return "?";
    }

    public static int [] getBarreChord(int [] diagram)
    {
        int totalNumFingers = 0;
        for(int f : diagram)
            if(f > 0)
                totalNumFingers++;


        int minFinger = getMinFret(diagram);

        int minDex = -1, maxDex = -1;

        for(int i = 0;i < diagram.length;i++)
            if(diagram[i] == minFinger) {
                minDex = i;
                break;
            }

        for(int i = diagram.length-1;i > -1;i--)
            if(diagram[i] == minFinger) {
                maxDex = i;
                break;
            }

        int [] SUCCESS = new int[]{minFinger,minDex,maxDex};
        int [] FAIL = new int[]{-1,-1,-1};

        if(maxDex == minDex)
            return FAIL;
        else if (maxDex-minDex < 3 && totalNumFingers < 4) //Not sure about this - failsafe for a d major?
            return FAIL;

        for(int i = minDex+1;i < maxDex;i++)
        {
            //Check fingers "over" bar
            if(diagram[i] < minFinger)
                return FAIL;
        }

        return SUCCESS;
    }

    public static int getMaxFret(int [] chord) {
        int max = 0;
        for (int f : chord)
            if(f > max)
                max = f;
        return max;
    }

    public static int getMinFret(int [] chord) {
        int min = 100;
        for (int f : chord)
            if(f < min && f > 0)
                min = f;
        return min;
    }
}