package org.tensorflow.lite.coin.detection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SecondFragment extends Fragment {

    private List<String> numberList;
    private List<Long> timestamps;
    private ArrayAdapter<String> adapter;
    private final double EXCHANGE_RATE = 9.12; // 환율

    private TextView totalJpyTextView;
    private TextView totalKrwTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numberList = new ArrayList<>();
        timestamps = new ArrayList<>();
        adapter = new ArrayAdapter<String>(requireContext(), 0, numberList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
                }

                TextView textView = convertView.findViewById(R.id.customTextView);
                textView.setText(formatCurrency(numberList.get(position), "￥(JPY)"));

                TextView krwTextView = convertView.findViewById(R.id.krwTextView);
                krwTextView.setText(formatCurrency(formatKRWAmount(numberList.get(position)), "원(KOR)"));

                TextView timestampTextView = convertView.findViewById(R.id.timestampTextView);
                timestampTextView.setText(formatTimestamp(timestamps.get(position)));

                return convertView;
            }
        };

        ListView numberListView = view.findViewById(R.id.numberListView);
        numberListView.setAdapter(adapter);

        // 아이템을 클릭하여 삭제하는 부분 추가
        numberListView.setOnItemClickListener((parent, view1, position, id) -> showDeleteConfirmationDialog(position));

        EditText numberEditText = view.findViewById(R.id.numberEditText);
        Button addButton = view.findViewById(R.id.addButton);

        totalJpyTextView = view.findViewById(R.id.totalJpyTextView);
        totalKrwTextView = view.findViewById(R.id.totalKrwTextView);

        addButton.setOnClickListener(v -> {
            String enteredNumber = numberEditText.getText().toString();
            if (!enteredNumber.isEmpty()) {
                double jpyAmount = Double.parseDouble(enteredNumber);
                double krwAmount = jpyAmount * EXCHANGE_RATE;

                numberList.add(String.valueOf(jpyAmount));
                timestamps.add(System.currentTimeMillis());
                adapter.notifyDataSetChanged();
                numberEditText.setText("");

                updateTotalAmount();
            }
        });
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일 HH시 mm분", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    private String formatKRWAmount(String jpyAmount) {
        double krwAmount = Double.parseDouble(jpyAmount) * EXCHANGE_RATE;
        return String.valueOf(krwAmount);
    }

    private String formatCurrency(String amount, String currencySymbol) {
        double parsedAmount = Double.parseDouble(amount);
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(parsedAmount) + currencySymbol;
    }

    private void updateTotalAmount() {
        double totalJpy = 0;
        double totalKrw = 0;

        for (String amount : numberList) {
            double jpyAmount = Double.parseDouble(amount);
            totalJpy += jpyAmount;
            totalKrw += jpyAmount * EXCHANGE_RATE;
        }

        totalJpyTextView.setText(String.format(Locale.getDefault(), "총 지출 금액 : %s(JPY)", formatCurrency(String.valueOf(totalJpy), "￥")));
        totalKrwTextView.setText(String.format(Locale.getDefault(), "%s(KOR)", formatCurrency(String.valueOf(totalKrw), "원")));
    }

    private void showDeleteConfirmationDialog(final int position) {
        // 다이얼로그의 텍스트 색상을 검은색으로 지정한 스타일 사용
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.AlertDialogStyle);

        builder.setTitle("삭제 확인")
                .setMessage("해당 사용 내역을 삭제하시겠습니까?")
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 클릭된 항목 삭제
                        numberList.remove(position);
                        timestamps.remove(position);

                        // 어댑터에 변경사항 알림
                        adapter.notifyDataSetChanged();

                        // 총 지출 금액 업데이트
                        updateTotalAmount();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 취소 버튼 클릭 시 아무 작업도 수행하지 않음
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }
}
