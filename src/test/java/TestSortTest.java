import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSortTest {



    @Test
    //BAD
    void add_BigDecimal_resultSum_() {
        // Arrange
        BigDecimal one = BigDecimal.ONE;

        // Assert
        assertThat(one.add(BigDecimal.ONE)).isEqualTo(BigDecimal.valueOf(2));
    }

    //GOOD
    @Test
    @DisplayName("BigDecimal을 add하면 합한 값이 결과가 된다")
    void add_BigDecimal_resultSum() {
        // Arrange
        BigDecimal one = BigDecimal.ONE;

        // ACT
        BigDecimal addToOne = one.add(BigDecimal.ONE);

        // Assert
        assertThat(addToOne).isEqualTo(BigDecimal.valueOf(2));
    }
}
