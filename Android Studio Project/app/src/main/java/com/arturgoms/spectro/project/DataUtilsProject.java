package com.arturgoms.spectro.project;

import android.os.Environment;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static com.arturgoms.spectro.project.MainProject.*;
import static com.arturgoms.spectro.project.MainProject.getBackupPath;
import static com.arturgoms.spectro.project.MainProject.getLocalPath;

    /*
    *   JSON file structure:
    *
    *   root_OBJ:{
    *       notes_ARR:[
    *           newNote_OBJ:{
    *             "title":"", "body":"", "colour":"", "favoured":true/false,
    *                   "fontSize":14/18/22, "hideBody":true/false},
    *           newNote_OBJ:{
    *             "title":"", "body":"", "colour":"", "favoured":true/false,
    *                   "fontSize":14/18/22, "hideBody":true/false}, etc
    *       ]
    *   };
    */


public class DataUtilsProject {

    public static final String PROJECT_FILE_NAME = "projetos.json"; // Local notes file name
    public static final String PROJECT_ARRAY_NAME = "projetos"; // Root object name

    public static final String BACKUP_FOLDER_PATH = "/Spectro"; // Backup folder path
    public static final String BACKUP_FILE_NAME = "spectro_backup.json"; // Backup file name

    // Note data constants used in intents and in key-value store
    public static final int NEW_PROJECT_REQUEST = 50000;
    public static final String PROJECT_REQUEST_CODE = "requestCode";
    public static final String PROJECT_TITLE = "title";
    public static final String PROJECT_BODY = "body";
    public static final String PROJECT_COLOUR = "colour";
    public static final String PROJECT_FAVOURED = "favoured";
    public static final String PROJECT_FONT_SIZE = "fontSize";
    public static final String PROJECT_HIDE_BODY = "hideBody";
    private static Button addBtn;
    public static final Button IN_PROJECT_BUTTON = addBtn;
    private static LinearLayout layoutProject;
    public static final LinearLayout IN_PROJECT_LAYOUT = layoutProject;

    /**
     * Wrap 'notes' array into a root object and store in file 'toFile'
     * @param toFile File to store notes into
     * @param project Array of notes to be saved
     * @return true if successfully saved, false otherwise
     */
    public static boolean saveData(File toFile, JSONArray project) {
        Boolean successful = false;

        JSONObject rootProject = new JSONObject();

        // If passed notes not null -> wrap in root JSONObject
        if (project != null) {
            try {
                rootProject.put(PROJECT_ARRAY_NAME, project);

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        // If passed notes null -> return false
        else
            return false;

        // If file is backup and it doesn't exist -> create file
        if (toFile == getBackupPath()) {
            if (isExternalStorageReadable() && isExternalStorageWritable()) {
                if (!toFile.exists()) {
                    try {
                        Boolean createdProject = toFile.createNewFile();

                        // If file failed to create -> return false
                        if (!createdProject)
                            return false;

                    } catch (IOException e) {
                        e.printStackTrace();
                        return false; // If file creation threw exception -> return false
                    }
                }
            }

            // If external storage not readable/writable -> return false
            else
                return false;
        }

        // If file is local and it doesn't exist -> create file
        else if (toFile == getLocalPath() && !toFile.exists()) {
            try {
                Boolean createdProject = toFile.createNewFile();

                // If file failed to create -> return false
                if (!createdProject)
                    return false;

            } catch (IOException e) {
                e.printStackTrace();
                return false; // If file creation threw exception -> return false
            }
        }


        BufferedWriter bufferedWriterProject = null;

        try {
            // Initialize BufferedWriter with FileWriter and write root object to file
            bufferedWriterProject = new BufferedWriter(new FileWriter(toFile));
            bufferedWriterProject.write(rootProject.toString());

            // If we got to this stage without throwing an exception -> set successful to true
            successful = true;

        } catch (IOException e) {
            // If something went wrong in try block -> set successful to false
            successful = false;
            e.printStackTrace();

        } finally {
            // Finally, if bufferedWriter not null -> flush and close it
            if (bufferedWriterProject != null) {
                try {
                    bufferedWriterProject.flush();
                    bufferedWriterProject.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return successful;
    }


    /**
     * Read from file 'fromFile' and return parsed JSONArray of notes
     * @param fromFile File we are reading from
     * @return JSONArray of notes
     */
    public static JSONArray retrieveData(File fromFile) {
        JSONArray project = null;

        // If file is backup and it doesn't exist -> return null
        if (fromFile == getBackupPath()) {
            if (isExternalStorageReadable() && !fromFile.exists()) {
                return null;
            }
        }

        /*
         * If file is local and it doesn't exist ->
         * Initialize notes JSONArray as new and save into local file
         */
        else if (fromFile == getLocalPath() && !fromFile.exists()) {
            project = new JSONArray();

            Boolean successfulSaveToLocal = saveData(fromFile, project);

            // If save successful -> return new notes
            if (successfulSaveToLocal) {
                return project;
            }

            // Else -> return null
            return null;
        }


        JSONObject rootProject = null;
        BufferedReader bufferedReader = null;

        try {
            // Initialize BufferedReader, read from 'fromFile' and store into root object
            bufferedReader = new BufferedReader(new FileReader(fromFile));

            StringBuilder text = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
            }

            rootProject = new JSONObject(text.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();

        } finally {
            // Finally, if bufferedReader not null -> close it
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // If root is not null -> get notes array from root object
        if (rootProject != null) {
            try {
                project = rootProject.getJSONArray(PROJECT_ARRAY_NAME);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Return fetches notes < May return null! >
        return project;
    }


    /**
     * Create new JSONArray of notes from 'from' without the notes at positions in 'selectedNotes'
     * @param from Main notes array to delete from
     * @param selectedProject ArrayList of Integer which represent note positions to be deleted
     * @return New JSONArray of notes without the notes at positions 'selectedNotes'
     */
    public static JSONArray deleteProject(JSONArray from, ArrayList<Integer> selectedProject) {
        // Init new JSONArray
        JSONArray newProject = new JSONArray();

        // Loop through main notes
        for (int i = 0; i < from.length(); i++) {
            // If array of positions to delete doesn't contain current position -> put in new array
            if (!selectedProject.contains(i)) {
                try {
                    newProject.put(from.get(i));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // Finally, return the new notes
        return newProject;
    }


    /**
     * Check if external storage is writable or not
     * @return true if writable, false otherwise
     */
    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Check if external storage is readable or not
     * @return true if readable, false otherwise
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
