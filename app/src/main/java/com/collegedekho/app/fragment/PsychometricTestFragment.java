package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.PsychometricTestAdapter;
import com.collegedekho.app.entities.PsychometricTestQuestion;

import java.util.ArrayList;

/**
 * Created by Bashir on 11/12/15.
 */
public class PsychometricTestFragment extends BaseFragment implements PsychometricTestAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ArrayList<PsychometricTestQuestion> mQuestionList;
    private ArrayList<PsychometricTestQuestion> subList;

//    String[] questions = new String[]{"Are you the one who can understand the feelings and emotions of your near and dear ones?", "Do you enjoy solving new puzzles that requires multidimensional thought process?", "During your school/college have you taken lead in organizing project, activity or event?",
//            "Given a situation can you visualize it and can sketch it.", "Do you have an excellent command over languages that you speak or write. Always Sometimes Never", "Have you actively participated in debate competition organized in school or college .", "Are you the one who list out and organize day to day activities systematically.",
//            "When you help someone do you do not do that for the sake of something in return or for self satisfaction?", "Are you very fast in mathematical calculations like multiplication, division and finding percentages without using pen and paper?", "Under adverse situations will you do something which has been your biggest threat in life?",
//            "If somebody describes you a situation or location can you imagine it clearly?", "Do you have a natural flair for writing and use complex words when you write with lot of ease. ",
//            "Are you the one who does not easily get convinced till the time you analyze the situation rationally from all the perspectives .", "Do you keep a track of money that you spend. ", "Are you compassionate and kind by nature?", "Are you the first one to try and solve complex problems in the class?",
//            "Given a choice you prefer to plan an event of activity rather than participating in it?", "Do you take initiative in mending mechanical gadgets like clocks or fans when they stop working at home?", "Are you able to apply the concepts of Maths and Science in tackling real life problems?",
//            "If I am convinced about something, I put my 100% efforts for its accomplishment?", "You can think out of the box and can come up with solution for any complex problems?", "Do you enjoy writing whatever is in my mind and are you good in it.",
//            "You do not believe the other person fully till he or she gives a valid logic for that. ", "Do you like spending time working on electronic gadgets?", "Do you feel delighted to let someone else take the credit in group activities?", "If you encounter something new of your interest, do you take extra efforts to understand that?", "Are you active on social networking sites.",
//            "Do you prefer to actively host for parties at workplace, home or club?", "Can you sit in front of computers for hours at a stretch for cracking the logic behind any program. ", "Do you have the capability of getting in the shoes of others and understand their point of view rather then only considered about proving your point.", "Are you good in cracking the logic behind any problem? ",
//            "During your childhood your friends naturally expected you to be the leader.", "Do you like reading novels or newspaper during your spare time .", "Have you ever filed a petition or RTI?", "Can you solve Maths questions effortlessly? ",
//            "Are you confident can no one beat you in arguments. Always Sometimes Never", "Whenever you are free do you prefer to pen down your thoughts by drawing or sketching ? ", "Are you the one who prefer solving most of the problems by thinking in a step by step manner. Always Sometimes Never", "Given a task that involves 24*7 efforts i.e immense hard work you are the one who will not run away from that but on the contrary fight till its accomplishment? ", "While taking crucial decisions do you refer it with your close associates."};
private static PsychometricTestFragment.OnNextPageListener mNextListener;
    public static PsychometricTestFragment newInstance(ArrayList<PsychometricTestQuestion> questionsList,PsychometricTestFragment.OnNextPageListener listener) {
        Bundle args = new Bundle();
        mNextListener=listener;
        args.putParcelableArrayList("psychometric_questions_list", questionsList);
        PsychometricTestFragment fragment = new PsychometricTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mQuestionList = bundle.getParcelableArrayList("psychometric_questions_list");
            int id=bundle.getInt("id");
            int start=id*4;
            int end=start+4;
            if(end>mQuestionList.size()){
                end=mQuestionList.size()-start;
            }
            subList = new ArrayList<>(mQuestionList.subList(start, end));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.psychometric_test_fragment, container, false);
        layoutManager = new LinearLayoutManager(getActivity());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.psychometric_test_recycler);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        PsychometricTestAdapter adapter = new PsychometricTestAdapter(getActivity(), subList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
//                submitPsychometricTest();
                break;
            default:
                break;
        }
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onItemClicked(int position) {
        mNextListener.gotoNext();
    }

    public interface OnNextPageListener{
        public void gotoNext();
    }
}
