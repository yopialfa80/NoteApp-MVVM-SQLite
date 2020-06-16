package com.github.noteapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends ListAdapter<Note, RecycleAdapter.ViewHolder> {

    private OnItemClickListener listener;
    public RecycleAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(Note oldItem, Note newItem) {
            return oldItem.getTittle().equals(newItem.getTittle()) &&
                    oldItem.getMaincontent().equals(newItem.getMaincontent()) &&
                    oldItem.getDate().equals(newItem.getDate());
        }
    };
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.txtTittle.setText(currentNote.getTittle());
        holder.txtMainContent.setText(currentNote.getMaincontent());
        holder.txtDate.setText(String.valueOf(currentNote.getDate()));
    }
    public Note getNoteAt(int position) {
        return getItem(position);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTittle;
        private TextView txtMainContent;
        private TextView txtDate;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTittle = itemView.findViewById(R.id.txtTittle);
            txtMainContent = itemView.findViewById(R.id.txtMainContent);
            txtDate = itemView.findViewById(R.id.txtDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

