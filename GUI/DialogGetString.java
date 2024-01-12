package GUI;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;

public class DialogGetString extends Dialog {
    private TextField text;
    private Button button;
    
    public DialogGetString(Dialog owner) {
        super(owner);
        text = new TextField(11);
        button = new Button("Ok");
        setLayout(new FlowLayout());
        add(text);
        add(button);
        addWindowListener(new DialogClose(this));
        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(200, 70);
        setResizable(false);
    }
    
    public DialogGetString(Frame owner) {
        super(owner);
        text = new TextField(11);
        button = new Button("Ok");
        setLayout(new FlowLayout());
        add(text);
        add(button);
        addWindowListener(new DialogClose(this));
        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(200, 70);
        setResizable(false);
    }

    public TextField getText() {
        return text;
    }

    public Button getButton() {
        return button;
    }
}
