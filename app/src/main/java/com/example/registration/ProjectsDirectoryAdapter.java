package com.example.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ProjectsDirectoryAdapter extends RecyclerView.Adapter<ProjectsDirectoryAdapter.ViewHolder> {
    private ArrayList<ProjectsDirectoryClass> project_directory_items;
    private Context context;
    private String projectNameForDirectory;
    private static final String TAG = "ProjectsDirectoryAdapter";
    private DataBaseProjects dataBaseProjects;
    private  ArrayList<ProjectsClass> projectsClasses;

    public ArrayList<ProjectsDirectoryClass> getProject_directory_items() {
        return project_directory_items;
    }

    public void setProject_directory_items(ArrayList<ProjectsDirectoryClass> project_directory_items) {
        this.project_directory_items = project_directory_items;
    }

    public ProjectsDirectoryAdapter(Context context, String projectNameToGet, DataBaseProjects dataBaseProjects) {
        this.context = context;
        this.projectNameForDirectory = projectNameToGet;
        this.dataBaseProjects = dataBaseProjects;
        this.projectsClasses = (ArrayList<ProjectsClass>) dataBaseProjects.getEveryList();
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_directory_cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!projectNameForDirectory.equals(project_directory_items.get(position).getProjectsClassName())) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            holder.direct_txt.setText(project_directory_items.get(position).getDirectoryName());
            holder.project_txt.setText(project_directory_items.get(position).getProjectsClassName());
            Glide.with(context)
                    .asBitmap()
                    .load(project_directory_items.get(position).getDrawableForDir())
                    .into(holder.direct_img);
            {
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Assignments_Activity.class);
                        intent.putExtra("dirNameForFile", project_directory_items.get(position).getDirectoryName());
                        context.startActivity(intent);

                    }
                });
                holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        holder.remove_textBtn.setVisibility(View.VISIBLE);
                        holder.remove_textBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dataBaseProjects.removeOne(project_directory_items.get(position).getDirectoryName());
                                Toast.makeText(getContext(), "Directory Deleted", Toast.LENGTH_SHORT).show();
                                holder.remove_textBtn.setVisibility(View.GONE);
                                notifyItemRemoved(position);
                            }
                        });
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return project_directory_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView direct_img;
        private TextView direct_txt;
        private TextView project_txt;
        private TextView remove_textBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.directory_cards);
            direct_img = itemView.findViewById(R.id.directory_img);
            direct_txt = itemView.findViewById(R.id.directory_name);
            project_txt = itemView.findViewById(R.id.project_name_in_directory);
            remove_textBtn = itemView.findViewById(R.id.remove_textBtn);
        }
    }
}
