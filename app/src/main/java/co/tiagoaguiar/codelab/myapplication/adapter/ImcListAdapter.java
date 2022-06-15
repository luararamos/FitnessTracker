package co.tiagoaguiar.codelab.myapplication.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.tiagoaguiar.codelab.myapplication.R;
import co.tiagoaguiar.codelab.myapplication.Register;

public class ImcListAdapter extends RecyclerView.Adapter<ImcListAdapter.ImcListViewHolder> {
    private List<Register> registerItems;

    public ImcListAdapter(List<Register> registerItems) {
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
            String formatDate = "";
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
                Date dateSaved = simpleDateFormat.parse(item.createdDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
                formatDate = dateFormat.format(dateSaved);

            } catch (Exception e) {
            }

            String stDate = itemView.getContext().getString(R.string.list_response, item.response, formatDate );

            ((TextView) itemView).setText( stDate);
        }
    }
}
