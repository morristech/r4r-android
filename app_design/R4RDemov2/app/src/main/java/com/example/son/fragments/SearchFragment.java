package com.example.son.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.son.r4rdemov2.R;
import com.example.son.r4rdemov2.SearchRequest;
import com.example.son.r4rdemov2.SearchResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Son on 4/12/2016.
 */
public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search,container,false);

        final EditText etStreet = (EditText) rootView.findViewById(R.id.etStreet);
        final EditText etDistrict = (EditText) rootView.findViewById(R.id.etDistrict);
        final EditText etWard = (EditText) rootView.findViewById(R.id.etWard);
        final EditText etCity = (EditText) rootView.findViewById(R.id.etCity);

        final EditText etMinPrice = (EditText) rootView.findViewById(R.id.etMinPrice);
        final EditText etMaxPrice = (EditText) rootView.findViewById(R.id.etMaxPrice);

        final EditText etMinArea = (EditText) rootView.findViewById(R.id.etMinArea);
        final EditText etMaxArea = (EditText) rootView.findViewById(R.id.etMaxArea);
        final Button btnSearch = (Button) rootView.findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String street = etStreet.getText().toString();
                final String district = etDistrict.getText().toString();
                final String ward = etWard.getText().toString();
                final String city = etCity.getText().toString();
                final int minPrice = Integer.parseInt(etMinPrice.getText().toString());
                final int maxPrice = Integer.parseInt(etMaxPrice.getText().toString());
                final double minArea = Double.parseDouble(etMinArea.getText().toString());
                final double maxArea = Double.parseDouble(etMaxArea.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String result = response;
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean status = Boolean.parseBoolean(jsonResponse.opt("status").toString());

                            if (status){
                                Intent intent = new Intent(getActivity().getApplicationContext(),SearchResult.class);
                                intent.putExtra("response", result);
                                startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                                builder.setMessage("Search Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                SearchRequest searchRequest = new SearchRequest(street,district,ward,city,minPrice,maxPrice,minArea,maxArea,responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(searchRequest);
            }
        });

        return rootView;
    }
}
