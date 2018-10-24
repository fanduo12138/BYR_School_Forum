package com.bupt.forum.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.*;
import android.content.Intent;

import com.bupt.forum.R;
import com.bupt.forum.activity.SearchActivity;
import com.bupt.forum.metadata.BoardList;


public class SearchFragment extends Fragment implements View.OnClickListener{

    private View view;
    private EditText SearchView;
    private Spinner SearchBoardView;
    private Button SearchButton;
    private BoardList map = new BoardList();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                String board_info = map.getValue(SearchBoardView.getSelectedItem().toString());
                String search_content = SearchView.getText().toString();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("Board_Info", board_info);
                intent.putExtra("Search_Content", search_content);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        SearchView = (EditText) view.findViewById(R.id.search_text);
        SearchBoardView = (Spinner) view.findViewById(R.id.search_board);
        SearchButton = (Button) view.findViewById(R.id.search_button);

        SearchButton.setOnClickListener(this);

        return view;
    }
}
