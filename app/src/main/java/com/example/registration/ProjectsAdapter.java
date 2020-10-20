package com.example.registration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {
    private static final String TAG = "ProjectsAdapter";
    private ArrayList<ProjectsClass> project_items;
    private Context context;
    private DataBaseProjects dataBaseProjects;
    private RelativeLayout layout;
    private EditText pin_code;
    private Button button;
    private TextView textView;

    public ArrayList<ProjectsClass> getProject_items() {
        return project_items;
    }

    public void setProject_items(ArrayList<ProjectsClass> project_items) {
        this.project_items = project_items;
    }

    public ProjectsAdapter(Context context, DataBaseProjects dataBaseProjects, RelativeLayout layout, EditText pin_code, Button button, TextView textView) {
        this.context = context;
        this.dataBaseProjects = dataBaseProjects;
        this.layout = layout;
        this.pin_code = pin_code;
        this.button = button;
        this.textView = textView;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dataBaseProjects.containsProject(project_items.get(position))) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            holder.txtProject_name.setText(project_items.get(position).getName());
            holder.txtDate.setText(project_items.get(position).getDates());
            holder.txtID.setText(String.valueOf(project_items.get(position).getId()));
            Glide.with(context)
                    .asBitmap()
                    .load(project_items.get(position).getDrawable())
                    .into(holder.imgProject_item);
            {
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(dataBaseProjects.checkPin_code(project_items.get(position).getName(), pin_code.getText().toString())) {
                                    Intent intent = new Intent(context, Project_Directories.class);
                                    intent.putExtra("projectNameToGet", project_items.get(position).getName());
                                    layout.setVisibility(View.GONE);
                                    context.startActivity(intent);
                                } else {
                                    textView.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
                holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        holder.removeBtn.setVisibility(View.VISIBLE);
                        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Are you sure you want to delete " + project_items.get(position).getName() + "?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dataBaseProjects.removeOne(project_items.get(position));
                                        Toast.makeText(getContext(), "Project Deleted", Toast.LENGTH_SHORT).show();
                                        holder.removeBtn.setVisibility(View.GONE);
                                        notifyItemRemoved(position);

                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.create().show();

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
        return project_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView imgProject_item;
        private TextView txtProject_name;
        private TextView txtDate;
        private TextView txtID;
        private TextView removeBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent_projects);
            imgProject_item = itemView.findViewById(R.id.img_project);
            txtProject_name = itemView.findViewById(R.id.project_name);
            txtDate = itemView.findViewById(R.id.date);
            txtID = itemView.findViewById(R.id.id_projects);
            removeBtn = itemView.findViewById(R.id.remove_card_project);
        }
    }
}
