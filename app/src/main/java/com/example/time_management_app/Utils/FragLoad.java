package com.example.time_management_app.Utils;

import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.time_management_app.R;

public class FragLoad {
    public static void loadFrag(Fragment fragment, FrameLayout frameLayout, FragmentManager fragmentManager, int flag) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(flag==1){
            fragmentTransaction.add(frameLayout.getId(),fragment);
        }
        else{
            fragmentTransaction.replace(frameLayout.getId(),fragment);
            //  clean the back stack of the fragment
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
