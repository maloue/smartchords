package smartchords;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by wilson on 5/9/15.
 */

public class Main {
    private static void testChordParsing()
    {

        Map<String,Chord> expected = new HashMap<String, Chord>();
        expected.put("C major",new Chord("C","maj"));
        expected.put("Ab",new Chord("Ab","maj"));
        expected.put("G#m7",new Chord("G#","min7"));
        expected.put("CbM7",new Chord("Cb","maj7"));
        expected.put("D major 7",new Chord("D","maj7"));
        for(String key : expected.keySet())
        {
            Chord output = Chord.parseInput(key);
            if(expected.get(key).equals(output))
                System.out.println("SUCCESS: "+key);
            else {
                System.out.print("FAIL: " + key);
                if(output == null)
                    System.out.print("  (Chord was null)");
                else
                    System.out.print("  (Chord was incorrect)");
                System.out.println();
            }
        }
    }

    public static void main(String args []) {
        Scanner s = new Scanner(System.in);
        while(true)
        {
            System.out.println("Enter key:");
            String key = s.nextLine();
            List<Chord> result = ChordProgressionGenerator.getChordsInKey(key);
            for(Chord c : result)
            {
                System.out.println(c.toString());
            }
        }
    }
}
