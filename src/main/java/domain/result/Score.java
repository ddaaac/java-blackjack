package domain.result;

import domain.card.Hand;

import java.util.Objects;

public class Score {
	public static final Score BLACKJACK = Score.from(21);
	private static final int TEN = 10;
	private static final int BLACKJACK_SCORE = 21;

	private final int score;

	private Score(int score) {
		this.score = score;
	}

	public static Score from(int score) {
		return new Score(score);
	}

	public static Score from(Hand hand) {
		int score = hand.sumOfCards();

		return new Score(reviseAceScore(hand.hasAce(), score));
	}

	private static int reviseAceScore(boolean hasAce, int score) {
		if (hasAce && (score + TEN <= BLACKJACK_SCORE)) {
			score += TEN;
		}
		return score;
	}

	public boolean isBiggerThan(Score other) {
		return this.score > other.score;
	}

	public boolean isEqualTo(Score other) {
		return this.score == other.score;
	}

	public boolean isLowerThan(Score other) {
		return this.score < other.score;
	}

	public int getScore() {
		return score;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Score score1 = (Score) o;
		return score == score1.score;
	}

	@Override
	public int hashCode() {
		return Objects.hash(score);
	}
}
