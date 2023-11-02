package com.example.project_prm392;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_prm392.entity.Book;
import com.example.project_prm392.entity.Tag;
import com.example.project_prm392.entity.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView rcvCategory;
    private TagAdapter tagAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rcvCategory = view.findViewById(R.id.rcv_category);
        tagAdapter = new TagAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvCategory.setLayoutManager(linearLayoutManager);
        tagAdapter.setData(getListCategory());
        rcvCategory.setAdapter(tagAdapter);
        return view;
    }

    private List<Tag> getListCategory() {
        List<Tag> listCategory = new ArrayList<>();
        List<Book> listBook = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("book").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBook.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Book book = snap.getValue(Book.class);
                    listBook.add(book);
                    listCategory.clear();
                    listCategory.add(new Tag("cate1", listBook));
                    listCategory.add(new Tag("cate2", listBook));
                    listCategory.add(new Tag("cate3", listBook));
                    listCategory.add(new Tag("cate4", listBook));
                    listCategory.add(new Tag("cate5", listBook));
                    listCategory.add(new Tag("cate6", listBook));
                    listCategory.add(new Tag("cate7", listBook));
                    listCategory.add(new Tag("cate8", listBook));
                    listCategory.add(new Tag("cate9", listBook));
                    listCategory.add(new Tag("cate10", listBook));
                }
                tagAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return listCategory;
    }
}



//        listBook.add(new Book(1,"Book1","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(2,"Book2","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(3,"Book3","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(4,"Book4","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(5,"Book5","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(6,"Book6","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(7,"Book7","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(8,"Book8","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(9,"Book9","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//        listBook.add(new Book(10,"Book10","Aaa","hung",1,"https://i.truyenvua.com/ebook/190x247/boku-no-hero-academia_1552459650.jpg?gt=hdfgdfg&mobile=2",1));
//
//        listCategory.add(new Category("cate1", listBook));
//        listCategory.add(new Category("cate2", listBook));
//        listCategory.add(new Category("cate3", listBook));
//        listCategory.add(new Category("cate4", listBook));
//        listCategory.add(new Category("cate5", listBook));
//        listCategory.add(new Category("cate6", listBook));
//        listCategory.add(new Category("cate7", listBook));
//        listCategory.add(new Category("cate8", listBook));
//        listCategory.add(new Category("cate9", listBook));
//        listCategory.add(new Category("cate10", listBook));
//
//
//        return listCategory;


