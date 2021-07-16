package com.driver_Trivia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.driver_Trivia.R;

public class TriviaFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trivia, container, false);
    }



    public static TriviaFragment newInstance(){
        TriviaFragment fragment = new TriviaFragment();
        Bundle args  = new Bundle();
        fragment.setArguments(args);
        return fragment;
}

}