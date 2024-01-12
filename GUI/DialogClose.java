package GUI;

import java.awt.Dialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogClose extends WindowAdapter {
    final Dialog dialog;
    
    public DialogClose(final Dialog dialog) {
        this.dialog = dialog;
    }
    
    public void windowClosing(final WindowEvent windowEvent) {
        dialog.setVisible(false);
    }
}
