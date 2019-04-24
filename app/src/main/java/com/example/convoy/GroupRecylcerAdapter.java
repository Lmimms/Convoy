package com.example.convoy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupRecylcerAdapter extends RecyclerView.Adapter<GroupRecylcerAdapter.ViewHolder> {

    private ArrayList<Group> groups = new ArrayList<>();
    private Context mContext;

    public GroupRecylcerAdapter(ArrayList<Group> groups, Context context) {
        this.groups = groups;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.id.setText(groups.get(i).getId());
        viewHolder.name.setText(groups.get(i).getName());
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavActivity.setCurrentGroupID(groups.get(i).getId());
                Toast.makeText(mContext,"Group changed to " + NavActivity.getCurrentGroupID(),Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id;
        TextView name;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.listGroupName);
            id = itemView.findViewById(R.id.listGroupId);
            parentLayout = itemView.findViewById(R.id.listParent);
        }
    }
}
