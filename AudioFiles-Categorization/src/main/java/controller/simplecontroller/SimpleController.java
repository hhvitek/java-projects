package controller.simplecontroller;

import controller.IController;
import model.IModel;
import view.IView;
import view.MainForm;
import view.SimpleView;

public class SimpleController implements IController {
    private IModel model;
    private IView view;

    public SimpleController(IModel model) {
        this.model = model;
        this.view = new SimpleView(model, this);
        view.createView();
    }
}
