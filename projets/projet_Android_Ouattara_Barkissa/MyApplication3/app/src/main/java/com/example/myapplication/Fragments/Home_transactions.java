package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.Controller.TransactionDAO;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Model.Transaction;
import com.example.myapplication.R;
import com.example.myapplication.adapters.TransctionAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_transactions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_transactions extends Fragment {

    private RecyclerView homeTransactionRv;
    private TransctionAdapter adapter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home_transactions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_transaction.
     **/
    // Factory method to create a new instance of Home_transactions fragment with parameters
    public static Home_transactions newInstance(String param1, String param2) {
        Home_transactions fragment = new Home_transactions();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_transactions, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        homeTransactionRv = view.findViewById(R.id.homeTransactionRv);
        homeTransactionRv.setLayoutManager(new LinearLayoutManager(getContext()));

        TransactionDAO transactionDao = new TransactionDAO(requireContext()); // ✅ Initialisation
        List<Transaction> transactions = transactionDao.getAllTransactions(); // ✅ Récupération des transactions

        adapter = new TransctionAdapter((ArrayList<Transaction>) transactions);
        homeTransactionRv.setAdapter(adapter);

        transactionDao.close(); // ✅ Fermer la base de données après utilisation
    }


}