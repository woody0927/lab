package idv.woody.future;

import java.math.BigDecimal;

public class Test {
	public static void main(String[] args) {
        BigDecimal parameter = new BigDecimal(".0");
		BigDecimal actualObject = new BigDecimal(".0");
		System.out.println(parameter.signum() == 0);
		System.out.println(actualObject.signum() == 0);
        System.out.println(BigDecimal.ZERO.equals(parameter));
        BigDecimal wholeObject = actualObject.divide(parameter, 4, BigDecimal.ROUND_HALF_EVEN);
	}
}
