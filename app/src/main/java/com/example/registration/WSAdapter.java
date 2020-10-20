package com.example.registration;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class WSAdapter extends RecyclerView.Adapter<WSAdapter.ViewHolder> {
    private ArrayList<WS_items> ws_items;
    private Context context;

    public ArrayList<WS_items> getWs_items() {
        return ws_items;
    }

    public void setWs_items(ArrayList<WS_items> ws_items) {
        this.ws_items = ws_items;
    }

    public WSAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ws_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtWS_item.setText(ws_items.get(position).getWs_item());
        Glide.with(context)
                .asBitmap()
                .load(ws_items.get(position).getDrawable())
                .into(holder.imgWS_item);

        switch(ws_items.get(position).getWs_item()) {
            case "Projects":
                holder.parent.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                      Intent intent = new Intent(context, Projects_Activity.class);
                      context.startActivity(intent);
                 }
                });
                break;
            case "Tasks":
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AllTasksActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Goals_Activity.class);
                        context.startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return ws_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView imgWS_item;
        private TextView txtWS_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgWS_item = itemView.findViewById(R.id.imgWS_item);
            txtWS_item = itemView.findViewById(R.id.txtWS_item);
        }
    }
}
