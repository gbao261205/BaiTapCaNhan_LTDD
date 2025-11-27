package com.vibecoding.viewbinding.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vibecoding.viewbinding.databinding.FragmentNeworderBinding;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class NewOrderFragment extends Fragment {
    FragmentNeworderBinding binding;
    public NewOrderFragment(){

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
