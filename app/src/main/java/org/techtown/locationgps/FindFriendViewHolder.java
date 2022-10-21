package org.techtown.locationgps;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImage;
    TextView username;
    public FindFriendViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage=itemView.findViewById(R.id.profileImage);
        username=itemView.findViewById(R.id.username);


    }
}
