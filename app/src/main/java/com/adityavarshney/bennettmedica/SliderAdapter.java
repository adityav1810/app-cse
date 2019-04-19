package com.adityavarshney.bennettmedica;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.w3c.dom.Text;

public class SliderAdapter extends PagerAdapter {
  Context context;
  LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;

    }
    public int[] slide_image={
            R.drawable.aryan,
            R.drawable.aniket,
            R.drawable.aditya,
            R.drawable.abhimanyu,
            R.drawable.abhinav
    };
     public String[] slide_heading={
       "Aryan Kapur",
       "Aniket Chowdhury",
       "Aditya Varshney",
       "Abhimanyu Banerjee",
       "Abhinav Robinson"
     };
     public  String[] slide_enroll={
             "E18CSE004",
             "E18CSE015",
             "E18CSE010",
             "E18CSE005",
             "E18CSE006",
     };
     public String[] slide_desc={
             "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
             "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",

             "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
             "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
             "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
     };
    @Override
    public int getCount() {
        return slide_heading.length ;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view ==(RelativeLayout) o;
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position){
          layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
          View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideImageView=(ImageView) view.findViewById((R.id.slide_image));

        TextView slideHeadingView=(TextView) view.findViewById((R.id.slide_heading));

        TextView slideEnrollView=(TextView) view.findViewById((R.id.slide_enroll));

        TextView slideDescView=(TextView) view.findViewById((R.id.slide_desc));



        slideImageView.setImageResource(slide_image[position]);
        slideHeadingView.setText(slide_heading[position]);
        slideEnrollView.setText(slide_enroll[position]);
        slideDescView.setText(slide_desc[position]);

        container.addView(view);

        return  view;
    }
    @Override
    public void destroyItem(ViewGroup container,int postion,Object object){
         container.removeView((RelativeLayout)object);
    }
}
