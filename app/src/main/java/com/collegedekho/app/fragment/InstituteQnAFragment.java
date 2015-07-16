package com.collegedekho.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Question;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnQuestionAskedListener} interface
 * to handle interaction events.
 * Use the {@link InstituteQnAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteQnAFragment extends Fragment implements TextWatcher, AdapterView.OnItemClickListener {
    //private static final String ARG_COURSES = "courses";

    //Removing Course List for now, too much of a headache.
    //private ArrayList<InstituteCourse> mCourses;

    private OnQuestionAskedListener mListener;
    // private int currentCourseIndex;
    private TextInputLayout til1;
    private TextInputLayout til2;

    public InstituteQnAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InstituteQnAFragment.
     */
    public static InstituteQnAFragment newInstance() {
        //    Bundle args = new Bundle();
        //args.putParcelableArrayList(ARG_COURSES, courseList);
        //    fragment.setArguments(args);
        return new InstituteQnAFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mCourses = getArguments().getParcelableArrayList(ARG_COURSES);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_qna, container, false);
//        Spinner s = (Spinner) rootView.findViewById(R.id.spinner_question_course);
//        s.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,getCourses()));
//        s.setOnItemClickListener(this);
        rootView.findViewById(R.id.button_expert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });
        return rootView;
    }

    public Question validateData(View rootView) {
        View check = rootView.findViewById(R.id.question_title);
        Question q = new Question();
        q.title = ((EditText) check).getText().toString();
        boolean valid = q.title.length() > 0;
        ((EditText) check).addTextChangedListener(this);
        til1 = (TextInputLayout) check.getParent();
        if (!valid)
            til1.setError("Question title cannot be empty.");
        else
            til1.setErrorEnabled(false);
        check = rootView.findViewById(R.id.question_content);
        ((EditText) check).addTextChangedListener(this);
        til2 = (TextInputLayout) check.getParent();
        q.content = ((EditText) check).getText().toString();
        valid &= q.content.length() > 0;
        if (!valid)
            til2.setError("Question text cannot be empty.");
        else
            til2.setErrorEnabled(false);
        //   q.course = ""+mCourses.get(currentCourseIndex).getId();
        if (valid)
            return q;
        return null;
    }

    public void onButtonPressed() {
        View rootView = getView();
        if (rootView != null) {
            Question q = validateData(rootView);
            if (q != null) {
                mListener.onQuestionAsked(q);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnQuestionAskedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnQuestionAskedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (til1 != null) {
            til1.setErrorEnabled(false);
            til2.setErrorEnabled(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //    currentCourseIndex = position;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnQuestionAskedListener {
        void onQuestionAsked(Question question);
    }

    /*
    private ArrayList<String> getCourses(){
        ArrayList<String> courseList = new ArrayList<>();
        for(InstituteCourse c: mCourses){
            courseList.add(c.getName());
        }
        return courseList;
    }*/

}
