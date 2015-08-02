package smartchords;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wilson on 5/22/15.
 */
public class ListUtil {

    public static <T> double getPercentElement(List<T> list,T elem)
    {
        double count = 0;
        for(T listElem : list)
            if(listElem.equals(elem))
                count++;
        return count/list.size();
    }

    public static <T> List<T> getDistinct(List<T> input)
    {
        List<T> result = new ArrayList<T>();
        for(T t : input) {
            if (!result.contains(t))
                result.add(t);
        }
        return result;
    }

    public <T> void printList(List<T> l)
    {
        for(T t : l)
            System.out.print(t.toString() + ", ");
        System.out.println();
    }

    public static <T> List<T> getElementsWithCount(List<T> inList, int n)
    {
        List<T> result = new ArrayList<T>();
        List<T> processed = new ArrayList<T>();
        for(int i = 0;i < inList.size();i++)
        {
            T curr = inList.get(i);
            if(!processed.contains(curr))
            {
                int count = 1;
                for(int j = i+1;j < inList.size();j++)
                {
                    if(inList.get(j).equals(curr))
                        count++;
                }
                if(count == n)
                {
                    result.add(curr);

                }
                processed.add(curr);

            }
        }
        return result;
    }
}
