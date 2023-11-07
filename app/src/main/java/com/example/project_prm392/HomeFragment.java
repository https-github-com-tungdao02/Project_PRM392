package com.example.project_prm392;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.project_prm392.entity.Book;
import com.example.project_prm392.entity.Photo;
import com.example.project_prm392.entity.Tag;
import com.example.project_prm392.entity.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {

    private RecyclerView rcvCategory;
    private TagAdapter tagAdapter;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;

    private List<Photo> mListPhoto;
    private Timer mTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rcvCategory = view.findViewById(R.id.rcv_category);
        viewPager = view.findViewById(R.id.viewpager);

        circleIndicator = view.findViewById(R.id.circle_indicator);

        mListPhoto = getListPhoto();

        photoAdapter = new PhotoAdapter(requireContext(), mListPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);

        tagAdapter = new TagAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        rcvCategory.setLayoutManager(linearLayoutManager);
        rcvCategory.setAdapter(tagAdapter);

        getListCategory();

        autoSlideImage();

        return view;
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.banner));
        list.add(new Photo(R.drawable.banner2));
        list.add(new Photo(R.drawable.banner3));
        return list;
    }


    private void autoSlideImage(){
        if(mListPhoto == null || mListPhoto.isEmpty() || viewPager == null ){
            return;
        }

        if(mTimer ==null ){
            mTimer = new Timer();
        }

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() -1;
                        if(currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);

                        }else {
                            viewPager.setCurrentItem(0);
                        }

                    }
                });
            }
        },500,3000);



    }

    private void getListCategory() {
        List<Book> listBook = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("book").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listBook.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Book book = snap.getValue(Book.class);
                    listBook.add(book);
                }

                List<Tag> listCategory = new ArrayList<>();
                listCategory.add(new Tag("Giải trí", listBook));
                listCategory.add(new Tag("Truyện", listBook));
                listCategory.add(new Tag("Đời sống", listBook));
                listCategory.add(new Tag("Thể thao", listBook));
                listCategory.add(new Tag("Tiểu thuyết", listBook));

                tagAdapter.setData(listCategory);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Xử lý khi có lỗi
            }
        });
    }
}


