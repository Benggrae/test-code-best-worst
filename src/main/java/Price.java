public class Price {
    private final static int MIN_PRICE_NUMBER = 0;
    private int number;

    public Price(int price) {
        if (price<= MIN_PRICE_NUMBER) {
            throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        }
        this.number = price;
    }
}
