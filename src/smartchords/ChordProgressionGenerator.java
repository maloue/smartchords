package smartchords;

import java.util.*;

public class ChordProgressionGenerator {
    public static List<Chord> getChordsInKey(String key)
    {
        int keyIndex = MusicCore.getNoteIndex(key);
        String[] scale = MusicCore.scales[keyIndex];

        List<Chord> result = new ArrayList<Chord>(scale.length);

        for(int i = 0;i < scale.length;i++)
        {
            if(MusicCore.major[i]) {
                result.add(new Chord(scale[i],"maj"));
            }
            else {
                result.add(new Chord(scale[i],"min"));
            }
        }
        return result;
    }

    private static int[][] progressionShells = new int[][] {
        {1,5,6,4},
        {1,6,5,4},
        {1,4,5,6},
        {1,4,6,5},
        {1,5,6,4},
        {1,5,6,4},
    };
}
