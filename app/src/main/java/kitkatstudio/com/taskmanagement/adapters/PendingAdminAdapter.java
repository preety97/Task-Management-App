package kitkatstudio.com.taskmanagement.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.customclass.PendingContent;
import kitkatstudio.com.taskmanagement.customclass.PendingAdminContent.PendingAdminContentItem;

import java.util.List;


public class PendingAdminAdapter extends RecyclerView.Adapter<PendingAdminAdapter.ViewHolder> {

    private final List<PendingAdminContentItem> mValues;
    private Context context;

    public PendingAdminAdapter(List<PendingAdminContentItem> items, Context listener) {
        mValues = items;
        context = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pendingadmin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.user_name.setText(mValues.get(position).user_name);
        holder.task.setText(mValues.get(position).task);
        holder.description.setText(mValues.get(position).description);
        holder.start_date.setText("Start Date : " + mValues.get(position).start_date);
        holder.end_date.setText("End Date : " + mValues.get(position).end_date);
        holder.percentage.setText(mValues.get(position).percentage);
        holder.assigned_by.setText("Assigned By : " + mValues.get(position).assigned_by);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView user_name;
        public final TextView task;
        public final TextView description;
        public final TextView start_date;
        public final TextView end_date;
        public final TextView percentage;
        public final TextView assigned_by;
        public PendingAdminContentItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            user_name = (TextView) view.findViewById(R.id.user_name);
            task = (TextView) view.findViewById(R.id.task);
            description= (TextView) view.findViewById(R.id.description);
            start_date= (TextView) view.findViewById(R.id.start_date);
            end_date= (TextView) view.findViewById(R.id.end_date);
            percentage= (TextView) view.findViewById(R.id.percentage);
            assigned_by = (TextView) view.findViewById(R.id.assigned_by);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + end_date.getText() + "'";
        }
    }
}