package co.tiagoaguiar.codelab.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import co.tiagoaguiar.codelab.myapplication.ImcActivity;
import co.tiagoaguiar.codelab.myapplication.OnAdapterItemClickListener;
import co.tiagoaguiar.codelab.myapplication.R;
import co.tiagoaguiar.codelab.myapplication.Register;
import co.tiagoaguiar.codelab.myapplication.SqlHelper;
import co.tiagoaguiar.codelab.myapplication.TmbActivity;

public class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder> implements OnAdapterItemClickListener {
    private List<Register> registerItems;
    private Context context;


    public ListCalcAdapter(Context context, List<Register> registerItems) {
        this.registerItems = registerItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListCalcViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_expandable_list_item_1,
                                parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ListCalcViewHolder holder, int position) {
        Register registerCurrent = registerItems.get(position);
        holder.bind(registerCurrent, this);
    }

    @Override
    public int getItemCount() {
        return registerItems.size();
    }

    @Override
    public void onClick(int id, String type) {
        // verificar qual tipo de dado deve ser EDITADO na tela seguinte
        switch (type) {
            case "imc":
                Intent intent = new Intent(context, ImcActivity.class);
                intent.putExtra("updateId", id);
                context.startActivity(intent);
                break;
            case "tmb":
                Intent i = new Intent(context, TmbActivity.class);
                i.putExtra("updateId", id);
                context.startActivity(i);
                break;
        }
    }

    @Override
    public void onLongClick(int position, String type, int id) {
        // evento de exclusão (PERGUNTAR ANTES PARA O USUARIO)
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.delete_message))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    new Thread(() -> {
                        SqlHelper sqlHelper = SqlHelper.getInstance(context);
                        long calcId = sqlHelper.removeItem(type, id);


                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (calcId > 0) {
                                    Toast.makeText(context, R.string.calc_removed, Toast.LENGTH_LONG).show();
                                    registerItems.remove(position);
                                    notifyDataSetChanged();
                                }
                            }
                        });

                    }).start();

                })
                .create();

        alertDialog.show();

    }


    public class ListCalcViewHolder extends RecyclerView.ViewHolder {
        public ListCalcViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Register data, final OnAdapterItemClickListener onItemClickListener) {
            String formatDate = "";
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
                Date dateSaved = simpleDateFormat.parse(data.createdDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
                formatDate = dateFormat.format(dateSaved);

            } catch (Exception e) {
            }

            String stDate = itemView.getContext().getString(R.string.list_response, data.response, formatDate);

            itemView.setOnClickListener(view -> {
                onItemClickListener.onClick(data.id, data.type);
            });

            ((TextView) itemView).setText(stDate);
            itemView.setOnLongClickListener(view -> {
                onItemClickListener.onLongClick(getAdapterPosition(), data.type, data.id);
                return false;
            });
        }
    }
}
