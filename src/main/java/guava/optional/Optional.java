package guava.optional;

import static com.google.common.base.Optional.*;

public class Optional {

	public static void main(String[] args) {
		getFirstNotNull();
	}

	private static void getFirstNotNull() {
		String a = null;
		String b = null;
		String c = "cup";
		System.out.println(fromNullable(a).or(fromNullable(b)).or(fromNullable(c)).orNull());
		c = null;
		System.out.println(fromNullable(a).or(fromNullable(b)).or(fromNullable(c)).orNull());
	}

}
