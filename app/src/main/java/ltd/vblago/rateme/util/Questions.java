package ltd.vblago.rateme.util;

import java.util.ArrayList;
import java.util.List;

public class Questions {

    public static String getQuestion(int question){

        List<String> questions = new ArrayList<>();
        questions.add("Пожалуйста, оцените уровень обслуживания персонала");
        questions.add("Как вам наш ассортимент?");
        questions.add("А скорость обслуживания и время ожидания?");
        questions.add("Как вам уровень чистоты помещения?");
        questions.add("Если бы рассказали друзьям о нас, как бы вы оценили наш ресторан?");

        return questions.get(question-1);
    }
}
