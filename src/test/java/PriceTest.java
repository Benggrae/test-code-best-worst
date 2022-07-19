
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void minPriceValidTest(int price) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Price(price));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2})
    void minPriceValidTests(int price) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Price(price));
    }

}
