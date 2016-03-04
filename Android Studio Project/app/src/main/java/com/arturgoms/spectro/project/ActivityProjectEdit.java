package com.arturgoms.spectro.project;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.arturgoms.spectro.ColorPicker.ColorPickerDialog;
import com.arturgoms.spectro.R;
import com.arturgoms.spectro.common.logger.Log;

import java.io.OutputStream;
import java.util.UUID;

import static com.arturgoms.spectro.ColorPicker.ColorPickerSwatch.OnClickListener;
import static com.arturgoms.spectro.ColorPicker.ColorPickerSwatch.OnColorSelectedListener;
import static com.arturgoms.spectro.ColorPicker.ColorPickerSwatch.OnTouchListener;
import static com.arturgoms.spectro.project.DataUtilsProject.NEW_PROJECT_REQUEST;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_BODY;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_COLOUR;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_FONT_SIZE;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_HIDE_BODY;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_REQUEST_CODE;
import static com.arturgoms.spectro.project.DataUtilsProject.PROJECT_TITLE;
import static com.arturgoms.spectro.project.DataUtilsProject.IN_PROJECT_BUTTON;

public class ActivityProjectEdit extends ActionBarActivity implements Toolbar.OnMenuItemClickListener {


    private static final String TAG = "LEDOnOff";

    Button btnOn, btnOff;

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    // Well known SPP UUID
    private static final UUID MY_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Insert your server's MAC address
    private static String address = "00:00:00:00:00:00";
    // Layout components
    private EditText titleProject, bodyProject;
    private RelativeLayout relativeLayoutProject;
    private Toolbar toolbar;
    private MenuItem menuHideBody;

    private InputMethodManager imm;
    private Bundle bundle;

    private String[] colourArr; // Colours string array
    private int[] colourArrResId; // colourArr to resource int array
    private int[] fontSizeArr; // Font sizes int array
    private String[] fontSizeNameArr; // Font size names string array

    // Defaults
    private String colour = "#FFFFFF"; // white default
    private int fontSize = 18; // Medium default
    private Boolean hideBody = false;

    private AlertDialog fontDialog, saveChangesDialog;
    private ColorPickerDialog colorPickerDialog;

    private LinearLayout layoutProject;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "In onCreate()");

        setContentView(R.layout.layout_editable);

        btAdapter = BluetoothAdapter.getDefaultAdapter();


        // Android version >= 18 -> set orientation fullUser
        if (Build.VERSION.SDK_INT >= 18)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);

            // Android version < 18 -> set orientation fullSensor
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        // Initialize colours and font sizes arrays
        colourArr = getResources().getStringArray(R.array.colours);

        colourArrResId = new int[colourArr.length];
        for (int i = 0; i < colourArr.length; i++)
            colourArrResId[i] = Color.parseColor(colourArr[i]);

        fontSizeArr = new int[] {14, 18, 22}; // 0 for small, 1 for medium, 2 for large
        fontSizeNameArr = getResources().getStringArray(R.array.fontSizeNames);
        setContentView(R.layout.project_edit);

        layoutProject = (LinearLayout)findViewById(R.id.layoutProject);
        addBtn = (Button)findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
            addViews();
            }
        });



        titleProject = (EditText)findViewById(R.id.titleProject);
        bodyProject = (EditText)findViewById(R.id.bodyProject);
        relativeLayoutProject = (RelativeLayout)findViewById(R.id.relativeLayoutProject);
        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        addBtn = (Button) findViewById(R.id.addBtn);

        imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);



        // If scrollView touched and note body doesn't have focus -> request focus and go to body end
        scrollView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!bodyProject.isFocused()) {
                    bodyProject.requestFocus();
                    bodyProject.setSelection(bodyProject.getText().length());
                    // Force show keyboard
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                            InputMethodManager.HIDE_IMPLICIT_ONLY);

                    return true;
                }

                return false;
            }
        });

        // Get data bundle from MainActivity
        bundle = getIntent().getExtras();

        if (bundle != null) {
            // If current note is not new -> initialize colour, font, hideBody and EditTexts
            if (bundle.getInt(PROJECT_REQUEST_CODE) != NEW_PROJECT_REQUEST) {
                colour = bundle.getString(PROJECT_COLOUR);
                fontSize = bundle.getInt(PROJECT_FONT_SIZE);
                hideBody = bundle.getBoolean(PROJECT_HIDE_BODY);

                titleProject.setText(bundle.getString(PROJECT_TITLE));
                bodyProject.setText(bundle.getString(PROJECT_BODY));
                bodyProject.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

                if (hideBody)
                    menuHideBody.setTitle(R.string.action_show_body);
            }

            // If current note is new -> request keyboard focus to note title and show keyboard
            else if (bundle.getInt(PROJECT_REQUEST_CODE) == NEW_PROJECT_REQUEST) {
                titleProject.requestFocus();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }

            // Set background colour to note colour
            relativeLayoutProject.setBackgroundColor(Color.parseColor(colour));
        }

        initDialogs(this);
    }

    private void addViews() {
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.layout_editable, null);
        Button buttonRemove = (Button)addView.findViewById(R.id.remove);
        buttonRemove.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                Toast msg = Toast.makeText(getBaseContext(),
                        "You have clicked On", Toast.LENGTH_SHORT);
                msg.show();
            }});

        layoutProject.addView(addView);
    }



    /**
     * Initialize toolbar with required components such as
     * - title, navigation icon + listener, menu/OnMenuItemClickListener, menuHideBody -
     */
    protected void initToolbar() {
        toolbar.setTitle("");

        // Set a 'Back' navigation icon in the Toolbar and handle the click
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Inflate menu_edit to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_edit_project);

        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(this);

        Menu menu = toolbar.getMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_project, menu);

        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Note colour menu item clicked -> show colour picker dialog
        if (id == R.id.action_save) {
            if (bundle.getInt(PROJECT_REQUEST_CODE) == NEW_PROJECT_REQUEST)
                saveChangesDialog.show();

                // Existing note
            else {
            /*
             * If title is not empty -> Check if note changed
             *  If yes -> saveChanges
             *  If not -> hide keyboard if showing and finish
             */
                if (!isEmpty(titleProject)) {
                    if (!(titleProject.getText().toString().equals(bundle.getString(PROJECT_TITLE))) ||
                            !(bodyProject.getText().toString().equals(bundle.getString(PROJECT_BODY))) ||
                            !(colour.equals(bundle.getString(PROJECT_COLOUR))) ||
                            fontSize != bundle.getInt(PROJECT_FONT_SIZE) ||
                            hideBody != bundle.getBoolean(PROJECT_HIDE_BODY)) {

                        saveChanges();
                    }

                    else {
                        imm.hideSoftInputFromWindow(titleProject.getWindowToken(), 0);

                        finish();
                        overridePendingTransition(0, 0);
                    }
                }

                // If title empty -> Toast title cannot be empty
                else
                    toastEditTextCannotBeEmpty();
            }
            return true;
        }

        // Font size menu item clicked -> show font picker dialog
        if (id == R.id.action_font_size) {
            fontDialog.show();
            return true;
        }

        // If 'Hide note body in list' or 'Show note body in list' clicked

        return false;
    }

    /**
     * Implementation of AlertDialogs such as
     * - colorPickerDialog, fontDialog and saveChangesDialog -
     * @param context The Activity context of the dialogs; in this case EditActivity context
     */
    protected void initDialogs(Context context) {
        // Colour picker dialog
        colorPickerDialog = ColorPickerDialog.newInstance(R.string.dialog_note_colour,
                colourArrResId, Color.parseColor(colour), 3,
                isTablet(this) ? ColorPickerDialog.SIZE_LARGE : ColorPickerDialog.SIZE_SMALL);

        // Colour picker listener in colour picker dialog
        colorPickerDialog.setOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                // Format selected colour to string
                String selectedColourAsString = String.format("#%06X", (0xFFFFFF & color));

                // Check which colour is it and equal to main colour
                for (String aColour : colourArr)
                    if (aColour.equals(selectedColourAsString))
                        colour = aColour;

                // Re-set background colour
                relativeLayoutProject.setBackgroundColor(Color.parseColor(colour));
            }
        });


        // Font size picker dialog
        fontDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_font_size)
                .setItems(fontSizeNameArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Font size updated with new pick
                        fontSize = fontSizeArr[which];
                        bodyProject.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                    }
                })
                .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();


        // 'Save changes?' dialog
        saveChangesDialog = new AlertDialog.Builder(context)
                .setMessage(R.string.dialog_save_changes)
                .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If 'Yes' clicked -> check if title is empty
                        // If title not empty -> save and go back; Otherwise toast
                        if (!isEmpty(titleProject))
                            saveChanges();

                        else
                            toastEditTextCannotBeEmpty();
                    }
                })
                .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If 'No' clicked in new note -> put extra 'discard' to show toast
                        if (bundle != null && bundle.getInt(PROJECT_REQUEST_CODE) ==
                                NEW_PROJECT_REQUEST) {

                            Intent intent = new Intent();
                            intent.putExtra("request", "discard");

                            setResult(RESULT_CANCELED, intent);

                            imm.hideSoftInputFromWindow(titleProject.getWindowToken(), 0);

                            dialog.dismiss();
                            finish();
                            overridePendingTransition(0, 0);
                        }
                    }
                })
                .create();
    }


    /**
     * Check if current device has tablet screen size or not
     * @param context current application context
     * @return true if device is tablet, false otherwise
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * Item clicked in Toolbar menu callback method
     * @param item Item clicked
     * @return true if click detected and logic finished, false otherwise
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        // Note colour menu item clicked -> show colour picker dialog
        if (id == R.id.action_save) {
            colorPickerDialog.show(getFragmentManager(), "colourPicker");
            return true;
        }

        // Font size menu item clicked -> show font picker dialog
        if (id == R.id.action_font_size) {
            fontDialog.show();
            return true;
        }

        // If 'Hide note body in list' or 'Show note body in list' click

        return false;
    }


    /**
     * Create an Intent with title, body, colour, font size and hideBody extras
     * Set RESULT_OK and go back to MainActivity
     */
    protected void saveChanges() {
        Intent intent = new Intent();

        // Package everything and send back to activity with OK
        intent.putExtra(PROJECT_TITLE, titleProject.getText().toString());
        intent.putExtra(PROJECT_BODY, bodyProject.getText().toString());
        intent.putExtra(PROJECT_COLOUR, colour);
        intent.putExtra(PROJECT_FONT_SIZE, fontSize);
        intent.putExtra(PROJECT_HIDE_BODY, hideBody);

        setResult(RESULT_OK, intent);

        imm.hideSoftInputFromWindow(titleProject.getWindowToken(), 0);

        finish();
        overridePendingTransition(0, 0);
    }


    /**
     * Back or navigation '<-' pressed
     */
    @Override
    public void onBackPressed() {
        // New note -> show 'Save changes?' dialog
        if (bundle.getInt(PROJECT_REQUEST_CODE) == NEW_PROJECT_REQUEST)
            saveChangesDialog.show();

            // Existing note
        else {
            /*
             * If title is not empty -> Check if note changed
             *  If yes -> saveChanges
             *  If not -> hide keyboard if showing and finish
             */
            if (!isEmpty(titleProject)) {
                if (!(titleProject.getText().toString().equals(bundle.getString(PROJECT_TITLE))) ||
                        !(bodyProject.getText().toString().equals(bundle.getString(PROJECT_BODY))) ||
                        !(colour.equals(bundle.getString(PROJECT_COLOUR))) ||
                        fontSize != bundle.getInt(PROJECT_FONT_SIZE) ||
                        hideBody != bundle.getBoolean(PROJECT_HIDE_BODY)) {

                    saveChanges();
                }

                else {
                    imm.hideSoftInputFromWindow(titleProject.getWindowToken(), 0);

                    finish();
                    overridePendingTransition(0, 0);
                }
            }

            // If title empty -> Toast title cannot be empty
            else
                toastEditTextCannotBeEmpty();
        }
    }


    /**
     * Check if passed EditText text is empty or not
     * @param editText The EditText widget to check
     * @return true if empty, false otherwise
     */
    protected boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    /**
     * Show Toast for 'Title cannot be empty'
     */
    protected void toastEditTextCannotBeEmpty() {
        Toast toast = Toast.makeText(getApplicationContext(),
                getResources().getString(R.string.toast_edittext_cannot_be_empty),
                Toast.LENGTH_LONG);
        toast.show();
    }


    /**
     * If current window loses focus -> hide keyboard
     * @param hasFocus parameter passed by system; true if focus changed, false otherwise
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!hasFocus)
            if (imm != null && titleProject != null)
                imm.hideSoftInputFromWindow(titleProject.getWindowToken(), 0);
    }


    /**
     * Orientation changed callback method
     * If orientation changed -> If any AlertDialog is showing -> dismiss it to prevent WindowLeaks
     * @param newConfig Configuration passed by system
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (colorPickerDialog != null && colorPickerDialog.isDialogShowing())
            colorPickerDialog.dismiss();

        if (fontDialog != null && fontDialog.isShowing())
            fontDialog.dismiss();

        if (saveChangesDialog != null && saveChangesDialog.isShowing())
            saveChangesDialog.dismiss();

        super.onConfigurationChanged(newConfig);
    }

}
