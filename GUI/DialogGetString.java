package GUI;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.TextField;

import javax.swing.BoxLayout;

public class DialogGetString extends Dialog {
    private TextField text;
    private Button button;

    private void init() {
        text = new TextField(11);
        button = new Button("Ok");
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(text);
        add(button);
        addWindowListener(new DialogClose(this));
        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(250, 60);
        setResizable(false);
    }
    
    public DialogGetString(Dialog owner) {
        super(owner);
        init();
    }
    
    public DialogGetString(Frame owner) {
        super(owner);
        init();
    }

    public TextField getText() {
        return text;
    }

    public Button getButton() {
        return button;
    }
}
