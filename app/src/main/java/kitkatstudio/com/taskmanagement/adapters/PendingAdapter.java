package kitkatstudio.com.taskmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kitkatstudio.com.taskmanagement.R;

import kitkatstudio.com.taskmanagement.customclass.PendingContent.PendingItem;
import kitkatstudio.com.taskmanagement.fragments.MessageFragment;
import kitkatstudio.com.taskmanagement.fragments.PercentageFragment;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    private final List<PendingItem> mValues;
    private Context context;
    private FragmentManager fragmentManager;

    public PendingAdapter(List<PendingItem> items, Context listener, FragmentManager fm) {
        fragmentManager = fm;
        mValues = items;
        context = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mItem = mValues.get(position);
        holder.task.setText(mValues.get(position).task);
        holder.description.setText(mValues.get(position).description);
        holder.start_date.setText("Start Date : " + mValues.get(position).start_date);
        holder.end_date.setText("End Date : " + mValues.get(position).end_date);
        holder.percentage.setText(mValues.get(position).percentage);
        holder.assigned_by.setText("Assigned By : " + mValues.get(position).assigned_by);

        final String id = mValues.get(position).id;
        final String task= mValues.get(position).task;
        final String description= mValues.get(position).description;
        final String start_date= mValues.get(position).start_date;
        final String end_date= mValues.get(position).end_date;
        final String percentage= mValues.get(position).percentage;
        final String assigned_by = mValues.get(position).assigned_by;

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragmentManager.beginTransaction().replace(R.id.content, new PercentageFragment()).commit();
                try {
                    Fragment fragment = null;
                    Class fragmentClass;

                    Bundle bundle = new Bundle();
                    bundle.putString(Intent.EXTRA_TEXT, id);
                    bundle.putString("task", task);
                    bundle.putString("description", description);
                    bundle.putString("start_date", start_date);
                    bundle.putString("end_date", end_date);
                    bundle.putString("percentage", percentage);
                    bundle.putString("assigned_by", assigned_by);

                    fragmentClass = PercentageFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        fragment.setArguments(bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                }
                catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
            }
        });

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragmentManager.beginTransaction().replace(R.id.content, new PercentageFragment()).commit();
                try {
                    Fragment fragment = null;
                    Class fragmentClass;

                    Bundle bundle = new Bundle();
                    bundle.putString(Intent.EXTRA_TEXT, id);
                    bundle.putString("task", task);
                    bundle.putString("description", description);
                    bundle.putString("start_date", start_date);
                    bundle.putString("end_date", end_date);
                    bundle.putString("percentage", percentage);
                    bundle.putString("assigned_by", assigned_by);

                    fragmentClass = MessageFragment.class;
                    try {
                        fragment = (Fragment) fragmentClass.newInstance();
                        fragment.setArguments(bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                }
                catch (Exception e){
                    Log.e("Error",e.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView task;
        public final TextView description;
        public final TextView start_date;
        public final TextView end_date;
        public final TextView percentage;
        public final TextView assigned_by;
        public PendingItem mItem;
        public ImageView imageView;
        public ImageView message;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            task = (TextView) view.findViewById(R.id.task);
            description= (TextView) view.findViewById(R.id.description);
            start_date= (TextView) view.findViewById(R.id.start_date);
            end_date= (TextView) view.findViewById(R.id.end_date);
            percentage= (TextView) view.findViewById(R.id.percentage);
            assigned_by = (TextView) view.findViewById(R.id.assigned_by);
            imageView = view.findViewById(R.id.imageView);
            message = view.findViewById(R.id.message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + end_date.getText() + "'";
        }
    }
}
