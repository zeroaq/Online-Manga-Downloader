/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package anonscanlations.downloader;

import java.io.*;

/**
 *
 * @author Administrator
 */
public abstract class DownloadListener
{
    private boolean aborted;
    protected DownloadDirectory directory;
    public DownloadListener(DownloadDirectory myDir)
    {
        aborted = false;
        directory = myDir;
    }

    public abstract void downloadIncrement(Chapter c);
    public abstract void downloadFinished(Chapter c);
    public abstract void setTotal(int total);
    
    public synchronized void abortDownload()
    {
        aborted = true;
    }
    public synchronized boolean isDownloadAborted()
    {
        return(aborted);
    }

    final String BAD_CHARS ="[\\\\/:*?\"<>\\|]";
    
    public synchronized String downloadPath(Chapter c, int i) throws IOException
    {
        String saveTitle = "c" + c.getTitle() + "_" + String.format("%03d", i) + ".jpg";
        saveTitle = saveTitle.replace(' ', '_');

        saveTitle = saveTitle.replaceAll(BAD_CHARS, "");

        return((new File(directory.getFile(), saveTitle)).getAbsolutePath());
    }
}
