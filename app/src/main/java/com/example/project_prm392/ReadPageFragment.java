package com.example.project_prm392;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReadPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadPageFragment newInstance(String param1, String param2) {
        ReadPageFragment fragment = new ReadPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_page, container, false);
        TextView pageContentTextView = view.findViewById(R.id.pageContentTextView);

        // Đọc nội dung trang từ Firebase Realtime Database và hiển thị trong TextView
        // Điều này cần được thay thế bằng mã lấy dữ liệu từ Firebase thích hợp cho ứng dụng của bạn

        // Ví dụ:
        String pageContent = "morhter dsakdjsakldjsakl";
        pageContentTextView.setText(pageContent);

        return view;
    }
}