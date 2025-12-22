package hw4.cucumber.steps;

import io.cucumber.java.ru.*;

public class TestSteps {

    @Дано("Cucumber установлен")
    public void cucumber_установлен() {
        System.out.println("✅ Cucumber готов к работе!");
    }

    @Когда("Я запускаю тест")
    public void я_запускаю_тест() {
        System.out.println("🚀 Запускаю тест...");
    }

    @То("Я вижу успешное выполнение")
    public void я_вижу_успешное_выполнение() {
        System.out.println("🎉 Тест выполнен успешно!");
    }
}