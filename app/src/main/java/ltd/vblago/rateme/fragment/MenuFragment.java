package ltd.vblago.rateme.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ltd.vblago.rateme.R;
import ltd.vblago.rateme.util.ActivityCommunication;

public class MenuFragment extends Fragment {

    Unbinder unbinder;
    ActivityCommunication activityCommunication;
    @BindView(R.id.set_point_number)
    Button setPointBtn;
    private static final String SHPREF = "sPreferences";
    private int numPoint, setNumPoint;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        SharedPreferences settings = Objects.requireNonNull(getContext()).getSharedPreferences(SHPREF, Context.MODE_PRIVATE);
        numPoint = settings.getInt("point_num", 0);
        if (numPoint == 0){
            setPointBtn.setText(R.string.set_point_number_str);
        }
        return view;
    }

    @OnClick(R.id.go_to_question_fragment)
    public void goToQuestionFragment(){
        if (numPoint != 0){
            activityCommunication.setNumPoint(numPoint);
            activityCommunication.goToFirstQuestion();
        }else {
            Toast.makeText(getContext(), R.string.point_not_set_str, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.set_point_number)
    public void setPointNumber(){
        setNumPoint = numPoint;
        final Dialog d = new Dialog(Objects.requireNonNull(getContext()));
        d.setTitle(R.string.set_point_num_dialog_str);
        d.setContentView(R.layout.dialog_set_poiner);

        NumberPicker numberPicker = d.findViewById(R.id.number_picker);
        Button cancelBtn = d.findViewById(R.id.cancelBtn);
        Button saveBtn = d.findViewById(R.id.saveBtn);

        if (numPoint != 0){
            numberPicker.setValue(numPoint);
        }
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setNumPoint = newVal;
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numPoint = setNumPoint;
                savePointToPref(numPoint);
                setPointBtn.setText(R.string.change_point_number_str);
                d.dismiss();
                activityCommunication.hideUI();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                activityCommunication.hideUI();
            }
        });
        d.show();
    }

    private void savePointToPref(int num){
        SharedPreferences sPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(SHPREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt("point_num", num);
        editor.apply();
    }
}
