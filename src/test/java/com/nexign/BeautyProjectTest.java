package com.nexign;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BeautyProjectTest {

    //Vars
    String  url = "https://beauty-project.ru/calculator.html",
            validExpDate = "Еще годен",
            isExpired = "Вы ввели код, не являющийся батч кодом или он еще не поддерживается";

    @CsvSource(value = {
            "Nars|0247BC",
            "Dolce&Gabbana|9269DL",
            "MAC|9269DL",
    },
            delimiter = '|'
    )
    @ParameterizedTest(name = "Проверка косметики по бренду {0} и батч-коду {1}")
    void searchBeautyProject(String brand, String code) {
        //Предусловия
        Selenide.open(url);
        //Шаги
        $("#brand").$(byText(brand)).click();
        $("#code").setValue(code);
        $("button[type='submit']").click();
        //Ожмдаемый результат
        $$("#Result").find(Condition.text(validExpDate))
                .shouldNotHave(Condition.text(isExpired));
    }
}
