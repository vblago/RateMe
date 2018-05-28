package ltd.vblago.rateme.util;

import java.util.ArrayList;
import java.util.List;

public class Questions {

    public static String getQuestion(int question){

        List<String> questions = new ArrayList<>();
        questions.add("Please rate the level of staff service");
        questions.add("How do you like the quality of the assortment?");
        questions.add("Have you been satisfied with the speed of service?");
        questions.add("How to you the level of cleanliness of the room?");
        questions.add("If you told your friends about us,\n how would you rate our restaurant?");

        return questions.get(question-1);
    }
}
