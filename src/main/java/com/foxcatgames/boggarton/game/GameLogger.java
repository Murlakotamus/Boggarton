package com.foxcatgames.boggarton.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.foxcatgames.boggarton.Const;
import com.foxcatgames.boggarton.Logger;

public class GameLogger {

    private boolean isInit;

    private FileOutputStream fos;
    private BufferedWriter bw;

    public GameLogger(final String name) {

        final SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        final SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH-mm-ss");

        final Date moment = new Date();
        final String date = simpleDateFormat1.format(moment);
        final String time = simpleDateFormat2.format(moment);

        final File file = new File("History" + File.separator + name + File.separator + date + File.separator + time + ".txt");
        file.getParentFile().mkdirs();
        try {
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            isInit = true;
        } catch (final FileNotFoundException e) {
            Logger.printStackTrace(e);
        }
    }

    public void close() {
        if (isInit)
            try {
                log("\n" + Const.GAMEOVER_STR);
                isInit = false;
                bw.close();
                fos.close();
            } catch (final IOException e) {
                Logger.printStackTrace(e);
            }
    }

    public void log(final String str) {
        if (isInit)
            try {
                bw.write(str);
                bw.flush();
            } catch (final IOException e) {
                Logger.printStackTrace(e);
            }
    }
}
