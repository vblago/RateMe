package ltd.vblago.rateme.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import ltd.vblago.rateme.R;
import ltd.vblago.rateme.fragment.MenuFragment;
import ltd.vblago.rateme.fragment.QuestionFragment;
import ltd.vblago.rateme.fragment.ResultFragment;
import ltd.vblago.rateme.model.Opinion;
import ltd.vblago.rateme.util.ActivityCommunication;
import ltd.vblago.rateme.util.Retrofit;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements ActivityCommunication {

    int numPoint;
    Opinion opinion;
    public static final int START = 1;
    @SuppressLint("HandlerLeak")
    Handler h = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            if (msg.what == START) {
                opinion.setTime();
                sendOpinion();
                goToFirstQuestion();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideUI();
        goToMenuFragment();
    }

    private void goToMenuFragment(){
        MenuFragment menuFragment = new MenuFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, menuFragment)
                .commit();
    }

    @Override
    public void goToFirstQuestion(){
        opinion = new Opinion(numPoint);
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.container, QuestionFragment.newInstance(1))
                .commit();
    }

    private void goToNextQuestion(int question){
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.container, QuestionFragment.newInstance(question))
                .commit();
    }

    private void goToResultFragment(){
        ResultFragment resultFragment = new ResultFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.container, resultFragment)
                .commit();
    }

    @Override
    public void setRate(int question, int rate) {
        h.removeMessages(START);
        h.sendEmptyMessageDelayed(START, 30_000);
        switch (question){
            case 1:
                opinion.q1 = String.valueOf(rate);
                goToNextQuestion(2);
                break;
            case 2:
                opinion.q2 = String.valueOf(rate);
                goToNextQuestion(3);
                break;
            case 3:
                opinion.q3 = String.valueOf(rate);
                goToNextQuestion(4);
                break;
            case 4:
                opinion.q4 = String.valueOf(rate);
                goToNextQuestion(5);
                break;
            case 5:
                opinion.q5 = String.valueOf(rate);
                goToResultFragment();
                break;
        }
    }

    private void sendOpinion(){
        Retrofit.setOpinion(opinion, new Callback<String>() {
            @Override
            public void success(String s, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void hideUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    @Override
    public void setNumPoint(int num) {
        numPoint = num;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        h.removeMessages(START);
    }
}
