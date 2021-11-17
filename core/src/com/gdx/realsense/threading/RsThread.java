package com.gdx.realsense.threading;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class RsThread implements Runnable, Disposable {

    private static final String TAG = RsThread.class.getSimpleName();

    private Thread thread;
    private Array<RsUpdatableThread> listUpdatable;

    public RsThread() {
        listUpdatable = new Array<>();
        thread = new Thread(this);
        thread.setPriority(1);
        thread.start();
    }

    public synchronized void register(RsUpdatableThread updatable) {
        if (!listUpdatable.contains(updatable, true))
            listUpdatable.add(updatable);
    }

    public synchronized void unregister(RsUpdatableThread updatable) {
        listUpdatable.removeValue(updatable, true);
    }

    @Override
    public void run() {
        while (thread!= null && !thread.isInterrupted()){
            for (int i = 0; i < listUpdatable.size; i++) {
                if(listUpdatable.get(i) != null) listUpdatable.get(i).updateOnThread();
            }
        }
    }

    @Override
    public synchronized void dispose() {
        try{
            thread.interrupt();
            thread = null;
        }catch (Exception e){

        }finally {
            listUpdatable.clear();
        }
    }
}
