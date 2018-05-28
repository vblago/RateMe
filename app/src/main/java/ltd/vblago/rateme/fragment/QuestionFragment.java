package ltd.vblago.rateme.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ltd.vblago.rateme.R;
import ltd.vblago.rateme.util.ActivityCommunication;
import ltd.vblago.rateme.util.Questions;

public class QuestionFragment extends Fragment {

    private static final String CONST_NUM = "question_number";
    ActivityCommunication activityCommunication;
    private int questionNum;
    Unbinder unbinder;
    @BindView(R.id.questionTv)
    TextView questionTv;

    public static QuestionFragment newInstance(int num) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(CONST_NUM, num);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.great_view, R.id.good_view, R.id.fine_view, R.id.bad_view, R.id.awful_view})
    public void onClickRate(View view) {
        switch (view.getId()) {
            case R.id.great_view:
                activityCommunication.setRate(questionNum, 5);
                break;
            case R.id.good_view:
                activityCommunication.setRate(questionNum, 4);
                break;
            case R.id.fine_view:
                activityCommunication.setRate(questionNum, 3);
                break;
            case R.id.bad_view:
                activityCommunication.setRate(questionNum, 2);
                break;
            case R.id.awful_view:
                activityCommunication.setRate(questionNum, 1);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNum = getArguments().getInt(CONST_NUM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        questionTv.setText(Questions.getQuestion(questionNum));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivityCommunication) {
            activityCommunication = (ActivityCommunication) context;
        } else {
            throw new RuntimeException(Context.class.getSimpleName() + " must implement ActivityCommunication interface");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
