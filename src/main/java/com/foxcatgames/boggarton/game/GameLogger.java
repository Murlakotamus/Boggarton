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

public class GameLogger {

    private boolean isInit = false;

    private FileOutputStream fos = null;
    private BufferedWriter bw = null;

    public GameLogger(String name) {

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            logEvent("\n" + Const.GAMEOVER);
            isInit = false;
            bw.flush();
            bw.close();
            fos.flush();
            fos.close();
            bw = null;
            fos = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logEvent(final String str) {
        try {
            bw.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInit() {
        return isInit;
    }
}
