package comp3350.group6.promise.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.group6.promise.application.Main;

public class FileSystemUtil {

    public static void copyDatabaseToDevice(Activity activity) {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = activity.getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = activity.getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);

            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetManager, assetNames, dataDirectory);

            Main.setDBPath(dataDirectory.toString() + "/" + Main.getDBPath());

        } catch (final Exception ioe) {
            ioe.printStackTrace();
        }
    }

    public static void copyAssetsToDirectory(AssetManager assetManager, String[] assets, File directory) throws IOException {

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}
