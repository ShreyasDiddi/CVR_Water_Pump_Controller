package com.a6studios.cvr_waterpumpcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.a6studios.cvr_waterpumpcontrol.database.DatabaseHelper;
import com.a6studios.cvr_waterpumpcontrol.database.Pump;

import java.util.List;

public class PumpAdapter extends RecyclerView.Adapter<PumpAdapter.BlogViewHolder> {

    private Context context;
    List<Pump> pumpsList;
    private DatabaseHelper db;
    MainActivity a;

    public class BlogViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;
        public ImageButton delete;
        public ImageButton edit;

        public BlogViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            delete = view.findViewById(R.id.delete);
            edit = view.findViewById(R.id.edit);
        }
    }

    public PumpAdapter(Context context,MainActivity a, List<Pump> pumpsList, DatabaseHelper db) {
        this.context = context;
        this.pumpsList = pumpsList;
        this.db = db;
        this.a = a;
    }

    @Override
    public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pump_item, parent, false);

        return new BlogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BlogViewHolder holder, final int position) {
        Pump pumps = pumpsList.get(position);

        holder.title.setText(pumps.getLabel());

        holder.content.setText(pumps.getPhno());

        holder.itemView.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = pumpsList.get(position).getLabel();
                String c = pumpsList.get(position).getPhno();
                String p =  String.valueOf(position);
                a.edit(t,c,p);
            }
        });

        holder.itemView.findViewById(R.id.on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.send(pumpsList.get(position).getPhno(),"ON");
            }
        });
        holder.itemView.findViewById(R.id.off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.send(pumpsList.get(position).getPhno(),"OFF");
            }
        });

        holder.itemView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.deletePump(pumpsList.get(position));
                                pumpsList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,pumpsList.size());
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return pumpsList.size();
    }



}
