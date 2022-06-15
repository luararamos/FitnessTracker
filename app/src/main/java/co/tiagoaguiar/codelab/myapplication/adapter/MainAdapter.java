package co.tiagoaguiar.codelab.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.tiagoaguiar.codelab.myapplication.MainItem;
import co.tiagoaguiar.codelab.myapplication.OnItemClickListener;
import co.tiagoaguiar.codelab.myapplication.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private List<MainItem> mainItems;
    private OnItemClickListener listener;

    public MainAdapter(List<MainItem> mainItems) {
        this.mainItems = mainItems;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.main_item, parent, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        MainItem mainItemCurrent = mainItems.get(position);
        holder.bind(mainItemCurrent);

    }

    @Override
    public int getItemCount() {
        return mainItems.size();
    }

    // Entenda como sendo a VIEW DA CELULA que está dentro da RecyclerView
    protected class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(MainItem item) {
            TextView txtName = itemView.findViewById(R.id.item_txt_name);
            ImageView imgIcon = itemView.findViewById(R.id.item_img_icon);
            LinearLayout btnImc = (LinearLayout) itemView.findViewById(R.id.btn_imc);

            btnImc.setOnClickListener(v -> {
                listener.onClick(item.getId());
            });

            txtName.setText(item.getTextStringId());
            imgIcon.setImageResource(item.getDrawableId());
            btnImc.setBackgroundColor(item.getColor());
        }
    }
}
