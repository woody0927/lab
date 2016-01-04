package guava.splitter;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class SplitterAndJoin {

	public static void main(String[] args) {
		List<String> stringList = Lists.newArrayList("aa", "bb", "", "cc     ",
				"         dd");
		String string = Joiner.on(",").join(stringList);
		System.out.println(String.format("stringList after join :%s", string));
		List<String> stringAfterSplit = Splitter.on(",").omitEmptyStrings()
				.trimResults().splitToList(string);
		for (String data : stringAfterSplit) {
			System.out.println(String.format("element after split :%s", data));
		}
	}

}
