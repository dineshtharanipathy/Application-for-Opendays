/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package project.isep.com.opendays;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 * <p/>
 * <p>This class is used by (@link
 * ScreenSlideActivity} samples.</p>
 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        ImageView img = (ImageView)rootView.findViewById(R.id.img);
        img.setImageResource(getImage());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageClicked(mPageNumber);
            }
        });
        

        return rootView;
    }

    private int getImage(){
        switch(mPageNumber){
            case 0:
                return R.mipmap.p1;
            case 1:
                return R.mipmap.p2;
            case 2:
                return R.mipmap.p4;
            case 3:
                return R.mipmap.p5;
            default:
                return 0;
        }
    }

    private void imageClicked(int mPageNumber){
        String text = "";
        String title = "";
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (mPageNumber){
            case 0:
                title = "About ISEP";
                text = "ISEP is a graduate engineering school in Electronics, Software & Computer Engineering, Signal & Image Processing, Telecommunications and Networks";
                break;
            case 1:
                title="Infrastructure";
                text = "ISEP has developed an innovative teaching methods to foster the development of competences since 2008.";
                break;
            case 2:
                title="Education";
                text = "Students are given top class education with all the facilities like conference rooms";
                break;
            case 3:
                title="Maps";
                text = "NDC building: Line 12 (Mairie d’Issy/Porte de la Chapelle), stop “Notre-Dame des Champs” or Line 4 (Porte d’Orléans/Mairie de MLontrouge), stop « Saint-Placide »\n" +
                        "NDL building: Line 12 (Mairie d’Issy/Porte de la Chapelle), stop “Corentin Celton”";
                break;
        }
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setMessage(text);
        dialog = builder.create();
        dialog.show();
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
