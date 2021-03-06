package com.katuliteam.bagiresep_ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  DBConfig config;
  SQLiteDatabase db;
  Cursor cursor;

  Button btnLogout;
  Intent intentDisplay;

  TextView nama, email, linkGantiPassword, linkUbahAkun;

  public ProfileFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment ProfileFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ProfileFragment newInstance(String param1, String param2) {
    ProfileFragment fragment = new ProfileFragment();
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
    // Inflate the layout for this fragment
    View vw = inflater.inflate(R.layout.fragment_profile, container, false);

    config = new DBConfig(getContext());

    nama = vw.findViewById(R.id.txt_nama);
    email = vw.findViewById(R.id.txt_email);

    db = config.getReadableDatabase();
    cursor = db.rawQuery("SELECT * FROM tbl_login",null);
    cursor.moveToFirst();

    nama.setText(cursor.getString(1));
    email.setText(cursor.getString(0));

    linkUbahAkun = vw.findViewById(R.id.link_ubah_akun);
    linkUbahAkun.setOnClickListener(v -> {

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setIcon(R.mipmap.ic_launcher);
      builder.setTitle("Sabar yaa...");
      builder.setMessage("Ini emang dibikin gak bagus :)");
      builder.create().show();

    });

    linkGantiPassword = vw.findViewById(R.id.link_ganti_password);
    linkGantiPassword.setOnClickListener(v -> {

      intentDisplay = new Intent(getActivity(), UbahPasswordActivity.class);
      intentDisplay.putExtra("email", cursor.getString(0));
      startActivity(intentDisplay);

    });

    btnLogout = vw.findViewById(R.id.btnLogout);
    btnLogout.setOnClickListener(v -> {

      db = config.getReadableDatabase();

      db.execSQL("DELETE FROM tbl_login");

      intentDisplay = new Intent(getActivity(), AuthActivity.class);
      startActivity(intentDisplay);
      getActivity().finish();

    });

    return vw;
  }
}