package com.vibecoding.viewbinding.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vibecoding.viewbinding.databinding.FragmentNeworderBinding;

public class CancelFragment extends Fragment {
     FragmentNeworderBinding binding;

    public CancelFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNeworderBinding.inflate(inflater, container, false);

        //recyclerView

        return binding.getRoot();
    }
}
