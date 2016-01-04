package guava.functional;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class Functions {
	public static final Function<Identifiable, Long> GET_IDENTITY = new Function<Identifiable, Long>() {
		public Long apply(Identifiable identifiable) {
			return identifiable.getId();
		}
	};
	
	public static final Function<DisplayIdentity, String> GET_DISPLAY_IDENTITY = new Function<DisplayIdentity, String>() {
		public String apply(DisplayIdentity displayIdentity) {
			return displayIdentity.getDisplayIdentity();
		}
	};
	
	public static final Iterable<Long> GET_IDENTITIES(Iterable<? extends Identifiable> identifiables) {
		return Iterables.transform(identifiables, GET_IDENTITY);
	}
}
