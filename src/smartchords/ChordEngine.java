package smartchords;

import java.util.*;

/**
 * Created by wilson on 5/9/15.
 */
public class ChordEngine {



    private static Random r = new Random();

    private static String[][] valid_types = new String[][]{
            {"maj"},
            {"min", "min7"},
            {"min", "min7"},
            {"maj", "maj7"},
            {"maj"},
            {"min", "min7"},

    };

    private static boolean chordInScale(Chord chord, String[] scale) {
        for (int i = 0; i < scale.length; i++) {
            if (chord.noteEquals(scale[i]) && chord.isMajor() == MusicCore.major[i])
                return true;
        }
        return false;
    }

    public ChordEngine() {
        inputChords = new ArrayList<Chord>();
    }

    private List<Chord> inputChords, suggestedChords;

    private List<String> inputNotes = new ArrayList<String>();

    private Map<Chord, List<Integer>> chordContexts = new HashMap<Chord, List<Integer>>();
    //Map suggested chord to a list of all steps where that chord is suggested!

    public void addChord(Chord chord) {
        inputChords.add(chord);
        populateSuggestedChords();
    }

    public void remove(int index) {
        inputChords.remove(index);
        populateSuggestedChords();
    }

    public List<Chord> getInputChords() {
        return inputChords;
    }

    public Chord getSuggestedChord(int i)
    {
        if(i >= suggestedChords.size()) {
            System.out.println("getSuggestedChord index too high! ("+i+">="+suggestedChords.size());
            return null;
        }
        return suggestedChords.get(i);
    }

    public int getSuggestedChordsCount()
    {
        return suggestedChords.size();
    }

    private void populateSuggestedChords() {
        /*
        Process

        For each chord, make a list of suggested chords (just maj/min, no 7, distinct
        flatten into a smaller list

        Count how many times each chord occurs (loop from inputChords size down to 1, use count_n
        Sort by occurances, randomize in "pockets"

        Set some random 7ths and shit
         */

        chordContexts = new HashMap<Chord, List<Integer>>();
        //Reset contexts

        suggestedChords = new ArrayList<Chord>();
        //Reset suggested chords


        List<Chord> allSuggestedChords = new ArrayList<Chord>();
        for (Chord chord : inputChords) {
            List<Chord> matched = getSuggestedChords(chord);


            for (Chord c : ListUtil.getDistinct(matched)) {
                allSuggestedChords.add(c);
            }

            //System.out.print(c.toString()+", ");
            //System.out.println();
        }

        for (int numOccurances = inputChords.size(); numOccurances > 0; numOccurances--) {
            List<Chord> chordsThatOccurNTimes = ListUtil.getElementsWithCount(allSuggestedChords, numOccurances);
            //Populate result in random order!
            for (int i = chordsThatOccurNTimes.size(); i > 0; i--) {
                //Get random chord
                int index = r.nextInt(i);
                suggestedChords.add(chordsThatOccurNTimes.get(index));
                chordsThatOccurNTimes.remove(index);
            }
        }


        //Do some random 7ths!
        for (int i = 0; i < suggestedChords.size(); i++) {
            //40 % TIMES percentage of context that fits!
            Chord c = suggestedChords.get(i);
            if (c.type.equals("maj")) {
                double weight = 0.4 * ListUtil.getPercentElement(chordContexts.get(c), 3); // Use % of occurances that are 4ths!
                if (r.nextDouble() < weight)
                    suggestedChords.set(i, new Chord(c.note, "maj7"));
            } else if (r.nextDouble() < 0.3) // minor chord!
                suggestedChords.set(i, new Chord(c.note, "min7"));
        }

    }

    private boolean noteInInputs(String note) {
        for(Chord input : inputChords) {
            if(input.noteEquals(note))
                return true;
        }
        return false;
    }


    public List<Chord> getSuggestedChordsFILTERED() {
        List<Chord> result = new ArrayList<Chord>();
        for(Chord suggestedChord : suggestedChords) {
            if(!noteInInputs(suggestedChord.note))
                result.add(suggestedChord);
        }
        return result;
    }

    private List<Chord> getSuggestedChords(Chord c1) {
        //This should return a distinct list...except two copies of each chord that occurs in the KEY of the input chord
        List<Chord> result = new ArrayList<Chord>();
        for (String[] scale : MusicCore.scales) {
            String key = scale[0];
            boolean foundInKey = false;
            int foundIndex = -1;
            for (int i = 0; i < scale.length; i++)
                if (c1.noteEquals(scale[i]) && c1.isMajor() == MusicCore.major[i]) {
                    foundInKey = true;
                    foundIndex = i;
                    break;
                }
            if (foundInKey)
                for (int i = 0; i < scale.length; i++)
                    if (i != foundIndex) {
                        Chord c = new Chord(scale[i], valid_types[i][0]);
                        result.add(c);
                        if(c1.noteEquals(key)) //Add an extra copy!
                            result.add(c);
                        List<Integer> context;
                        if (chordContexts.containsKey(c))
                            context = chordContexts.get(c);
                        else
                            context = new ArrayList<Integer>();
                        context.add(i);
                        chordContexts.put(c, context);
                    }
        }
        return result;
    }
}