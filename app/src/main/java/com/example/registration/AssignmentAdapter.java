package com.example.registration;

import android.content.Context;
import android.content.Intent;
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


public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private ArrayList<ProjectsDirectoryClass> project_directory_items;
    private Context context;
    private String dirName;
    private DataBaseProjects dataBaseProjects;
    private ArrayList<ProjectsClass> projectsClasses;

    public ArrayList<ProjectsDirectoryClass> getProject_directory_items(ArrayList<ProjectsDirectoryClass> projectsDirectoryClasses) {
        return project_directory_items;
    }

    public void setProject_directory_items(ArrayList<ProjectsDirectoryClass> project_directory_items) {
        this.project_directory_items = project_directory_items;
    }

    public AssignmentAdapter(Context context, String dirNameToGet, DataBaseProjects dataBaseProjects) {
        this.context = context;
        this.dirName = dirNameToGet;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_cardsview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!dirName.equals(project_directory_items.get(position).getDirectoryName())) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            holder.direct_txt.setText(project_directory_items.get(position).getDirectoryName());
            holder.assignment_txt.setText(project_directory_items.get(position).getFileName());
            Glide.with(context)
                    .asBitmap()
                    .load(project_directory_items.get(position).getDrawableForFile())
                    .into(holder.direct_img);
            {
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Assignment_Edit_Text.class);
                        intent.putExtra("passDirName", project_directory_items.get(position).getDirectoryName());
                        intent.putExtra("passFileName", project_directory_items.get(position).getFileName());
                        intent.putExtra("passProjectName", project_directory_items.get(position).getProjectsClassName());
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
                                dataBaseProjects.removeAssignment(project_directory_items.get(position).getFileName());
                                Toast.makeText(getContext(), "Assignment Deleted", Toast.LENGTH_SHORT).show();
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
        private TextView assignment_txt;
        private TextView remove_textBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.assignments_cards);
            direct_img = itemView.findViewById(R.id.img_assignment);
            direct_txt = itemView.findViewById(R.id.assignmentDirectory);
            assignment_txt = itemView.findViewById(R.id.assignment_name_dir);
            remove_textBtn = itemView.findViewById(R.id.remove_assignmentBtn);
        }
    }
}
