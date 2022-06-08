package co.tiagoaguiar.codelab.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.tiagoaguiar.codelab.myapplication.Register;

public class ImcListAdapter extends RecyclerView.Adapter<ImcListAdapter.ImcListViewHolder> {
    private List<Register> registerItems;

    public ImcListAdapter(List<Register> registerItems){
        this.registerItems = registerItems;
    }

    @NonNull
    @Override
    public ImcListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImcListViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_expandable_list_item_1,
                                parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ImcListViewHolder holder, int position) {
        Register registerCurrent = registerItems.get(position);
        holder.bind(registerCurrent);
    }

    @Override
    public int getItemCount() {
        return registerItems.size();
    }

    public class ImcListViewHolder extends RecyclerView.ViewHolder {
        public ImcListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Register item) {
            TextView txtImc = itemView.findViewById(android.R.id.text1);
            txtImc.setText(String.valueOf(item.getResponse()));
        }
    }
}
