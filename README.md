![image-20220720023711351](/Users/kimbyounghee/Library/Application Support/typora-user-images/image-20220720023711351.png)

첫 글이 테스트 코드라니 어려운 주제이다.

평소에 테스트 코드에 대해서 중요하게 생각하지 않고 코드를 작성해 왔었다.

최근엔 우아한 테크캠프 프로를 통해 테스트 코드에 대해서 인사이트를 얻어 앞으로 실천하고자 모범적인 테스트 코드에 대해서 정리해보고 내가 작성한 좋지 않은 테스트 코드 사례에 대해서 정리해보고자 한다.

## 모범 적인 테스트 코드 사례 정리

(출처 : https://docs.microsoft.com/ko-kr/dotnet/core/testing/unit-testing-best-practices)

### 단위 테스트를 하는 이유

- 기능 테스트 수행 시간 단축

  ⇒  기능에 대한 테스트 코드를 사용자가 일반적으로 애플리케이션을 열고 등의 행위를 통해 검사하지만 test runner를 통해 간단하게 검증할 수 있다.

- 회귀에 대한 보호

  ⇒  새로운 코드를 추가할 때 기존의 추가된 테스트를 실행해보기 때문에 새로운 코드를 추가함으로써

  기존의 기능이 중단되지 않음을 보장한다.

- 적은 결합 코드

  ⇒  단위테스트를 가능한 코드를 작성함으로써 자연스럽게 코드가 분리된다.

### 좋은 테스트의 특징

- Fast : 테스트 코드는 빨라야 한다.

- Isolate : 독립적인 단위로 수행해야 하며, 외부 시스템(데이터베이스 등)에 영향을 받지 않아야

  한다.

- 반복 가능 : 단위 테스트를 실행할 때 항상 동일한 결과를 반환 해야 한다. (멱등성)

- 자체검사 : 테스트는 사람의 개입 없이 통과했는지를 자동으로 검색할 수 있어야 합니다. (JUnit)

- Timely : 단위 테스트 코드는 프로덕션 코드 보다 적게 작성 기간을 가져야 한다.

### 사례

- **테스트 이름 지정**

  테스트할 이름, 테스트 시나리오, 결과로 이름을 구성한다.

  ⇒ 테스트를 이름을 지정함으로써 테스트의 의도를 명시적으로 표현할 수 있다.

    - BAD (의미없는 테스트 이름 Test1)

      ```java
      @Test
      void test1() {
      BigDecimal one = BigDecimal.ONE;
      
      BigDecimal addToOne = one.add(BigDecimal.ONE);
      
      assertThat(addToOne).isEqualTo(BigDecimal.valueOf(2));
      }
      ```

    - Better

      ```java
      @Test
      @DisplayName("BigDecimal을 add하면 합한 값이 결과가 된다")
      void add_BigDecimal_resultSum() {
          BigDecimal one = BigDecimal.ONE;
      
          BigDecimal addToOne = one.add(BigDecimal.ONE);
      
          assertThat(addToOne).isEqualTo(BigDecimal.valueOf(2));
      }
      ```

- **테스트 정렬**

  테스트는 정렬, 동작 ,어설션으로 구성한다.

  ⇒ 각각 구분을 구별하여 가독성을 늘리고 각 단계를 강조할 수 있다.

    - BAD

      ```java
      @Test
      //BAD
      void add_BigDecimal_resultSum_() {
        // Arrange
        BigDecimalone =BigDecimal.ONE;
      
        // Assert
        assertThat(one.add(BigDecimal.ONE)).isEqualTo(BigDecimal.valueOf(2));
      }
      ```

    - Better (AAA 패턴으로 행위를 강조하고 있다)

      ```java
      void add_BigDecimal_resultSum() {
              // Arrange
              BigDecimal one = BigDecimal.ONE;
      
              // ACT
              BigDecimal addToOne = one.add(BigDecimal.ONE);
      
              // Assert
              assertThat(addToOne).isEqualTo(BigDecimal.valueOf(2));
        }
      ```

이외 에도..

- **매직 문자열 방지**

  테스트 코드에서도 매직 문자열을 방지 해야한다.

- **테스트에서 논리 방지**

  테스트 코드에서 논리 (if, for)등의 논리를 방지한다.

- **다중 동작 방지**

  테스트코드는 하나의 동작만 검사한다.

### 내가 작성한 좋지 않은 테스트 코드 사례

- **로직을 담은 테스트 코드**  **(테스트에서 논리 방지 위반)**

    - 로직을 담은 테스트 코드는 테스트 안에 로직에 대한 검증이 또 필요하다.

    - 테스트 코드는 한눈에 봐도 이해할 수 있어야 하는데 로직을 해석하는 과정이 필요하다.

    - 이전 코드 (BAD)

      ⇒  (다수의 값을 검증 하기위해 테스트 코드 안에 for 구문으로 로직을 검증 하고 있음)

      ```java
      @Test
      @DisplayName("chatAt 메서드의 벗어난 문자를 찾으면 StringIndexOutOfBoundsException 발생한다.")
      void charAtNoSearch(){
        for (int boundaryValue : boundary) {
            assertThatThrownBy(() -> data.charAt(boundaryValue))
                    .isInstanceOf(StringIndexOutOfBoundsException.class)
                    .hasMessageContaining("String index out of range");
        }
      }
      ```

    - 변경 코드

      ⇒ 다수의 검증을 위해서는 @ParameterizedTest를 이용하여 검증하자)

      ```java
      @ParameterizedTest
      @ValueSource(ints = {-1, 4})
      @DisplayName("chatAt 메서드의 벗어난 문자를 찾으면 StringIndexOutOfBoundsException 발생한다.")
      void charAtNoSearch(int index) {
          assertThatThrownBy(() -> data.charAt(index))
                  .isInstanceOf(StringIndexOutOfBoundsException.class)
                  .hasMessageContaining("String index out of range");
      }
      ```

- **여러 의미를 갖는 테스트를 코드 (다중 동작 방지 위반)**

    - 여러 의미를 갖는 테스트 코드는 사용자가 행위를 파악하기 힘들다.

      몇 개를 검증하는지? 어떤 행위를 검증하는지?

    - 이전 코드 (BAD)

      ⇒  특정 위치의 문자열, 벗어난 문자를 찾는 테스트 두 가지의 행위 테스트가 공존

      ```java
      @Test
      @DisplayName("특정위치의 문자열을 가져오고,"
       + "벗어난 문자를 찾으면 StringIndexOutOfBoundsException 발생한다.")
      void charAt(){
          String data = "abc";
          int[] boundary = {-1, 4};
          assertThat(data.charAt(0)).isEqualTo('a');
      
            assertThatThrownBy(() -> data.charAt(index))
                      .isInstanceOf(StringIndexOutOfBoundsException.class)
                      .hasMessageContaining("String index out of range");
      }
      ```

    - 변경 코드

      ⇒  여러 의미를 가질땐 분리하여 해당 테스트 메서드의 의미를 명확하게 하자

      ```java
      @ParameterizedTest
      @CsvSource(value = {"0:a","1:b", "2:c"}, delimiter = ':')
      @DisplayName("특정위치의 문자열을 가져온다.")
      void charAt(int index, char result){
        assertThat(data.charAt(index)).isEqualTo(result);
      }
      ```

      ```java
      @ParameterizedTest
      @ValueSource(ints = {-1, 4})
      @DisplayName("벗어난 문자를 찾으면 StringIndexOutOfBoundsException 발생한다.")
      void charAtNoSearch(int index) {
          assertThatThrownBy(() -> data.charAt(index))
                  .isInstanceOf(StringIndexOutOfBoundsException.class)
                  .hasMessageContaining("String index out of range");
      }
      ```

### 개인적으로 생각하는 추가 좋은 테스트 코드

- **경계값을 검사하는 테스트코드**

  **검사할 경계값을 정의하여 테스트 한다.**

    - BAD

      ```java
      @ParameterizedTest
      @ValueSource(ints = {-1, -2})
      void minPriceValidTests(int price) {
          assertThatIllegalArgumentException()
                  .isThrownBy(() -> new Price(price));
      }
      ```

    - Better (애매한 0의 경계값을 포함시켜 정의)

      ```java
      @ParameterizedTest
      @ValueSource(ints = {-1, 0})
      void minPriceValidTests(int price) {
          assertThatIllegalArgumentException()
                  .isThrownBy(() -> new Price(price));
      }
      ```

### 개인적으로 생각하는 테스트 코드의 이점

(개인적인 생각이니 틀린 점이나 더 다른 이점이 있으면 댓글 부탁드리겠습니다.)

- 테스트 코드를 작성함으로써 운영 코드의 안정성이 향상된다.
- 작성한 테스트 코드로 무엇을 검증하는지?를 통해 대략적인 프로세스를 알 수 있다.
- 기존에 작성한 테스트 코드의 보호 속에서 자신 있게 리팩토링을 할 수 있다.
