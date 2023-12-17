package org.tensorflow.lite.coin.detection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.coin.detection.FirstFragment;

import java.util.List;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.NumberViewHolder> {

    private List<FirstFragment.NumberModel> numberList;

    public NumbersAdapter(List<FirstFragment.NumberModel> numberList) {
        this.numberList = numberList;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_number, parent, false);
        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        FirstFragment.NumberModel numberModel = numberList.get(position);
        holder.bind(numberModel);
    }

    @Override
    public int getItemCount() {
        return numberList.size();
    }

    // ViewHolder 클래스 정의
    public static class NumberViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNumber;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
        }

        public void bind(FirstFragment.NumberModel numberModel) {
            textViewNumber.setText(numberModel.getNumber());
        }
    }
}
