package com.nexign;

import com.codeborne.selenide.Condition;
import com.nexign.filters.FilterCategories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@DisplayName("Класс с тестами для проверки функции поиска товаров по тексту в интернет-магазине Wildberries")
public class WildberriesSearchTest {
    //Vars
    String  url = "https://www.wildberries.ru/",
            notFound = "По Вашему запросу ничего не найдено.";

    @CsvSource(value = {
            "Nars|Румяна",
            "Dolce&Gabbana|Туалетная вода",
            "MAC|Тушь",
    },
            delimiter = '|'
    )

    @ParameterizedTest(name = "Проверка наличия в результатах поиска категории {1} у бренда {0}")
    void searchItemByBrand(String brand, String category) {
        //Предусловия
        open(url);
        //Шаги
        $("#searchInput").setValue(brand);
        $("#searchInput").pressEnter();
        //Ожидаемый результат
        $$(".product-card__wrapper").find(text(category))
                .shouldBe(visible)
                .shouldNotHave(text(notFound));
    }

    static Stream<Arguments> searchItemByBrandAndCategory() {
        return Stream.of(
                Arguments.of("Nars", "Румяна"),
                Arguments.of("Dolce&Gabbana", "Туалетная вода")
        );
    }

    @MethodSource("searchItemByBrandAndCategory")
    @ParameterizedTest(name = "Проверка наличия результатов поиска при вводе текста в формате {0} {1}")
    void searchItemByBrandAndCategory(String brand, String category) {
        //Предусловия
        open(url);
        //Шаги
        $("#searchInput").setValue(brand+ " " + category);
        $("#searchInput").pressEnter();
        //Ожидаемый результат
        $$(".product-card__wrapper").find(text(category))
                .shouldBe(visible)
                .shouldNotHave(text(notFound));
    }

    @EnumSource(FilterCategories.class)
    @ParameterizedTest()
    void searchItemAndCheckFilters(FilterCategories testData) {
        //Предусловия
        open(url);
        //Шаги
        $("#searchInput").setValue("Nars");
        $("#searchInput").pressEnter();
        //Ожидаемый результат
        $$(".catalog-page__side")
                .find(Condition.text(testData.rusName))
                .shouldBe(visible);
    }
}
