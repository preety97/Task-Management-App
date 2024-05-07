package kitkatstudio.com.taskmanagement.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.customclass.CompletedContent.CompletedItem;

import java.util.List;

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.ViewHolder> {

    private final List<CompletedItem> mValues;
    Context context;

    public CompletedAdapter(List<CompletedItem> items, Context listener) {
        mValues = items;
        context = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_completed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.task.setText(mValues.get(position).task);
        holder.description.setText(mValues.get(position).description);
        holder.start_date.setText("Start Date : " + mValues.get(position).start_date);
        holder.end_date.setText("End Date : " + mValues.get(position).end_date);
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
        public final TextView task;
        public final TextView description;
        public final TextView start_date;
        public final TextView end_date;
        public final TextView assigned_by;
        public CompletedItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            task = (TextView) view.findViewById(R.id.task);
            description= (TextView) view.findViewById(R.id.description);
            start_date= (TextView) view.findViewById(R.id.start_date);
            end_date= (TextView) view.findViewById(R.id.end_date);
            assigned_by = (TextView) view.findViewById(R.id.assigned_by);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + end_date.getText() + "'";
        }
    }
}
