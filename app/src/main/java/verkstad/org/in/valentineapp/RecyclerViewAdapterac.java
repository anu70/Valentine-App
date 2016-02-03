package verkstad.org.in.valentineapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.util.ArrayList;

/**
 * Created by anu on 2/2/2016.
 */
public class RecyclerViewAdapterac extends RecyclerView.Adapter<RecyclerViewAdapterac.viewHolderac> {
    LayoutInflater inflater;
    ArrayList<String> ac_name,ac_image;

    public RecyclerViewAdapterac(Context context,ArrayList<String> ac_image,ArrayList<String> ac_name){
       inflater=LayoutInflater.from(context);
        this.ac_image=ac_image;
        this.ac_name=ac_name;
    }
    @Override
    public viewHolderac onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.auto_complete_textview_row,parent,false);
        viewHolderac holderac = new viewHolderac(view);
        return holderac;
    }

    @Override
    public void onBindViewHolder(viewHolderac holder, int position) {
        String ac_name = ac_image.get(position);
        String acimage = ac_image.get(position);
        holder.ac_name.setText(ac_name);
        holder.ac_image.setProfileId(acimage);

    }

    @Override
    public int getItemCount() {
        return ac_name.size();
    }

    class viewHolderac extends RecyclerView.ViewHolder {
        ProfilePictureView ac_image;
        TextView ac_name;
        public viewHolderac(View itemView) {
            super(itemView);
            ac_image= (ProfilePictureView) itemView.findViewById(R.id.autocomplete_image);
            ac_name= (TextView) itemView.findViewById(R.id.autocomplete_name);
        }
    }
}
