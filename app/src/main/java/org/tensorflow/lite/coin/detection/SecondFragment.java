package org.tensorflow.lite.coin.detection;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SecondFragment extends Fragment {

    private TextView textViewNumbers;
    private RecyclerView recyclerViewNumbers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // XML에서 정의한 TextView와 RecyclerView를 초기화
        textViewNumbers = view.findViewById(R.id.textViewNumbers);
        recyclerViewNumbers = view.findViewById(R.id.recyclerViewNumbers);

        // 번들에서 전달된 데이터를 가져옴
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<FirstFragment.NumberModel> numberList = bundle.getParcelableArrayList("numberList");

            // 가져온 데이터를 TextView에 표시
            if (numberList != null) {
                StringBuilder numbersText = new StringBuilder("Numbers: ");
                for (FirstFragment.NumberModel numberModel : numberList) {
                    numbersText.append(numberModel.getNumber()).append(", ");
                }
                textViewNumbers.setText(numbersText.toString());
                Log.d("SecondFragment", "TextView Content: " + numbersText.toString());
            }

            // RecyclerView에 데이터를 표시하는 어댑터를 설정
            if (numberList != null) {
                NumbersAdapter numbersAdapter = new NumbersAdapter(numberList);
                recyclerViewNumbers.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerViewNumbers.setAdapter(numbersAdapter);
            }
        }
    }

}
