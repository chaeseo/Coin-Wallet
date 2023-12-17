package org.tensorflow.lite.coin.detection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private List<NumberModel> numberList = new ArrayList<>();
    private EditText editTextCapture3;

    // Parcelable 인터페이스 구현
    public static class NumberModel implements Parcelable {
        private String number;

        public NumberModel(String number) {
            this.number = number;
        }

        public String getNumber() {
            return number;
        }

        protected NumberModel(Parcel in) {
            number = in.readString();
        }

        public static final Creator<NumberModel> CREATOR = new Creator<NumberModel>() {
            @Override
            public NumberModel createFromParcel(Parcel in) {
                return new NumberModel(in);
            }

            @Override
            public NumberModel[] newArray(int size) {
                return new NumberModel[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(number);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        editTextCapture3 = rootView.findViewById(R.id.editTextCapture3);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton cameraButton = view.findViewById(R.id.imageViewCamera);
        if (cameraButton != null) {
            cameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(getActivity(), DetectorActivity.class);
                    startActivity(cameraIntent);
                }
            });
        }

        Button minusButton = view.findViewById(R.id.minus_button);
        if (minusButton != null) {
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMinusButtonClick();
                }
            });
        }
    }

    private void onMinusButtonClick() {
        String enteredNumber = editTextCapture3.getText().toString();

        if (!enteredNumber.isEmpty()) {
            NumberModel numberModel = new NumberModel(enteredNumber);
            numberList.add(numberModel);

            // 리스트를 SecondFragment로 전달
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("numberList", new ArrayList<>(numberList));

            SecondFragment secondFragment = new SecondFragment();
            secondFragment.setArguments(bundle);

            // 프래그먼트 이동
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, secondFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
