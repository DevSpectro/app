package com.arturgoms.spectro.project;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arturgoms.spectro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_BODY;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_COLOUR;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_FAVOURED;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_FONT_SIZE;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_HIDE_BODY;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_TITLE;
import static com.arturgoms.spectro.project.DataUtilsProject.IN_PROJECT_BUTTON;
import static com.arturgoms.spectro.project.MainProject.checkedArray;
import static com.arturgoms.spectro.project.MainProject.deleteActive;
import static com.arturgoms.spectro.project.MainProject.searchActive;
import static com.arturgoms.spectro.project.MainProject.setFavourite;


/**
 * Adapter class for custom notes ListView
 */
public class NoteAdapterProject extends BaseAdapter implements ListAdapter {
    private Context contextProject;
    private JSONArray adapterDataProject;
    private LayoutInflater inflaterProject;

    /**
     * Adapter constructor -> Sets class variables
     * @param context application context
     * @param adapterDataProject JSONArray of notes
     */
    public NoteAdapterProject(Context context, JSONArray adapterDataProject) {
        this.contextProject = context;
        this.adapterDataProject = adapterDataProject;
        this.inflaterProject = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Return number of notes
    @Override
    public int getCount() {
        if (this.adapterDataProject != null)
            return this.adapterDataProject.length();

        else
            return 0;
    }

    // Return note at position
    @Override
    public JSONObject getItem(int position) {
        if (this.adapterDataProject != null)
            return this.adapterDataProject.optJSONObject(position);

        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    // View inflater
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Inflate custom note view if null
        if (convertView == null)
            convertView = this.inflaterProject.inflate(R.layout.list_view_project, parent, false);

        // Initialize layout items
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayoutProject);
        LayerDrawable roundedCard = (LayerDrawable) contextProject.getResources().getDrawable(R.drawable.rounded_card);
        TextView titleView = (TextView) convertView.findViewById(R.id.titleViewProject);
        TextView bodyView = (TextView) convertView.findViewById(R.id.bodyViewProject);
        ImageButton favourite = (ImageButton) convertView.findViewById(R.id.favourite);
        Button addBtn = (Button) convertView.findViewById(R.id.addBtn);

        // Get Note object at position
        JSONObject ProjectObject = getItem(position);

        if (ProjectObject != null) {
            // If noteObject not empty -> initialize variables
            String title = contextProject.getString(R.string.project_title);
            String body = contextProject.getString(R.string.project_body);
            String colour = String.valueOf(contextProject.getResources().getColor(R.color.white));
            int fontSize = 18;
            Boolean hideBody = false;
            Boolean favoured = false;

            try {
                // Get noteObject data and store in variables
                title = ProjectObject.getString(PROJECT_TITLE);
                body = ProjectObject.getString(PROJECT_BODY);
                colour = ProjectObject.getString(PROJECT_COLOUR);

                if (ProjectObject.has(PROJECT_FONT_SIZE))
                    fontSize = ProjectObject.getInt(PROJECT_FONT_SIZE);

                if (ProjectObject.has(PROJECT_HIDE_BODY))
                    hideBody = ProjectObject.getBoolean(PROJECT_HIDE_BODY);

                favoured = ProjectObject.getBoolean(PROJECT_FAVOURED);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Set favourite image resource
            if (favoured)
                favourite.setImageResource(R.drawable.ic_fav);

            else
                favourite.setImageResource(R.drawable.ic_unfav);


            // If search or delete modes are active -> hide favourite button; Show otherwise
            if (searchActive || deleteActive)
                favourite.setVisibility(View.INVISIBLE);

            else
                favourite.setVisibility(View.VISIBLE);


            titleView.setText(title);

            // If hidBody is true -> hide body of note
            if (hideBody)
                bodyView.setVisibility(View.GONE);

            // Else -> set visible note body, text to normal and set text size to 'fontSize' as sp
            else {
                bodyView.setVisibility(View.VISIBLE);
                bodyView.setText(body);
                bodyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            }

            // If current note is selected for deletion -> highlight
            if (checkedArray.contains(position)) {
                ((GradientDrawable) roundedCard.findDrawableByLayerId(R.id.card))
                        .setColor(contextProject.getResources().getColor(R.color.theme_primary));
            }

            // If current note is not selected -> set background colour to normal
            else {
                ((GradientDrawable) roundedCard.findDrawableByLayerId(R.id.card))
                        .setColor(Color.parseColor(colour));
            }

            // Set note background style to rounded card
            relativeLayout.setBackground(roundedCard);

            final Boolean finalFavoured = favoured;
            favourite.setOnClickListener(new View.OnClickListener() {
                // If favourite button was clicked -> change that note to favourite or un-favourite
                @Override
                public void onClick(View v) {
                    setFavourite(contextProject, !finalFavoured, position);
                }
            });
        }

        return convertView;
    }
}
