package domain.gamer;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domain.card.Card;
import domain.card.Symbol;
import domain.card.Type;

class PlayerTest {
	@Test
	@DisplayName("입력받은 문자열이 null인지 검증")
	void validateNull() {
		assertThatThrownBy(() -> {
			new Player(null);
		}).isInstanceOf(NullPointerException.class);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "  "})
	@DisplayName("입력받은 문자열이 공백인지 검증")
	void validateSpace(String name) {
		assertThatThrownBy(() -> {
			new Player(name);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("플레이어가 지급받은 카드를 갖고 있는지 확인")
	void receiveCard() {
		Player player = new Player("pobi");
		player.hit(new Card(Symbol.ACE, Type.CLUB));

		assertThat(player.getCards()).contains(new Card(Symbol.ACE, Type.CLUB));
	}

	@Test
	@DisplayName("20이하의 경우 추가 카드를 받을 수 있는지")
	void canReceiveMore() {
		Player player = new Player("pobi");
		player.hit(new Card(Symbol.SEVEN, Type.DIAMOND));
		player.hit(new Card(Symbol.SEVEN, Type.CLUB));
		player.hit(new Card(Symbol.SIX, Type.DIAMOND));

		assertThat(player.canHit()).isTrue();
	}

	@Test
	@DisplayName("20초과의 경우 추가 카드를 받을 수 없는지")
	void canNotReceiveMore() {
		Player player = new Player("pobi");
		player.hit(new Card(Symbol.EIGHT, Type.DIAMOND));
		player.hit(new Card(Symbol.SEVEN, Type.CLUB));
		player.hit(new Card(Symbol.SIX, Type.DIAMOND));

		assertThat(player.canHit()).isFalse();
	}

	@Test
	@DisplayName("hit을 할 경우 카드가 하나 추가되는지 테스트")
	void hitTest() {
		Player player = new Player("pobi");
		player.hit(new Card(Symbol.EIGHT, Type.DIAMOND));
		player.hit(new Card(Symbol.SEVEN, Type.CLUB));
		player.hit(new Card(Symbol.SIX, Type.DIAMOND));

		assertThat(player.getCards()).hasSize(3);
	}

	@Test
	@DisplayName("카드의 합을 올바르게 계산하는지 테스트")
	void sumOfCardTest() {
		Player player = new Player("pobi");
		player.hit(new Card(Symbol.EIGHT, Type.DIAMOND));
		player.hit(new Card(Symbol.SEVEN, Type.CLUB));
		player.hit(new Card(Symbol.SIX, Type.DIAMOND));
		assertThat(player.sumOfCards()).isEqualTo(21);

		player.hit(new Card(Symbol.ACE, Type.HEART));
		assertThat(player.sumOfCards()).isEqualTo(22);
	}

	@Test
	@DisplayName("에이스를 포함하는지 여부를 테스트")
	void hasAceTest() {
		Player player = new Player("pobi");
		player.hit(new Card(Symbol.EIGHT, Type.DIAMOND));
		assertThat(player.hasAce()).isFalse();

		player.hit(new Card(Symbol.ACE, Type.CLUB));
		assertThat(player.hasAce()).isTrue();
	}

	@Test
	@DisplayName("플레이어가 처음에 2장의 패를 공개하는지 테스트")
	void firstOpenedCardsTest() {
		Player player = new Player("pobi");
		player.hit(new Card(Symbol.EIGHT, Type.DIAMOND));
		player.hit(new Card(Symbol.NINE, Type.DIAMOND));

		assertThat(player.firstOpenedCards()).hasSize(2);
	}
}