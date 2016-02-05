package idv.woody.infinispan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WordCache extends Cache<String> {
    @Autowired
    public WordCache(@Value("WORD_CACHE")String cacheName) {
        super(cacheName);
    }
}
