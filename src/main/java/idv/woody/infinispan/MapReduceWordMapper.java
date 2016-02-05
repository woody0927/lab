package idv.woody.infinispan;

import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;

import java.util.StringTokenizer;

public class MapReduceWordMapper implements Mapper<String, String, String, Integer> {
    public void map(String key, String value, Collector<String, Integer> collector) {
        StringTokenizer tokenizer = new StringTokenizer(value);
        while (tokenizer.hasMoreElements()) {
            String s = (String) tokenizer.nextElement();
            collector.emit(s, 1);
        }
    }
}
