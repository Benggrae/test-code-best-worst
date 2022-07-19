import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestNameTest {

    //테스트의 이름을 명확하게
    @Test
    //BAD
    void test1() {
        BigDecimal one = BigDecimal.ONE;

        BigDecimal addToOne = one.add(BigDecimal.ONE);

        assertThat(addToOne).isEqualTo(BigDecimal.valueOf(2));
    }

    //GOOD
    @Test
    @DisplayName("BigDecimal을 add하면 합한 값이 결과가 된다")
    void add_BigDecimal_resultSum() {
        BigDecimal one = BigDecimal.ONE;

        BigDecimal addToOne = one.add(BigDecimal.ONE);

        assertThat(addToOne).isEqualTo(BigDecimal.valueOf(2));
    }
}
