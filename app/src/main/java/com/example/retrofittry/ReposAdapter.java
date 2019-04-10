package com.example.retrofittry;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofittry.model.Repo;

import java.util.List;

public class ReposAdapter  extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private static final String TAG = ReposAdapter.class.getSimpleName();

    private Context context;
    private List<Repo> list;
    private AdapterCallback mAdapterCallback;

    public ReposAdapter(Context context, List<Repo> list, AdapterCallback mAdapterCallback) {
        this.context = context;
        this.list = list;
        this.mAdapterCallback = mAdapterCallback;
    }

    @NonNull
    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repo,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposAdapter.ViewHolder viewHolder, int i) {

        Repo item = list.get(i);

        String name = item.getName();
        String description = item.getDescription();

        viewHolder.tvName.setText(name);
        viewHolder.tvDesc.setText(description);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvDesc;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);

        }
    }
    public interface AdapterCallback {
        void onRowClicked(int position);
    }
}
