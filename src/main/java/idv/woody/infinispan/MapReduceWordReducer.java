package idv.woody.infinispan;

import org.infinispan.distexec.mapreduce.Reducer;

import java.util.Iterator;

/**
 * Created by chu-chiao on 2016/1/6.
 */
public class MapReduceWordReducer implements Reducer<String, Integer> {
    public Integer reduce(String s, Iterator<Integer> iter) {
        int sum = 0;
        while (iter.hasNext()) {
            Integer i = (Integer) iter.next();
            sum += i;
        }
        return sum;
    }
}
