package kitkatstudio.com.taskmanagement.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kitkatstudio.com.taskmanagement.R;
import kitkatstudio.com.taskmanagement.customclass.ShowMessageContent.ShowMessageItem;

import java.util.List;

public class ShowMessageAdapter extends RecyclerView.Adapter<ShowMessageAdapter.ViewHolder> {

    private final List<ShowMessageItem> mValues;
    Context context;

    public ShowMessageAdapter(List<ShowMessageItem> items, Context listener) {
        mValues = items;
        context = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_showmessage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.task.setText(mValues.get(position).task);
        holder.description.setText(mValues.get(position).description);
        holder.message.setText("Message : " + mValues.get(position).message);

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
        public final TextView message;
        public ShowMessageItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            task = (TextView) view.findViewById(R.id.task);
            description = (TextView) view.findViewById(R.id.description);
            message = (TextView) view.findViewById(R.id.message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + message.getText() + "'";
        }
    }
}
