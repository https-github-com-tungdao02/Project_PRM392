package com.example.project_prm392;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_prm392.entity.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private List<History> list;
    private List<History> selected;
    boolean isSelectAll= false;
    public List<History> getSelected() {
        return selected;
    }

    public boolean isSelectAll() {
        return isSelectAll;
    }

    public void setSelectAll(boolean selectAll) {
        isSelectAll = selectAll;
        notifyDataSetChanged();
    }


    public void setSelected(List<History> selected) {
        this.selected = selected;
    }

    public HistoryAdapter(List<History> list, OnDeleteClickListener onDeleteClickListener) {
        this.list = list;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    private OnDeleteClickListener onDeleteClickListener;
    public interface OnDeleteClickListener {
        void onDeleteClick(History history);
    }
    public interface OnAddClickListener{
        void onAddClick(History history);
    }
    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        return new  HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        History history= list.get(position);
        Glide.with(holder.itemView).load(history.getBook().getImage()).into(holder.imv_img_book);
        holder.tv_book_name_history.setText(history.getBook().getName());
        holder.tv_book_date_history.setText(history.getTime()+"");
//        holder.tv_book_page_history.setText(history.getNumber_page().getNumber()+"");
        holder.btn_delete_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onDeleteClickListener.onDeleteClick(history);
            }
        });

        if (isSelectAll){
            holder.cb_id_history.setChecked(true);
            selected.add(history);
        }else {
            holder.cb_id_history.setChecked(false);
            selected.remove(history);
        }
        holder.cb_id_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cb_id_history.isChecked()){
                    selected.add(history);
                }else {
                    selected.remove(history);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class HistoryHolder extends RecyclerView.ViewHolder {
        TextView tv_book_name_history,tv_book_page_history,tv_book_date_history;
        CheckBox cb_id_history;
        Button btn_delete_history;
        ImageView imv_img_book;
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            tv_book_name_history=itemView.findViewById(R.id.tv_book_name_history);
            tv_book_page_history=itemView.findViewById(R.id.tv_book_page_history);
            tv_book_date_history=itemView.findViewById(R.id.tv_book_date_history);
            cb_id_history=itemView.findViewById(R.id.cb_id_history);
            btn_delete_history=itemView.findViewById(R.id.btn_delete_history);
            imv_img_book=itemView.findViewById(R.id.imv_img_book);


        }
    }
}
