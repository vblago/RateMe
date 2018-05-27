package ltd.vblago.rateme.util;

public interface ActivityCommunication {
    void setRate(int question, int rate);
    void goToFirstQuestion();
    void hideUI();
    void setNumPoint(int num);
}
