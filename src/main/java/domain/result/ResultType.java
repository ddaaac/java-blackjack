package domain.result;

import domain.gamer.Gamer;
import domain.gamer.Money;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum ResultType {
	BLACK_JACK("블랙잭", 0.5, (gamer, otherGamer) -> {
		return gamer.isBlackJack() && otherGamer.isNotBlackJack();
	}),
	WIN("승", 1, (gamer, otherGamer) -> {
		return gamer.isNotBlackJack() && !gamer.isBust() && (otherGamer.isBust() || gamer.isBiggerThan(otherGamer));
	}),
	DRAW("무", 0, (gamer, otherGamer) -> {
		return (gamer.isBlackJack() && otherGamer.isBlackJack()) ||
				(!gamer.isBust() && !otherGamer.isBust() && gamer.isEqualTo(otherGamer));
	}),
	LOSE("패", -1, (gamer, otherGamer) -> {
		return gamer.isBust() || gamer.isLowerThan(otherGamer);
	});

	private final String result;
	private final double times;
	private final BiFunction<Gamer, Gamer, Boolean> expression;

	ResultType(String result, double times, BiFunction<Gamer, Gamer, Boolean> expression) {
		this.result = result;
		this.times = times;
		this.expression = expression;
	}

	public static ResultType of(Gamer gamer, Gamer otherGamer) {
		return Arrays.stream(ResultType.values())
				.filter(result -> result.expression.apply(gamer, otherGamer))
				.findFirst()
				.orElseThrow(() -> new AssertionError("ResultType 중 반드시 하나가 반환되어야합니다."));
	}

	public double calculateProfit(Money money) {
		return money.multiply(this.times);
	}

	public String getResult() {
		return this.result;
	}
}